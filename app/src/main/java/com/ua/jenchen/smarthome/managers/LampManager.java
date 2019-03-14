package com.ua.jenchen.smarthome.managers;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.ua.jenchen.models.LightConfiguration;
import com.ua.jenchen.smarthome.button.Button;
import com.ua.jenchen.smarthome.listeners.LightButtonListener;

import java.io.IOException;

public class LampManager implements AutoCloseable {

    private static final String LOG_TAG = LampManager.class.getSimpleName();

    private Button button;
    private Gpio controlPin;
    private LightConfiguration configuration;

    public LampManager(Gpio buttonPin, Gpio controlPin, LightConfiguration configuration) {
        try {
            this.controlPin = configureOutput(controlPin);
            button = new Button(buttonPin, Button.LogicState.PRESSED_WHEN_LOW);
            button.setOnButtonEventListener(new LightButtonListener(getUid(), controlPin));
            this.configuration = configuration;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Lamp manager can't be created", e);
        }
    }

    public void changeStateOfLamp(boolean state) {
        try {
            controlPin.setValue(state);
            Log.i(LOG_TAG,"State of lamp with label: " + getLabel() + " was changed");
        } catch (IOException e) {
            Log.e(LOG_TAG, "State of lamp can't be changed", e);
        }
    }

    public String getLabel() {
        return configuration.getLabel();
    }

    @Override
    public void close() throws Exception {
        button.close();
    }

    private String getUid() {
        return button.getName() + controlPin.getName();
    }

    private Gpio configureOutput(Gpio gpio) throws IOException {
        gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        gpio.setActiveType(Gpio.ACTIVE_LOW);
        return gpio;
    }
}
