package com.ua.jenchen.smarthome.di.modules;

import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.smarthome.database.AppDatabase;
import com.ua.jenchen.smarthome.database.dao.ExtensionDao;
import com.ua.jenchen.smarthome.database.dao.LampStateDao;
import com.ua.jenchen.smarthome.database.dao.LightConfigurationDao;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

import static com.ua.jenchen.models.AppConstants.DATABASE_NAME;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    public AppDatabase appDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    public LightConfigurationDao configurationDao(AppDatabase database) {
        return database.lightConfigurationDao();
    }

    @Provides
    @Singleton
    public LampStateDao lampStateDao(AppDatabase database) {
        return database.lampStateDao();
    }


    @Provides
    @Singleton
    public ExtensionDao extensionDao(AppDatabase database) {
        return database.extensionDao();
    }

    @Provides
    @Singleton
    public FirebaseDatabase firebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }
}
