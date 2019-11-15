package com.ua.jenchen.smarthome.database;

import com.ua.jenchen.models.extensions.Extension;
import com.ua.jenchen.models.light.LampState;
import com.ua.jenchen.models.light.LightConfiguration;
import com.ua.jenchen.models.warmfloor.WarmFloorConfiguration;
import com.ua.jenchen.models.warmfloor.WarmFloorState;
import com.ua.jenchen.smarthome.database.dao.ExtensionDao;
import com.ua.jenchen.smarthome.database.dao.LampStateDao;
import com.ua.jenchen.smarthome.database.dao.LightConfigurationDao;
import com.ua.jenchen.smarthome.database.dao.WarmFloorDao;
import com.ua.jenchen.smarthome.database.dao.WarmFloorStateDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(exportSchema = false, entities = {LightConfiguration.class, LampState.class, Extension.class,
        WarmFloorConfiguration.class, WarmFloorState.class},
        version = 14)
public abstract class AppDatabase extends RoomDatabase {

    public abstract LightConfigurationDao lightConfigurationDao();

    public abstract LampStateDao lampStateDao();

    public abstract ExtensionDao extensionDao();

    public abstract WarmFloorDao warmFloorDao();

    public abstract WarmFloorStateDao warmFloorStateDao();
}
