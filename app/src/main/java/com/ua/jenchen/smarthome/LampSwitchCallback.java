package com.ua.jenchen.smarthome;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;

import java.io.IOException;

public class LampSwitchCallback implements GpioCallback {

    private static final String LOG_TAG = LampSwitchCallback.class.getSimpleName();

    private Gpio output;

    public LampSwitchCallback(Gpio output) {
        this.output = output;
    }

    @Override
    public boolean onGpioEdge(Gpio gpio) {
        try {
            if (!gpio.getValue()) {
                return true;
            }
            output.setValue(!output.getValue());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Switch error", e);
        }
        return true;
    }

    @Override
    public void onGpioError(Gpio gpio, int error) {
        Log.w(LOG_TAG, gpio + " Error code: " + error);
    }
}
