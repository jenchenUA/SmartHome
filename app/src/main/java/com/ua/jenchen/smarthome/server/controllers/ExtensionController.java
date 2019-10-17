package com.ua.jenchen.smarthome.server.controllers;

import com.ua.jenchen.models.HttpCodes;
import com.ua.jenchen.models.extensions.Extension;
import com.ua.jenchen.smarthome.services.ExtensionService;

import javax.inject.Inject;

import io.javalin.core.validation.Validator;
import io.javalin.http.Context;

public class ExtensionController {

    private ExtensionService extensionService;

    @Inject
    public ExtensionController(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }

    public void getExtensions(Context request) {
        request.json(extensionService.getAllExtensions());
        request.status(HttpCodes.OK);
    }

    public void createExtension(Context request) {
        Extension extension = request.bodyAsClass(Extension.class);
        extensionService.create(extension);
        request.status(HttpCodes.CREATED);
    }

    public void deleteExtension(Context request) {
        Validator<Integer> uid = request.pathParam("uid", Integer.class);
        Integer id = uid.getValue();
        extensionService.removeById(id);
        request.status(HttpCodes.CREATED);
    }
}
