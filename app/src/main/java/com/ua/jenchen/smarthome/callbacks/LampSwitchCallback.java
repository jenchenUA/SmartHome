package com.ua.jenchen.smarthome.callbacks;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.LampState;

import java.io.IOException;

public class LampSwitchCallback implements GpioCallback {

    private static final String LOG_TAG = LampSwitchCallback.class.getSimpleName();

    private Gpio output;
    private String uidOfLamp;

    public LampSwitchCallback(Gpio output, String uidOfLamp) {
        this.output = output;
        this.uidOfLamp = uidOfLamp;
    }

    @Override
    public boolean onGpioEdge(Gpio gpio) {
        try {
            if (!gpio.getValue()) {
                return true;
            }
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference(AppConstants.LIGHT_STATE_TABLE_NAME);
            reference.child(uidOfLamp).setValue(new LampState(uidOfLamp, !output.getValue()));
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
