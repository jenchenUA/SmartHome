package com.ua.jenchen.smarthome.managers;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.ua.jenchen.smarthome.button.Button;
import com.ua.jenchen.smarthome.listeners.LightButtonListener;

import java.io.IOException;

public class LampManager {

    private static final String LOG_TAG = LampManager.class.getSimpleName();

    private Button button;
    private Gpio controlPin;

    public LampManager(Gpio buttonPin, Gpio controlPin) {
        try {
            this.controlPin = configureOutput(controlPin);
            button = new Button(buttonPin, Button.LogicState.PRESSED_WHEN_LOW);
            button.setOnButtonEventListener(new LightButtonListener(getUid(), controlPin));
        } catch (IOException e) {
            Log.e(LOG_TAG, "Lamp manager can't be created", e);
        }
    }

    private String getUid() {
        return button.getName() + controlPin.getName();
    }

    public void changeStateOfLamp(boolean state) {
        try {
            controlPin.setValue(state);
        } catch (IOException e) {
            Log.e(LOG_TAG, "State of lamp can't be changed", e);
        }
    }

    private Gpio configureOutput(Gpio gpio) throws IOException {
        gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        gpio.setActiveType(Gpio.ACTIVE_LOW);
        return gpio;
    }
}
