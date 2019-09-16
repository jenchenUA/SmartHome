package com.ua.jenchen.smarthome.database;

import androidx.room.RoomDatabase;

import com.ua.jenchen.dao.LightConfiguratonDao;

//@Database(exportSchema = false, entities = {LightConfiguration.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract LightConfiguratonDao lightConfiguratonDao();
}
