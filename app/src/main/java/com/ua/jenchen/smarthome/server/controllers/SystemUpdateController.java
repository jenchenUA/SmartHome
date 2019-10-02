package com.ua.jenchen.smarthome.server.controllers;

import com.google.android.things.update.VersionInfo;
import com.ua.jenchen.smarthome.services.SystemUpdateService;

import io.javalin.http.Context;

public class SystemUpdateController {

    private SystemUpdateService systemUpdateService;

    public SystemUpdateController(SystemUpdateService systemUpdateService) {
        this.systemUpdateService = systemUpdateService;
    }

    public void checkUpdate(Context request) {
        VersionInfo version = systemUpdateService.checkUpdate();
        request.json(version);
        request.status(204);
    }

    public void performUpdate(Context request) {
        systemUpdateService.performUpdate();
        request.status(204);
    }

    public void performUpdateAndReboot(Context request) {
        systemUpdateService.performUpdateAndReboot();
        request.status(204);
    }
}
