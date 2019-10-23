package com.ua.jenchen.models.extensions;

import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.converters.ExtensionTypeConverter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = AppConstants.EXTENSION_TABLE_NAME,
        indices = {@Index(value = {"type", "address"}, unique = true),
                @Index(value = "address", unique = true)})
public class Extension {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "type")
    @TypeConverters({ExtensionTypeConverter.class})
    private ExtensionType type;
    @ColumnInfo(name = "address")
    private int address;
    private boolean online;

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

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
