package com.ua.jenchen.smarthome.di.modules;

import com.ua.jenchen.smarthome.database.dao.LightConfiguratonDao;
import com.ua.jenchen.smarthome.managers.GpioManager;
import com.ua.jenchen.smarthome.services.LightConfigurationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    @Singleton
    public LightConfigurationService lightConfigurationService(LightConfiguratonDao lightConfiguratonDao,
                                                               GpioManager gpioManager) {
        return new LightConfigurationService(lightConfiguratonDao, gpioManager);
    }
}
