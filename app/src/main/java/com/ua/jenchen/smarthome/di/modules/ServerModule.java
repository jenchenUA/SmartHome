package com.ua.jenchen.smarthome.di.modules;

import com.ua.jenchen.smarthome.server.controllers.ExtensionController;
import com.ua.jenchen.smarthome.server.controllers.LampStateController;
import com.ua.jenchen.smarthome.server.controllers.LightConfigurationController;
import com.ua.jenchen.smarthome.server.controllers.SystemUpdateController;
import com.ua.jenchen.smarthome.services.ExtensionService;
import com.ua.jenchen.smarthome.services.LampStateService;
import com.ua.jenchen.smarthome.services.LightConfigurationService;
import com.ua.jenchen.smarthome.services.SystemUpdateService;

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

    @Provides
    @Singleton
    public LampStateController lampStateController(LampStateService lampStateService) {
        return new LampStateController(lampStateService);
    }

    @Provides
    @Singleton
    public SystemUpdateController systemUpdateController(SystemUpdateService systemUpdateService) {
        return new SystemUpdateController(systemUpdateService);
    }

    @Provides
    @Singleton
    public ExtensionController extensionController(ExtensionService extensionService) {
        return new ExtensionController(extensionService);
    }
}
