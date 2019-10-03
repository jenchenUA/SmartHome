package com.ua.jenchen.smarthome.services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.light.LampState;
import com.ua.jenchen.models.light.LightConfiguration;
import com.ua.jenchen.smarthome.database.dao.LampStateDao;
import com.ua.jenchen.smarthome.database.dao.LightConfigurationDao;
import com.ua.jenchen.smarthome.managers.GpioManager;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;

public class LightConfigurationService {

    private LightConfigurationDao dao;
    private GpioManager gpioManager;
    private LampStateDao lampStateDao;
    private DatabaseReference lampStateReference;

    @Inject
    public LightConfigurationService(LightConfigurationDao dao, GpioManager gpioManager, LampStateDao lampStateDao,
                                     FirebaseDatabase firebaseDatabase) {
        this.dao = dao;
        this.gpioManager = gpioManager;
        this.lampStateDao = lampStateDao;
        this.lampStateReference = firebaseDatabase.getReference(AppConstants.LIGHT_STATE_TABLE_NAME);
    }

    public void create(LightConfiguration configuration) {
        dao.insert(configuration);
    }

    public List<LightConfiguration> getConfigurations() {
        return dao.getAll();
    }

    public Optional<LightConfiguration> getConfiguration(String uid) {
        return Optional.ofNullable(dao.getByUid(uid));
    }

    public void runSavedConfigurations(AppCompatActivity activity) {
        dao.getAllAsLiveData().observe(activity,
                lightConfigurations -> lightConfigurations.stream()
                        .filter(configuration -> gpioManager.isNotManagerExist(configuration.getUid()))
                        .forEach(this::makeLampManager)
        );
    }

    private void makeLampManager(LightConfiguration configuration) {
        CompletableFuture.runAsync(() -> lampStateDao.insert(new LampState(configuration.getUid(), false)));
        gpioManager.makeLampManager(configuration);
    }

    public void deleteConfiguration(String uid) {
        dao.deleteByUid(uid);
        lampStateReference.child(uid).setValue(null);
        gpioManager.destroyLampManager(uid);
    }
}
