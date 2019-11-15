package com.ua.jenchen.smarthome.database.dao;

import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.warmfloor.WarmFloorConfiguration;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WarmFloorDao {

    @Query("SELECT * FROM " + AppConstants.WARM_FLOOR_CONFIGURATION_TABLE_NAME)
    List<WarmFloorConfiguration> getAllConfigurations();

    @Query("SELECT * FROM " + AppConstants.WARM_FLOOR_CONFIGURATION_TABLE_NAME)
    LiveData<List<WarmFloorConfiguration>> getAllLiveData();

    @Query("UPDATE " + AppConstants.WARM_FLOOR_CONFIGURATION_TABLE_NAME + " SET threshold = :threshold WHERE uid = " +
            ":uid")
    void setThreshold(String uid, float threshold);

    @Insert
    void insert(WarmFloorConfiguration configuration);

    @Delete
    void delete(WarmFloorConfiguration configuration);

    @Query("DELETE FROM " + AppConstants.WARM_FLOOR_CONFIGURATION_TABLE_NAME + " WHERE uid = :uid")
    void deleteByUid(String uid);
}
