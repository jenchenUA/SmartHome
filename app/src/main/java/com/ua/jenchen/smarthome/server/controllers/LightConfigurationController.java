package com.ua.jenchen.smarthome.server.controllers;

import com.ua.jenchen.models.light.LightConfiguration;
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

    public void getConfigurations(Context request) {
        request.json(service.getConfigurations());
        request.status(200);
    }

    public void getConfiguration(Context request) {
        service.getConfiguration(request.pathParam("uid"))
                .ifPresent(request::json);
        request.status(200);
    }

    public void deleteConfiguration(Context request) {
        service.deleteConfiguration(request.pathParam("uid"));
        request.status(204);
    }

    public void getLamps(Context request) {
        request.json(service.getLamps());
        request.status(200);
    }
}
