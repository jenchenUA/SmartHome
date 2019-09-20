package com.ua.jenchen.smarthome.di.modules;

import com.ua.jenchen.smarthome.database.AppDatabase;
import com.ua.jenchen.smarthome.server.controllers.LightConfigurationController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServerModule {

    @Provides
    @Singleton
    public LightConfigurationController provideLightConfigurationController(AppDatabase database) {
        return new LightConfigurationController(database);
    }
}
