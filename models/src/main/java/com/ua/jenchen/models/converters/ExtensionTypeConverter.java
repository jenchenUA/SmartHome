package com.ua.jenchen.models.converters;

import com.ua.jenchen.models.extensions.ExtensionType;

import androidx.room.TypeConverter;

public class ExtensionTypeConverter {

    @TypeConverter
    public String fromExtensionType(ExtensionType extensionType) {
        return extensionType.toString();
    }

    @TypeConverter
    public ExtensionType toExtensionType(String extensionType) {
        return ExtensionType.valueOf(extensionType);
    }
}
