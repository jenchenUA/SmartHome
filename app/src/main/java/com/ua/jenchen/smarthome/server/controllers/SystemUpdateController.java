package com.ua.jenchen.smarthome.server.controllers;

import com.ua.jenchen.smarthome.services.SystemUpdateService;

import io.javalin.http.Context;

public class SystemUpdateController {

    private SystemUpdateService systemUpdateService;

    public SystemUpdateController(SystemUpdateService systemUpdateService) {
        this.systemUpdateService = systemUpdateService;
    }

    public void checkUpdate(Context request) {
        request.json(systemUpdateService.checkUpdate());
        request.status(200);
    }

    public void performUpdate(Context request) {
        request.json(systemUpdateService.performUpdate());
        request.status(200);
    }

    public void performUpdateAndReboot(Context request) {
        request.json(systemUpdateService.performUpdateAndReboot());
        request.status(200);
    }

    public void getCurrentVersion(Context request) {
        request.json(systemUpdateService.getCurrentVersion());
        request.status(200);
    }
}
