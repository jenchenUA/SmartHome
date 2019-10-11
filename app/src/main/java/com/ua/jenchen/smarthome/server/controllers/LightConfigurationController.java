package com.ua.jenchen.smarthome.server.controllers;

import com.ua.jenchen.models.HttpCodes;
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
        request.status(HttpCodes.CREATED);
    }

    public void getConfigurations(Context request) {
        request.json(service.getConfigurations());
        request.status(HttpCodes.OK);
    }

    public void getConfiguration(Context request) {
        service.getConfiguration(request.pathParam("uid"))
                .ifPresent(request::json);
        request.status(HttpCodes.OK);
    }

    public void deleteConfiguration(Context request) {
        service.deleteConfiguration(request.pathParam("uid"));
        request.status(HttpCodes.NO_CONTENT);
    }

    public void getLamps(Context request) {
        request.json(service.getLamps());
        request.status(HttpCodes.OK);
    }
}
