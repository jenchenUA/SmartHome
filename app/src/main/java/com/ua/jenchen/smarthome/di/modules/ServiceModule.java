package com.ua.jenchen.smarthome.di.modules;

import com.google.android.things.update.UpdateManager;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.smarthome.database.dao.ExtensionDao;
import com.ua.jenchen.smarthome.database.dao.LampStateDao;
import com.ua.jenchen.smarthome.database.dao.LightConfigurationDao;
import com.ua.jenchen.smarthome.managers.AdcManager;
import com.ua.jenchen.smarthome.managers.GpioManager;
import com.ua.jenchen.smarthome.managers.PeripheralGpioManager;
import com.ua.jenchen.smarthome.services.ExtensionService;
import com.ua.jenchen.smarthome.services.LampStateService;
import com.ua.jenchen.smarthome.services.LightConfigurationService;
import com.ua.jenchen.smarthome.services.SystemUpdateService;
import com.ua.jenchen.smarthome.services.WebSocketService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    @Singleton
    public LightConfigurationService lightConfigurationService(LightConfigurationDao lightConfigurationDao,
                                                               GpioManager gpioManager,
                                                               LampStateDao lampStateDao,
                                                               FirebaseDatabase database) {
        return new LightConfigurationService(lightConfigurationDao, gpioManager, lampStateDao, database);
    }

    @Provides
    @Singleton
    public LampStateService lampStateService(LampStateDao dao, FirebaseDatabase database, GpioManager gpioManager) {
        return new LampStateService(dao, database, gpioManager);
    }

    @Provides
    @Singleton
    public SystemUpdateService systemUpdateService(UpdateManager updateManager) {
        return new SystemUpdateService(updateManager);
    }

    @Singleton
    @Provides
    public WebSocketService webSocketService() {
        return new WebSocketService();
    }

    @Singleton
    @Provides
    public ExtensionService extensionService(ExtensionDao extensionDao, AdcManager adcManager,
                                             PeripheralGpioManager peripheralGpioManager) {
        return new ExtensionService(extensionDao, peripheralGpioManager, adcManager);
    }
}
