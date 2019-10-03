package com.ua.jenchen.models.light;

import com.ua.jenchen.models.AppConstants;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = AppConstants.LIGHT_STATE_TABLE_NAME,
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

    @Ignore
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
