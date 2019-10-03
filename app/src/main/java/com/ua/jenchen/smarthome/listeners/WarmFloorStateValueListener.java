package com.ua.jenchen.smarthome.listeners;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ua.jenchen.models.warmfloor.WarmFloorState;
import com.ua.jenchen.smarthome.application.SmartHomeApplication;
import com.ua.jenchen.smarthome.managers.GpioManager;
import com.ua.jenchen.smarthome.managers.WarmFloorManager;

import javax.inject.Inject;

public class WarmFloorStateValueListener implements ValueEventListener {

    @Inject
    GpioManager gpioManager;

    public WarmFloorStateValueListener() {
        SmartHomeApplication.appComponent.inject(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            WarmFloorState value = snapshot.getValue(WarmFloorState.class);
            if (value != null) {
                WarmFloorManager manager = gpioManager.getWarmFloorManager(value.getUid());
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
