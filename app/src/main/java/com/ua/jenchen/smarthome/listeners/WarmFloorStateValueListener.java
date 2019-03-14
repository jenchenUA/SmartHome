package com.ua.jenchen.smarthome.listeners;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ua.jenchen.models.WarmFloorState;
import com.ua.jenchen.smarthome.managers.GpioManager;
import com.ua.jenchen.smarthome.managers.WarmFloorManager;

public class WarmFloorStateValueListener implements ValueEventListener {

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            WarmFloorState value = snapshot.getValue(WarmFloorState.class);
            if (value != null) {
                WarmFloorManager manager = GpioManager.getInstance().getWarmFloorManager(value.getUid());
                if (manager != null) {
                    manager.setState(value);
                }
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
