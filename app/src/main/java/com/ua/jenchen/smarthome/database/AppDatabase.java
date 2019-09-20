package com.ua.jenchen.smarthome.database;

import com.ua.jenchen.dao.LightConfiguratonDao;
import com.ua.jenchen.models.LightConfiguration;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(exportSchema = false, entities = {LightConfiguration.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract LightConfiguratonDao lightConfiguratonDao();
}
