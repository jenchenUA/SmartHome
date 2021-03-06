package com.ua.jenchen.models.warmfloor;

import com.ua.jenchen.models.AppConstants;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = AppConstants.WARM_FLOOR_STATE_TABLE_NAME,
        indices = {@Index(value = "uid", unique = true)})
public class WarmFloorState {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "uid")
    private String uid;
    @ColumnInfo(name = "state")
    private boolean state;

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
