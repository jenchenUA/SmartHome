package com.ua.jenchen.smarthome.services;

import com.ua.jenchen.models.LightConfiguration;
import com.ua.jenchen.smarthome.database.dao.LightConfiguratonDao;
import com.ua.jenchen.smarthome.managers.GpioManager;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;

public class LightConfigurationService {

    private LightConfiguratonDao dao;
    private GpioManager gpioManager;

    @Inject
    public LightConfigurationService(LightConfiguratonDao dao, GpioManager gpioManager) {
        this.dao = dao;
        this.gpioManager = gpioManager;
    }

    public void create(LightConfiguration configuration) {
        dao.insert(configuration);
        gpioManager.makeLampManager(configuration);
    }

    public void runSavedConfigurations(AppCompatActivity activity) {
        dao.getAllAsLiveData().observe(activity,
                lightConfigurations -> lightConfigurations.forEach(gpioManager::makeLampManager));
    }
}
