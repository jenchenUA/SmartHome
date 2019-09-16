package com.ua.jenchen.smarthome.listeners;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ua.jenchen.models.GpioExpanderConfiguration;
import com.ua.jenchen.smarthome.managers.PeripheralGpioManager;

import java.util.Objects;
import java.util.stream.StreamSupport;

public class ExpernalGpioProviderConfigurationValueListener implements ValueEventListener {

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        StreamSupport.stream(dataSnapshot.getChildren().spliterator(), false)
                .map(snapshot -> snapshot.getValue(GpioExpanderConfiguration.class))
                .filter(Objects::nonNull)
                .filter(this::isNotExist)
                .forEach(this::configureLightManager);
    }

    private boolean isNotExist(GpioExpanderConfiguration configuration) {
        return !PeripheralGpioManager.getInstance().isProviderExists(configuration.getAddress());
    }

    private void configureLightManager(GpioExpanderConfiguration configuration) {
        PeripheralGpioManager.getInstance().createGpioProvider(configuration.getAddress());
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
