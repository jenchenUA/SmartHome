package com.ua.jenchen.smarthome.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ua.jenchen.dao.LightConfiguratonDao;
import com.ua.jenchen.models.LightConfiguration;

@Database(entities = {LightConfiguration.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract LightConfiguratonDao lightConfiguratonDao();
}
