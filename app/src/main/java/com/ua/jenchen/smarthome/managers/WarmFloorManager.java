package com.ua.jenchen.smarthome.managers;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.WarmFloorConfiguration;
import com.ua.jenchen.models.WarmFloorState;
import com.ua.jenchen.smarthome.button.Button;
import com.ua.jenchen.smarthome.listeners.WarmFloorButtonListener;
import com.ua.jenchen.smarthome.services.AdcService;
import com.ua.jenchen.smarthome.services.Ads1115Service;
import com.ua.jenchen.smarthome.workers.WarmFloorListener;

import java.io.IOException;

import static com.ua.jenchen.smarthome.button.Button.LogicState.PRESSED_WHEN_LOW;

public class WarmFloorManager implements AutoCloseable {

    private static final String LOG_TAG = WarmFloorManager.class.getSimpleName();

    private Gpio controlPin;
    private Button button;
    private WarmFloorConfiguration configuration;
    private AdcService adc;
    private WarmFloorListener listener;

    public WarmFloorManager(Gpio controlPin, Gpio buttonPin, WarmFloorConfiguration configuration) {
        try {
            this.controlPin = configureControlPin(controlPin,
                    configuration.isControlPinHighActivation());
            this.button = new Button(buttonPin, PRESSED_WHEN_LOW);
            this.button.setOnButtonEventListener(new WarmFloorButtonListener(getUid()));
            this.configuration = configuration;
            adc = Ads1115Service.getInstance();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Warm floor manager can't be created", e);
        }
    }

    private String getUid() {
        return button.getName() + controlPin.getName();
    }

    public String getLabel() {
        return configuration.getLabel();
    }

    private Gpio configureControlPin(Gpio gpio, boolean activeHigh) throws IOException {
        gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH);
        gpio.setActiveType(Gpio.ACTIVE_LOW);
        return gpio;
    }

    public void setState(WarmFloorState state) {
        if (listener != null) {
            listener.setThreshold(state.getTemperature());
        }
        if (state.getState()) {
            listener = new WarmFloorListener(this, controlPin, state.getTemperature());
            listener.execute();
        } else if (!state.getState() && listener != null){
            listener.shutdown();
        }
    }

    public float getTemperatureInCelsius() {
        float result = 0;
        for (int i = 0; i < configuration.getCountOfMeasures(); i++) {
            float measuredVoltage = adc.getVoltage(configuration.getChannel());
            float resistance = configuration.getResistanceOfSupportResistor() *
                    ((configuration.getVoltage() / measuredVoltage) - 1f);
            float measuredTemperature = (float) ((configuration.getBOfThermoresistor() * AppConstants.ROOM_TEMPERATURE) /
                    (configuration.getBOfThermoresistor() + (AppConstants.ROOM_TEMPERATURE *
                            Math.log(resistance / configuration.getResistanceOfThresmoresistor()))));
            result += measuredTemperature;
        }
        return (result / configuration.getCountOfMeasures()) - AppConstants.ZERO_TEMPERATURE;
    }

    @Override
    public void close() throws Exception {
        button.close();
    }
}
