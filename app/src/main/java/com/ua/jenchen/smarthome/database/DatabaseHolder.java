package com.ua.jenchen.smarthome.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import static com.ua.jenchen.models.AppConstants.DATABASE_NAME;

public class DatabaseHolder {

    private Context context;
    private AppDatabase database;
    private static DatabaseHolder holder;

    private DatabaseHolder(Context context) {
        this.context = context;
        this.database = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
    }

    public static DatabaseHolder getInstance(Context context) {
        if (holder == null) {
            synchronized (DatabaseHolder.class) {
                if (holder == null) {
                    holder = new DatabaseHolder(context);
                }
            }
        }
        return holder;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
