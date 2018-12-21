package com.ua.jenchen.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = AppConstants.LIGHT_CONFIGURATION_TABLE_NAME,
        indices = {@Index(value = "uid", unique = true)})
public class LampState {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "uid")
    private String uid;
    @ColumnInfo(name = "state")
    private boolean state;

    public LampState() {
    }

    public LampState(String uid, boolean state) {
        this.uid = uid;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
