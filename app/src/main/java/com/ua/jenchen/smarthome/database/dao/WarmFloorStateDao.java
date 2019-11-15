package com.ua.jenchen.smarthome.database.dao;

import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.warmfloor.WarmFloorState;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WarmFloorStateDao {

    @Query("SELECT * FROM " + AppConstants.WARM_FLOOR_STATE_TABLE_NAME + " WHERE uid = :uid")
    WarmFloorState getByUid(String uid);

    @Query("UPDATE " + AppConstants.WARM_FLOOR_STATE_TABLE_NAME + " SET state = :state WHERE uid = :uid")
    void updateState(String uid, boolean state);

    @Insert
    void insert(WarmFloorState state);
}
