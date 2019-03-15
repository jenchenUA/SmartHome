package com.ua.jenchen.smarthome.listeners;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ua.jenchen.models.WarmFloorConfiguration;
import com.ua.jenchen.smarthome.managers.GpioManager;

import java.util.Objects;
import java.util.stream.StreamSupport;

public class WarmFloorConfigurationValueListener implements ValueEventListener {

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        StreamSupport.stream(dataSnapshot.getChildren().spliterator(), false)
                .map(snapshot -> snapshot.getValue(WarmFloorConfiguration.class))
                .filter(Objects::nonNull)
                .filter(configuration -> GpioManager.getInstance()
                        .isNotManagerExist(configuration.getUid()))
                .forEach(this::configureWarmFloorManager);
    }

    private void configureWarmFloorManager(WarmFloorConfiguration configuration) {
        GpioManager.getInstance().makeWarmFloorManager(configuration);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
