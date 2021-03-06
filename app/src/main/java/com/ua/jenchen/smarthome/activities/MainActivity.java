package com.ua.jenchen.smarthome.activities;

import android.os.Bundle;

import com.google.android.things.update.UpdateManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.smarthome.R;
import com.ua.jenchen.smarthome.application.SmartHomeApplication;
import com.ua.jenchen.smarthome.listeners.LampStateValueEventListener;
import com.ua.jenchen.smarthome.listeners.UpdateStatusListener;
import com.ua.jenchen.smarthome.managers.AdcManager;
import com.ua.jenchen.smarthome.managers.GpioManager;
import com.ua.jenchen.smarthome.services.ExtensionService;
import com.ua.jenchen.smarthome.services.LampStateService;
import com.ua.jenchen.smarthome.services.LightConfigurationService;
import com.ua.jenchen.smarthome.services.WarmFloorService;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Inject
    FirebaseDatabase firebaseDatabase;
    @Inject
    LightConfigurationService lightConfigurationService;
    @Inject
    LampStateService lampStateService;
    @Inject
    GpioManager gpioManager;
    @Inject
    UpdateStatusListener statusListener;
    @Inject
    UpdateManager manager;
    @Inject
    ExtensionService extensionService;
    @Inject
    AdcManager adcManager;
    @Inject
    WarmFloorService warmFloorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SmartHomeApplication.appComponent.inject(this);
        manager.addStatusListener(statusListener);

        CompletableFuture.runAsync(() -> {
            extensionService.initGpioExpanderConfigurations();
            extensionService.initAdcConfigurations();
            warmFloorService.disableAllWarmFloorManagers();
        }).join();
        initGpioExpanders();
        initAdc();
        initLight();
        initWarmFloor();
    }

    private void initGpioExpanders() {
        extensionService.runGpioExpanderConfiguration(this);
    }

    private void initAdc() {
        extensionService.runAdcConfiguration(this);
    }

    private void initLight() {
        lampStateService.disableAllLight();
        DatabaseReference reference = firebaseDatabase.getReference(AppConstants.LIGHT_STATE_TABLE_NAME);
        reference.addValueEventListener(new LampStateValueEventListener());
        lightConfigurationService.runSavedConfigurations(this);
    }

    private void initWarmFloor() {
        warmFloorService.runAllConfigurations(this);
    }

    @Override
    protected void onDestroy() {
        gpioManager.closeAllGpios();
        manager.removeStatusListener(statusListener);
        adcManager.close();
        super.onDestroy();
    }
}
