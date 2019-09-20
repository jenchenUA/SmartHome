package com.ua.jenchen.smarthome.di;

import android.app.Application;
import android.content.Context;

import com.ua.jenchen.smarthome.activities.MainActivity;
import com.ua.jenchen.smarthome.di.modules.DatabaseModule;
import com.ua.jenchen.smarthome.di.modules.ServerModule;
import com.ua.jenchen.smarthome.di.modules.ServiceModule;
import com.ua.jenchen.smarthome.server.Server;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {DatabaseModule.class, ServerModule.class, ServiceModule.class})
public interface AppComponent {

    void inject(Server server);

    void inject(MainActivity mainActivity);

    @Component.Builder
    interface Builder {

        AppComponent build();

        @BindsInstance
        Builder context(Context context);

        @BindsInstance
        Builder application(Application application);
    }
}
