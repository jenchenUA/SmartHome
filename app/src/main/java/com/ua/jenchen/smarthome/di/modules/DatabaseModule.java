package com.ua.jenchen.smarthome.di.modules;

import android.content.Context;

import com.ua.jenchen.smarthome.database.AppDatabase;

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
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
    }
}
