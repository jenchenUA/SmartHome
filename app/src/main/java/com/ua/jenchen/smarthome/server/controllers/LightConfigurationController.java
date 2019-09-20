package com.ua.jenchen.smarthome.server.controllers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.dao.LightConfiguratonDao;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.models.LightConfiguration;
import com.ua.jenchen.smarthome.database.AppDatabase;

import io.javalin.http.Context;

public class LightConfigurationController {

    private DatabaseReference databaseReference;
    private LightConfiguratonDao configuratonDao;

    private LightConfigurationController() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference(AppConstants.LIGHT_CONFIGURATION_TABLE_NAME);
    }

    public LightConfigurationController(AppDatabase database) {
        this();
        configuratonDao = database.lightConfiguratonDao();
    }

    public void createConfiguration(Context request) {
        LightConfiguration configuration = request.bodyAsClass(LightConfiguration.class);
        databaseReference.child(configuration.getUid()).setValue(configuration);
        configuratonDao.insert(configuration);
        request.status(201);
    }
}
