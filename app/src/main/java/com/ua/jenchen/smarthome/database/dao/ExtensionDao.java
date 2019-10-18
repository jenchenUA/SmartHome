package com.ua.jenchen.smarthome.database.dao;

import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.converters.ExtensionTypeConverter;
import com.ua.jenchen.models.extensions.Extension;
import com.ua.jenchen.models.extensions.ExtensionType;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

@Dao
@TypeConverters({ExtensionTypeConverter.class})
public interface ExtensionDao {

    @Query("SELECT * FROM " + AppConstants.EXTENSION_TABLE_NAME)
    List<Extension> getAll();

    @Query("SELECT * FROM " + AppConstants.EXTENSION_TABLE_NAME + " WHERE type = :type")
    LiveData<List<Extension>> getByType(ExtensionType type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Extension extension);

    @Delete
    void delete(Extension extension);

    @Query("DELETE FROM " + AppConstants.EXTENSION_TABLE_NAME + " WHERE id = :id")
    void deleteById(int id);
}
