package com.ua.jenchen.smarthome.di.modules;

import com.ua.jenchen.smarthome.managers.AdcManager;
import com.ua.jenchen.smarthome.managers.GpioManager;
import com.ua.jenchen.smarthome.managers.PeripheralGpioManager;
import com.ua.jenchen.smarthome.services.WebSocketService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PeripheralModule {

    @Provides
    @Singleton
    public PeripheralGpioManager peripheralGpioManager() {
        return new PeripheralGpioManager();
    }

    @Provides
    @Singleton
    public GpioManager gpioManager(PeripheralGpioManager manager, WebSocketService webSocketService) {
        return new GpioManager(manager, webSocketService);
    }

    @Provides
    @Singleton
    public AdcManager adcManager() {
        return new AdcManager();
    }
}
