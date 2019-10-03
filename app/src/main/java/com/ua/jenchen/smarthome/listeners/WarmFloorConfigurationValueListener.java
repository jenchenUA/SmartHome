package com.ua.jenchen.smarthome.listeners;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ua.jenchen.models.warmfloor.WarmFloorConfiguration;
import com.ua.jenchen.smarthome.application.SmartHomeApplication;
import com.ua.jenchen.smarthome.managers.GpioManager;

import java.util.Objects;
import java.util.stream.StreamSupport;

import javax.inject.Inject;

import androidx.annotation.NonNull;

public class WarmFloorConfigurationValueListener implements ValueEventListener {

    @Inject
    GpioManager gpioManager;

    public WarmFloorConfigurationValueListener() {
        SmartHomeApplication.appComponent.inject(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        StreamSupport.stream(dataSnapshot.getChildren().spliterator(), false)
                .map(snapshot -> snapshot.getValue(WarmFloorConfiguration.class))
                .filter(Objects::nonNull)
                .filter(configuration -> gpioManager.isNotManagerExist(configuration.getUid()))
                .forEach(this::configureWarmFloorManager);
    }

    private void configureWarmFloorManager(WarmFloorConfiguration configuration) {
        gpioManager.makeWarmFloorManager(configuration);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
