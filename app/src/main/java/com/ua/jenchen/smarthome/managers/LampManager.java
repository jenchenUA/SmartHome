package com.ua.jenchen.smarthome.managers;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.ua.jenchen.smarthome.callbacks.LampSwitchCallback;

import java.io.IOException;

public class LampManager {

    private static final String LOG_TAG = LampManager.class.getSimpleName();

    private Gpio input;
    private Gpio output;
    private boolean activeHigh;

    public LampManager(Gpio input, Gpio output, boolean activeHigh) {
        try {
            this.output = configureOutput(output, activeHigh);
            LampSwitchCallback callback = new LampSwitchCallback(this.output,
                    input.getName() + output.getName());
            this.input = configureInput(input, callback);
            this.activeHigh = activeHigh;
        } catch (IOException e) {
            Log.e(LOG_TAG, "GPIO error", e);
        }
    }

    public void changeStateOfLamp(boolean state) {
        try {
            output.setValue(activeHigh == state);
        } catch (IOException e) {
            Log.e(LOG_TAG, "GPIO error", e);
        }
    }

    private Gpio configureOutput(Gpio gpio, boolean activeHigh) throws IOException {
        gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        if (activeHigh) {
            gpio.setActiveType(Gpio.ACTIVE_HIGH);
        } else {
            gpio.setActiveType(Gpio.ACTIVE_LOW);
        }
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
