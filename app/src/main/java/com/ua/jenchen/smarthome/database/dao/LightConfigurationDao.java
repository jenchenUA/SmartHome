package com.ua.jenchen.smarthome.database.dao;

import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.LightConfiguration;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LightConfigurationDao {

    @Query("SELECT * FROM " + AppConstants.LIGHT_CONFIGURATION_TABLE_NAME)
    List<LightConfiguration> getAll();

    @Query("SELECT * FROM " + AppConstants.LIGHT_CONFIGURATION_TABLE_NAME)
    LiveData<List<LightConfiguration>> getAllAsLiveData();

    @Query("SELECT * FROM " + AppConstants.LIGHT_CONFIGURATION_TABLE_NAME + " WHERE uid LIKE :uid")
    LightConfiguration getByUid(String uid);

    @Query("SELECT * FROM " + AppConstants.LIGHT_CONFIGURATION_TABLE_NAME +
            " WHERE button_pin LIKE :buttonPin AND control_pin LIKE :controlPin")
    LightConfiguration getByButtonPinAndControlPin(String buttonPin, String controlPin);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LightConfiguration configuration);

    @Delete
    void delete(LightConfiguration configuration);

    @Query("DELETE FROM " + AppConstants.LIGHT_CONFIGURATION_TABLE_NAME + " WHERE uid = :uid")
    void deleteByUid(String uid);
}
