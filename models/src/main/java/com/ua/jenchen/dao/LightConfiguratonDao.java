package com.ua.jenchen.dao;

import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.LightConfiguration;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LightConfiguratonDao {

    @Query("SELECT * FROM " + AppConstants.LIGHT_CONFIGURATION_TABLE_NAME)
    List<LightConfiguration> getAll();

    @Query("SELECT * FROM " + AppConstants.LIGHT_CONFIGURATION_TABLE_NAME + " WHERE uid LIKE :arg0")
    LightConfiguration getByUid(String uid);

    @Query("SELECT * FROM " + AppConstants.LIGHT_CONFIGURATION_TABLE_NAME +
            " WHERE button_pin LIKE :arg0 AND control_pin LIKE :arg1")
    LightConfiguration getByButtonPinAndControlPin(String buttonPin, String controlPin);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LightConfiguration configuration);

    @Delete
    void delete(LightConfiguration configuration);
}
