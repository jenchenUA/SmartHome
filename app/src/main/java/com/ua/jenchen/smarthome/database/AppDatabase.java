package com.ua.jenchen.smarthome.database;

import com.ua.jenchen.models.LampState;
import com.ua.jenchen.models.LightConfiguration;
import com.ua.jenchen.smarthome.database.dao.LampStateDao;
import com.ua.jenchen.smarthome.database.dao.LightConfigurationDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(exportSchema = false, entities = {LightConfiguration.class, LampState.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract LightConfigurationDao lightConfigurationDao();

    public abstract LampStateDao lampStateDao();
}
