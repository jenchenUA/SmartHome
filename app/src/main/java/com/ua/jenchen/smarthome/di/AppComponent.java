package com.ua.jenchen.smarthome.di;

import android.app.Application;
import android.content.Context;

import com.ua.jenchen.smarthome.activities.MainActivity;
import com.ua.jenchen.smarthome.di.modules.DatabaseModule;
import com.ua.jenchen.smarthome.di.modules.PeripheralModule;
import com.ua.jenchen.smarthome.di.modules.ServerModule;
import com.ua.jenchen.smarthome.di.modules.ServiceModule;
import com.ua.jenchen.smarthome.di.modules.UpdateModule;
import com.ua.jenchen.smarthome.listeners.LampStateValueEventListener;
import com.ua.jenchen.smarthome.listeners.LightButtonListener;
import com.ua.jenchen.smarthome.listeners.WarmFloorConfigurationValueListener;
import com.ua.jenchen.smarthome.listeners.WarmFloorStateValueListener;
import com.ua.jenchen.smarthome.managers.LampManager;
import com.ua.jenchen.smarthome.managers.WarmFloorManager;
import com.ua.jenchen.smarthome.server.Server;
import com.ua.jenchen.smarthome.workers.WarmFloorListener;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        DatabaseModule.class,
        ServerModule.class,
        ServiceModule.class,
        PeripheralModule.class,
        UpdateModule.class
})
public interface AppComponent {

    void inject(Server server);

    void inject(MainActivity mainActivity);

    void inject(LampStateValueEventListener lampStateValueEventListener);

    void inject(WarmFloorConfigurationValueListener warmFloorConfigurationValueListener);

    void inject(WarmFloorStateValueListener warmFloorStateValueListener);

    void inject(LightButtonListener lightButtonListener);

    void inject(LampManager lampManager);

    void inject(WarmFloorManager warmFloorManager);

    void inject(WarmFloorListener warmFloorListener);

    @Component.Builder
    interface Builder {

        AppComponent build();

        @BindsInstance
        Builder context(Context context);

        @BindsInstance
        Builder application(Application application);
    }
}
