package com.ua.jenchen.smarthome.callbacks;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.WarmFloorState;

import java.io.IOException;

public class WarmFloorSwitchCallback implements GpioCallback {

    private static final String LOG_TAG = WarmFloorSwitchCallback.class.getSimpleName();

    private String uid;

    public WarmFloorSwitchCallback(String uid) {
        this.uid = uid;
    }

    @Override
    public boolean onGpioEdge(Gpio gpio) {
        try {
            if (!gpio.getValue()) {
                return true;
            }
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference(AppConstants.WARM_FLOOR_STATE_TABLE_NAME);
            WarmFloorState warmFloorState = new WarmFloorState();
            warmFloorState.setUid(uid);
            warmFloorState.setState(gpio.getValue());
            reference.child(uid).setValue(warmFloorState);
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
