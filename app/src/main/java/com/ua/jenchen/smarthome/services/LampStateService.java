package com.ua.jenchen.smarthome.services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.light.LampState;
import com.ua.jenchen.smarthome.database.dao.LampStateDao;
import com.ua.jenchen.smarthome.managers.GpioManager;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class LampStateService {

    private LampStateDao dao;
    private DatabaseReference reference;
    private GpioManager gpioManager;

    @Inject
    public LampStateService(LampStateDao dao, FirebaseDatabase firebaseDatabase, GpioManager gpioManager) {
        this.gpioManager = gpioManager;
        this.dao = dao;
        this.reference = firebaseDatabase.getReference(AppConstants.LIGHT_STATE_TABLE_NAME);
    }

    public void disableAllLight() {
        reference.setValue(null);
        CompletableFuture.runAsync(() -> dao.updateStateForAll(false));
    }

    public void changeStateInFirebase(String uid) {
        LampState state = dao.getByUid(uid);
        if (state == null) {
            state = new LampState(uid, false);
        }
        state.setState(!state.getState());
        reference.child(uid).setValue(state);
    }

    public void changeStateInDB(String uid, boolean state) {
        CompletableFuture.runAsync(() -> dao.updateState(uid, state));
    }

    public LampState getState(String uid) {
        return dao.getByUid(uid);
    }
}
