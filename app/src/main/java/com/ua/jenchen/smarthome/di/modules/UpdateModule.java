package com.ua.jenchen.smarthome.di.modules;

import com.google.android.things.update.UpdateManager;
import com.google.android.things.update.UpdatePolicy;
import com.ua.jenchen.smarthome.listeners.UpdateStatusListener;
import com.ua.jenchen.smarthome.services.WebSocketService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UpdateModule {

    @Singleton
    @Provides
    public UpdateManager updateManager() {
        UpdatePolicy policy = new UpdatePolicy.Builder()
                .setApplyDeadline(10L, TimeUnit.DAYS)
                .setPolicy(UpdatePolicy.POLICY_APPLY_AND_REBOOT)
                .build();
        UpdateManager manager = UpdateManager.getInstance();
        manager.setPolicy(policy);
        return manager;
    }

    @Singleton
    @Provides
    public UpdateStatusListener statusListener(WebSocketService service) {
        return new UpdateStatusListener(service);
    }
}
