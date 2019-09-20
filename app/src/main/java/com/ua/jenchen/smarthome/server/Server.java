package com.ua.jenchen.smarthome.server;

import com.ua.jenchen.smarthome.application.SmartHomeApplication;
import com.ua.jenchen.smarthome.server.controllers.LightConfigurationController;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

import javax.inject.Inject;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import io.javalin.core.util.CorsPlugin;
import io.javalin.http.staticfiles.Location;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class Server {

    private static Javalin server;
    private String staticResourcesPath;
    private int port;
    @Inject
    LightConfigurationController lightConfigurationController;

    public Server(int port) {
        SmartHomeApplication.appComponent.inject(this);
        this.port = port;
    }

    public Server(int port, String staticResourcesPath) {
        this(port);
        this.staticResourcesPath = staticResourcesPath;
    }

    public void run() {
        server = Javalin.create(getConfig()).start(port);
        CorsPlugin.forAllOrigins().apply(server);
        server.routes(() -> {
            path("light", () -> {
                path("config", () -> {
                    post(lightConfigurationController::createConfiguration);
                });
            });
        });
    }

    @NotNull
    private Consumer<JavalinConfig> getConfig() {
        return config -> {
            Optional.ofNullable(staticResourcesPath).ifPresent(path -> config.addStaticFiles(path, Location.EXTERNAL));
        };
    }
}
