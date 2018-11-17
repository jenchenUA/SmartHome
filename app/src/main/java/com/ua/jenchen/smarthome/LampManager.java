package com.ua.jenchen.smarthome;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;

import java.io.IOException;

public class LampManager {

    private static final String LOG_TAG = LampManager.class.getSimpleName();

    private Gpio input;
    private Gpio output;

    public LampManager(Gpio input, Gpio output) {
        try {
            this.output = configureOutput(output);
            LampSwitchCallback callback = new LampSwitchCallback(this.output);
            this.input = configureInput(input, callback);
        } catch (IOException e) {
            Log.e(LOG_TAG, "GPIO error", e);
        }
    }

    public void changeStateOfLamp() {
        try {
            output.setValue(!output.getValue());
        } catch (IOException e) {
            Log.e(LOG_TAG, "GPIO error", e);
        }
    }

    private Gpio configureOutput(Gpio gpio) throws IOException {
        gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        gpio.setActiveType(Gpio.ACTIVE_HIGH);
        gpio.setValue(true);
        return gpio;
    }

    private Gpio configureInput(Gpio gpio, GpioCallback callback) throws IOException {
        gpio.setDirection(Gpio.DIRECTION_IN);
        gpio.setActiveType(Gpio.ACTIVE_HIGH);
        gpio.setEdgeTriggerType(Gpio.EDGE_FALLING);
        gpio.registerGpioCallback(callback);
        return gpio;
    }
}
