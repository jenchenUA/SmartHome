package com.ua.jenchen.smarthome.services;

import com.ua.jenchen.models.LightConfiguration;
import com.ua.jenchen.smarthome.database.dao.LightConfiguratonDao;
import com.ua.jenchen.smarthome.managers.GpioManager;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;

public class LightConfigurationService {

    private LightConfiguratonDao dao;

    @Inject
    public LightConfigurationService(LightConfiguratonDao dao) {
        this.dao = dao;
    }

    public void create(LightConfiguration configuration) {
        dao.insert(configuration);
        GpioManager.getInstance().makeLampManager(configuration);
    }

    public void runSavedConfigurations(AppCompatActivity activity) {
        dao.getAllAsLiveData().observe(activity,
                lightConfigurations -> lightConfigurations.forEach(GpioManager.getInstance()::makeLampManager));
    }
}
