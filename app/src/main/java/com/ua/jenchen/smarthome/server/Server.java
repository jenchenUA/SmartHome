package com.ua.jenchen.smarthome.server;

import android.content.Context;

import com.ua.jenchen.smarthome.database.DatabaseHolder;
import com.ua.jenchen.smarthome.server.controllers.LightConfigurationController;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

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
    private LightConfigurationController lightConfigurationController;

    public Server(int port, Context context) {
        this.port = port;
        DatabaseHolder databaseHolder = DatabaseHolder.getInstance(context);
        this.lightConfigurationController = new LightConfigurationController(databaseHolder.getDatabase());
    }

    public Server(int port, Context context, String staticResourcesPath) {
        this(port, context);
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
