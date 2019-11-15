package com.ua.jenchen.smarthome.managers;

import android.util.Log;

import com.google.android.things.contrib.driver.adc.ads1xxx.Ads1xxx;
import com.google.android.things.pio.Gpio;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.warmfloor.WarmFloorConfiguration;
import com.ua.jenchen.models.warmfloor.WarmFloorView;
import com.ua.jenchen.smarthome.application.SmartHomeApplication;
import com.ua.jenchen.smarthome.button.Button;
import com.ua.jenchen.smarthome.listeners.WarmFloorButtonListener;
import com.ua.jenchen.smarthome.workers.WarmFloorListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import static com.ua.jenchen.smarthome.button.Button.LogicState.PRESSED_WHEN_LOW;

public class WarmFloorManager implements AutoCloseable {

    private static final String LOG_TAG = WarmFloorManager.class.getSimpleName();

    private Gpio controlPin;
    private Gpio buttonPin;
    private Button button;
    private WarmFloorConfiguration configuration;
    private WarmFloorListener listener;
    private Ads1xxx adc;
    @Inject
    AdcManager adcManager;

    public WarmFloorManager(Gpio controlPin, Gpio buttonPin, WarmFloorConfiguration configuration) {
        SmartHomeApplication.appComponent.inject(this);
        try {
            this.controlPin = configureControlPin(controlPin);
            this.buttonPin = buttonPin;
            this.button = new Button(buttonPin, PRESSED_WHEN_LOW);
            this.button.setOnButtonEventListener(new WarmFloorButtonListener(getUid()));
            this.configuration = configuration;
            Ads1xxx adc = adcManager.getAds1115ByAddress(configuration.getAdcAddress());
            if (adc == null) {
                throw new IllegalStateException(String.format("ADC with address %s isn't found",
                        configuration.getAdcAddress()));
            }
            this.adc = adc;
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

    private Gpio configureControlPin(Gpio gpio) throws IOException {
        gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH);
        gpio.setActiveType(Gpio.ACTIVE_LOW);
        return gpio;
    }

    public void setState(boolean state) {
        if (state) {
            listener = new WarmFloorListener(this, controlPin, this.configuration.getThreshold());
            listener.execute();
        } else if (listener != null){
            listener.shutdown();
        }
    }

    public void setThreshold(float threshold) {
        this.configuration.setThreshold(threshold);
        if (listener != null) {
            listener.setThreshold(threshold);
        }
    }

    public float getTemperatureInCelsius() {
        float averageVoltage = getAverageVoltage();
        float thermistorResistance = getThermistorResistance(averageVoltage);
        float measuredTemperature = getMeasuredTemperatureInKelvin(thermistorResistance);
        if (Float.isNaN(measuredTemperature)) {
            measuredTemperature = AppConstants.ZERO_TEMPERATURE;
        }
        return measuredTemperature - AppConstants.ZERO_TEMPERATURE;
    }

    private float getMeasuredTemperatureInKelvin(float thermistorResistance) {
        return (float) ((configuration.getBOfThermoresistor() * AppConstants.ROOM_TEMPERATURE) /
                    (configuration.getBOfThermoresistor() + (AppConstants.ROOM_TEMPERATURE *
                            Math.log(thermistorResistance / configuration.getResistanceOfThermoresistor()))));
    }

    private float getThermistorResistance(float averageVoltage) {
        return configuration.getResistanceOfSupportResistor() *
                    ((configuration.getVoltage() / averageVoltage) - 1f);
    }

    private float getAverageVoltage() {
        float result = 0;
        for (int i = 0; i < configuration.getCountOfMeasures(); i++) {
            result += getVoltage(configuration.getChannel());
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                Log.e(LOG_TAG, "Interruption", e);
            }
        }
        return result / configuration.getCountOfMeasures();
    }

    public boolean isEnabled() {
        return listener != null && listener.isExecuted();
    }

    public boolean isWarmingEnabled() {
        try {
            return controlPin.getValue();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            return false;
        }
    }

    private double getVoltage(int channel) {
        try {
            return adc.readSingleEndedVoltage(channel);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error during voltage reading", e);
            return 0;
        }
    }

    @Override
    public void close() throws Exception {
        disable(buttonPin);
        button.close();
        controlPin.close();
    }

    public List<Gpio> releaseGpios() {
        button.unregisterCallback();
        disable(controlPin);
        return Arrays.asList(buttonPin, controlPin);
    }

    public WarmFloorView toView() {
        WarmFloorView view = new WarmFloorView();
        view.setUid(this.configuration.getUid());
        view.setLabel(this.configuration.getLabel());
        view.setThreshold(this.configuration.getThreshold());
        view.setEnabled(isEnabled());
        view.setWarmingEnabled(isWarmingEnabled());
        view.setCurrentTemperature(getTemperatureInCelsius());
        view.setOnline(true);
        return view;
    }

    private void disable(Gpio gpio) {
        try {
            gpio.setValue(false);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Can't be disabled object: " + gpio);
        }
    }
}
