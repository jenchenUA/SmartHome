package com.ua.jenchen.smarthome.server.controllers;

import com.ua.jenchen.smarthome.services.LampStateService;

import javax.inject.Inject;

import io.javalin.http.Context;

public class LampStateController {

    private LampStateService lampStateService;

    @Inject
    public LampStateController(LampStateService lampStateService) {
        this.lampStateService = lampStateService;
    }

    public void changeLampState(Context request) {
        lampStateService.changeStateInFirebase(request.pathParam("uid"));
        request.status(204);
    }
}
