package com.ua.jenchen.smarthome.server.controllers;

import com.ua.jenchen.models.LightConfiguration;
import com.ua.jenchen.smarthome.services.LightConfigurationService;

import javax.inject.Inject;

import io.javalin.http.Context;

public class LightConfigurationController {

    private LightConfigurationService service;


    @Inject
    public LightConfigurationController(LightConfigurationService service) {
        this.service = service;
    }

    public void createConfiguration(Context request) {
        service.create(request.bodyAsClass(LightConfiguration.class));
        request.status(201);
    }
}
