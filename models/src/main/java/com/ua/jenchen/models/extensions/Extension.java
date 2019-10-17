package com.ua.jenchen.models.extensions;

import com.ua.jenchen.models.AppConstants;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = AppConstants.LIGHT_STATE_TABLE_NAME,
        indices = {@Index(value = {"type", "address"}, unique = true)})
public class Extension {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "type")
    private ExtensionType type;
    @ColumnInfo(name = "address")
    private int address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ExtensionType getType() {
        return type;
    }

    public void setType(ExtensionType type) {
        this.type = type;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
}
