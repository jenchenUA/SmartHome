package com.ua.jenchen.smarthome.listeners;

import com.google.android.things.pio.Gpio;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.light.LampState;
import com.ua.jenchen.smarthome.button.Button;

import java.io.IOException;

public class LightButtonListener implements Button.OnButtonEventListener {

    private String uid;
    private Gpio controlPin;

    public LightButtonListener(String uid, Gpio controlPin) {
        this.uid = uid;
        this.controlPin = controlPin;
    }

    @Override
    public void onButtonEvent(Button button, boolean pressed) {
        if (pressed) {
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference(AppConstants.LIGHT_STATE_TABLE_NAME);
            try {
                reference.child(uid).setValue(new LampState(uid, !controlPin.getValue()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
