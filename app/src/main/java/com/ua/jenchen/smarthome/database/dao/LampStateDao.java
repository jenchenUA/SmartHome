package com.ua.jenchen.smarthome.database.dao;

import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.light.LampState;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LampStateDao {

    @Query("SELECT * FROM " + AppConstants.LIGHT_STATE_TABLE_NAME)
    List<LampState> getAll();

    @Query("SELECT * FROM " + AppConstants.LIGHT_STATE_TABLE_NAME + " WHERE uid =:uid")
    LampState getByUid(String uid);

    @Query("UPDATE " + AppConstants.LIGHT_STATE_TABLE_NAME + " SET state = :state")
    void updateStateForAll(boolean state);

    @Query("UPDATE " + AppConstants.LIGHT_STATE_TABLE_NAME + " SET state = :state WHERE uid = :uid")
    void updateState(String uid, boolean state);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LampState state);

    @Delete
    void delete(LampState state);
}
