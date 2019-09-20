package com.ua.jenchen.smarthome.di.modules;

import com.ua.jenchen.smarthome.server.controllers.LightConfigurationController;
import com.ua.jenchen.smarthome.services.LightConfigurationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServerModule {

    @Provides
    @Singleton
    public LightConfigurationController lightConfigurationController(LightConfigurationService service) {
        return new LightConfigurationController(service);
    }
}
