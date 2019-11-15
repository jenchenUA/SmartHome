package com.ua.jenchen.smarthome.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ua.jenchen.smarthome.application.SmartHomeApplication;
import com.ua.jenchen.smarthome.server.controllers.ExtensionController;
import com.ua.jenchen.smarthome.server.controllers.LampStateController;
import com.ua.jenchen.smarthome.server.controllers.LightConfigurationController;
import com.ua.jenchen.smarthome.server.controllers.SystemUpdateController;
import com.ua.jenchen.smarthome.server.controllers.WarmFloorController;
import com.ua.jenchen.smarthome.services.WebSocketService;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

import javax.inject.Inject;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import io.javalin.core.util.CorsPlugin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.json.JavalinJson;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

public class Server {

    private static Javalin server;
    private String staticResourcesPath;
    private int port;
    @Inject
    LightConfigurationController lightConfigurationController;
    @Inject
    LampStateController lampStateController;
    @Inject
    SystemUpdateController systemUpdateController;
    @Inject
    WebSocketService webSocketService;
    @Inject
    ExtensionController extensionController;
    @Inject
    WarmFloorController warmFloorController;

    public Server(int port) {
        SmartHomeApplication.appComponent.inject(this);
        this.port = port;
    }

    public Server(int port, String staticResourcesPath) {
        this(port);
        this.staticResourcesPath = staticResourcesPath;
    }

    public void run() {
        Gson gson = new GsonBuilder()
                .create();
        server = Javalin.create(getConfig()).start(port);
        CorsPlugin.forAllOrigins().apply(server);
        JavalinJson.setFromJsonMapper(gson::fromJson);
        JavalinJson.setToJsonMapper(gson::toJson);
        server.routes(() -> {
            path("light", () -> {
                get(lightConfigurationController::getLamps);
                path("configuration", () -> {
                    post(lightConfigurationController::createConfiguration);
                    get(lightConfigurationController::getConfigurations);
                    get(":uid", lightConfigurationController::getConfiguration);
                    delete(":uid", lightConfigurationController::deleteConfiguration);
                });
                path(":uid/state", () -> {
                    put(lampStateController::changeLampState);
                });
            });
            path("system/update", () -> {
                get(systemUpdateController::checkUpdate);
                path("perform", () -> get(systemUpdateController::performUpdate));
                path("reboot", () -> get(systemUpdateController::performUpdateAndReboot));
                path("version", () -> get(systemUpdateController::getCurrentVersion));
            });
            path("extension", () -> {
                get(extensionController::getExtensions);
                post(extensionController::createExtension);
                path(":uid", () -> {
                    delete(extensionController::deleteExtension);
                });
                path("adc/online", () -> {
                    get(extensionController::getAvailableAdc);
                });
                path("gpio/online", () -> {
                    get(extensionController::getAvailableGpioProviders);
                });
            });
            path("warm-floor", () -> {
                path(":uid", () -> {
                    put("/state", warmFloorController::changeState);
                    put("temperature/:value", warmFloorController::changeTemperature);
                });
                path("views", () -> {
                    get(warmFloorController::getViews);
                });
                path("configuration", () -> {
                    post(warmFloorController::create);
                    delete(":uid", warmFloorController::delete);
                });
            });
        });
        server.ws(":channel", ws -> {
            ws.onConnect(webSocketService::subscribe);
            ws.onClose(webSocketService::unsubscribe);
        });
    }

    @NotNull
    private Consumer<JavalinConfig> getConfig() {
        return config -> {
            Optional.ofNullable(staticResourcesPath).ifPresent(path -> config.addStaticFiles(path, Location.EXTERNAL));
        };
    }
}
