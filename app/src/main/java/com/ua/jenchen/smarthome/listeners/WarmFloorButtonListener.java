package com.ua.jenchen.smarthome.listeners;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.warmfloor.WarmFloorState;
import com.ua.jenchen.smarthome.button.Button;

public class WarmFloorButtonListener implements Button.OnButtonEventListener {

    private static final boolean DEFAULT_STATE = false;

    private String uid;
    private boolean state;

    public WarmFloorButtonListener(String uid) {
        this.uid = uid;
        state = DEFAULT_STATE;
    }

    @Override
    public void onButtonEvent(Button button, boolean pressed) {
        if (pressed) {
            state = !state;
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference(AppConstants.WARM_FLOOR_STATE_TABLE_NAME);
            WarmFloorState warmFloorState = new WarmFloorState();
            warmFloorState.setUid(uid);
            warmFloorState.setState(state);
            reference.child(uid).setValue(warmFloorState);
        }
    }
}
