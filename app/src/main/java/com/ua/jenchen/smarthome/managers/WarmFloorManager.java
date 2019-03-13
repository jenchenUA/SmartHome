package com.ua.jenchen.smarthome.managers;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.WarmFloorConfiguration;
import com.ua.jenchen.models.WarmFloorState;
import com.ua.jenchen.smarthome.callbacks.WarmFloorSwitchCallback;
import com.ua.jenchen.smarthome.services.AdcService;
import com.ua.jenchen.smarthome.services.Ads1115Service;
import com.ua.jenchen.smarthome.workers.WarmFloorListener;

import java.io.IOException;

public class WarmFloorManager {

    private static final String LOG_TAG = WarmFloorManager.class.getSimpleName();

    private Gpio controlPin;
    private Gpio switcherPin;
    private WarmFloorConfiguration configuration;
    private AdcService adc;
    private WarmFloorListener listener;

    public WarmFloorManager(Gpio controlPin, Gpio switcherPin,
                            WarmFloorConfiguration configuration) {

        try {
            this.controlPin = configureControlPin(controlPin,
                    configuration.isControlPinHighActivation());
            WarmFloorSwitchCallback callback = new WarmFloorSwitchCallback(
                    configuration.getSwircherPin() + configuration.getControlPin());
            this.switcherPin = configureSwitcherPin(switcherPin, callback);
            this.configuration = configuration;
            adc = Ads1115Service.getInstance();
        } catch (IOException e) {
            Log.e(LOG_TAG, "GPIO error", e);
        }
    }


    private Gpio configureControlPin(Gpio gpio, boolean activeHigh) throws IOException {
        gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        gpio.setActiveType(Gpio.ACTIVE_LOW);
        return gpio;
    }

    private Gpio configureSwitcherPin(Gpio gpio, GpioCallback callback) throws IOException {
        gpio.setDirection(Gpio.DIRECTION_IN);
        gpio.setActiveType(Gpio.ACTIVE_HIGH);
        gpio.setEdgeTriggerType(Gpio.EDGE_FALLING);
        gpio.registerGpioCallback(callback);
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
}
