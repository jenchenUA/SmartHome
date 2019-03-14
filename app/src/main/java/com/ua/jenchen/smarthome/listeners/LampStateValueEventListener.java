package com.ua.jenchen.smarthome.listeners;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ua.jenchen.models.LampState;
import com.ua.jenchen.smarthome.managers.GpioManager;
import com.ua.jenchen.smarthome.managers.LampManager;

public class LampStateValueEventListener implements ValueEventListener {

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            LampState value = snapshot.getValue(LampState.class);
            if (value != null) {
                LampManager manager = GpioManager.getInstance().getLampManager(value.getUid());
                if (manager != null) {
                    manager.changeStateOfLamp(value.getState());
                }
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
