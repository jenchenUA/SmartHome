package com.ua.jenchen.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.LightConfiguration;

import java.util.List;

@Dao
public interface LightConfiguratonDao {

    @Query("SELECT * FROM " + AppConstants.LIGHT_CONFIGURATION_TABLE_NAME)
    List<LightConfiguration> getAll();

    @Query("SELECT * FROM " + AppConstants.LIGHT_CONFIGURATION_TABLE_NAME + " WHERE uid LIKE :arg0")
    LightConfiguration getByUid(String uid);

    @Query("SELECT * FROM " + AppConstants.LIGHT_CONFIGURATION_TABLE_NAME +
            " WHERE input LIKE :arg0 AND output LIKE :arg1")
    LightConfiguration getByInputAndOutput(String input, String output);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LightConfiguration configuration);

    @Delete
    void delete(LightConfiguration configuration);
}
