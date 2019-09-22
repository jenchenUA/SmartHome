package com.ua.jenchen.smarthome.listeners;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ua.jenchen.models.LampState;
import com.ua.jenchen.smarthome.application.SmartHomeApplication;
import com.ua.jenchen.smarthome.managers.GpioManager;
import com.ua.jenchen.smarthome.managers.LampManager;
import com.ua.jenchen.smarthome.services.LampStateService;

import javax.inject.Inject;

import androidx.annotation.NonNull;

public class LampStateValueEventListener implements ValueEventListener {

    @Inject
    GpioManager gpioManager;
    @Inject
    LampStateService lampStateService;

    public LampStateValueEventListener() {
        SmartHomeApplication.appComponent.inject(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            LampState value = snapshot.getValue(LampState.class);
            if (value != null) {
                LampManager manager = gpioManager.getLampManager(value.getUid());
                if (manager != null) {
                    lampStateService.changeStateInDB(value.getUid(), value.getState());
                    manager.changeStateOfLamp(value.getState());
                }
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
