package com.ua.jenchen.smarthome.server.controllers;

import com.ua.jenchen.models.HttpCodes;
import com.ua.jenchen.models.warmfloor.WarmFloorConfiguration;
import com.ua.jenchen.smarthome.services.WarmFloorService;

import javax.inject.Inject;

import io.javalin.http.Context;

public class WarmFloorController {

    private WarmFloorService warmFloorService;

    @Inject
    public WarmFloorController(WarmFloorService warmFloorService) {
        this.warmFloorService = warmFloorService;
    }

    public void getViews(Context request) {
        request.json(warmFloorService.getAllViews());
        request.status(HttpCodes.OK);
    }

    public void delete(Context request) {
        warmFloorService.deleteConfiguration(request.pathParam("uid"));
        request.status(HttpCodes.OK);
    }

    public void create(Context request) {
        warmFloorService.create(request.bodyAsClass(WarmFloorConfiguration.class));
        request.status(HttpCodes.CREATED);
    }

    public void changeState(Context request) {
        String uid = request.pathParam("uid");
        warmFloorService.changeState(uid);
        request.status(HttpCodes.NO_CONTENT);
    }

    public void changeTemperature(Context request) {
        String uid = request.pathParam("uid");
        float threshold = request.pathParam("value", Float.class).get();
        warmFloorService.changeThreshold(uid, threshold);
        request.status(HttpCodes.NO_CONTENT);
    }
}
