package com.ua.jenchen.smarthome.listeners;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ua.jenchen.models.LightConfiguration;
import com.ua.jenchen.smarthome.managers.GpioManager;

import java.util.Objects;
import java.util.stream.StreamSupport;

public class LightConfigurationValueListener implements ValueEventListener {

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        StreamSupport.stream(dataSnapshot.getChildren().spliterator(), false)
                .map(snapshot -> snapshot.getValue(LightConfiguration.class))
                .filter(Objects::nonNull)
                .filter(configuration -> GpioManager.getInstance()
                        .isNotManagerExist(configuration.getUid()))
                .forEach(this::configureLightManager);
    }

    private void configureLightManager(LightConfiguration configuration) {
        GpioManager.getInstance().makeLampManager(configuration);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
