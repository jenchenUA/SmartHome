package com.ua.jenchen.smarthome.managers;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.ua.jenchen.drivers.mcp23017.MCP23017;
import com.ua.jenchen.drivers.mcp23017.MCP23017GPIO;
import com.ua.jenchen.models.light.LightConfiguration;
import com.ua.jenchen.models.warmfloor.WarmFloorConfiguration;
import com.ua.jenchen.models.warmfloor.WarmFloorView;
import com.ua.jenchen.models.websockets.Channels;
import com.ua.jenchen.models.websockets.Events;
import com.ua.jenchen.models.websockets.Message;
import com.ua.jenchen.smarthome.services.WebSocketService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import androidx.annotation.Nullable;

public class GpioManager {

    private static final String LOG_TAG = GpioManager.class.getSimpleName();

    private Map<String, LampManager> lampManagers;
    private Map<String, WarmFloorManager> floorManagers;
    private PeripheralManager peripheralManager;
    private PeripheralGpioManager peripheralGpioManager;
    private Map<String, Gpio> unusedGpios;
    private WebSocketService webSocketService;
    private WarmFloorEventSender warmFloorEventSender;

    public GpioManager(PeripheralGpioManager manager, WebSocketService webSocketService) {
        peripheralManager = PeripheralManager.getInstance();
        peripheralGpioManager = manager;
        lampManagers = new ConcurrentHashMap<>();
        floorManagers = new ConcurrentHashMap<>();
        unusedGpios = new ConcurrentHashMap<>();
        this.webSocketService = webSocketService;
    }

    public Optional<Gpio> getGpio(String name) {
        Optional<Gpio> result = Optional.empty();
        try {
            Gpio gpio = peripheralManager.openGpio(name);
            result = Optional.of(gpio);
        } catch (IOException e) {
            Log.e(LOG_TAG, "GPIO with name " + name + " is unavailable", e);
        }
        return result;
    }

    private Gpio openGpio(String name, int address) {
        Optional<Object> gpioProvider = peripheralGpioManager.getGpioProvider(address);
        return gpioProvider.filter(PeripheralManager.class::isInstance)
                .map(PeripheralManager.class::cast)
                .map(manager -> openDeviceGpio(name, manager))
                .orElseGet(() -> gpioProvider.filter(MCP23017.class::isInstance)
                        .map(MCP23017.class::cast)
                        .map(manager -> openMcp23017Gpio(name, manager))
                        .orElse(null)
                );

    }

    private Gpio openDeviceGpio(String name, PeripheralManager manager) {
        try {
            Gpio gpio = unusedGpios.get(name);
            if (gpio == null) {
                gpio = manager.openGpio(name);
            } else {
                unusedGpios.remove(name);
            }
            return gpio;
        } catch (IOException e) {
            Log.e(LOG_TAG, "GPIO " + name + " can't be opened", e);
        }
        return null;
    }

    @Nullable
    private Gpio openMcp23017Gpio(String name, MCP23017 manager) {
        try {
            return manager.openGpio(Arrays.stream(MCP23017GPIO.values())
                    .filter(value -> value.getName().equals(name))
                    .findAny()
                    .orElseThrow(IOException::new));
        } catch (Throwable e) {
            Log.e(LOG_TAG, "GPIO " + name + " can't be opened", e);
        }
        return null;
    }

    public Optional<LampManager> makeLampManager(LightConfiguration configuration) {
        Optional<LampManager> result = Optional.empty();
        Gpio buttonPin = openGpio(configuration.getButtonPin(), configuration.getGpioAddress());
        Gpio controlPin = openGpio(configuration.getControlPin(), configuration.getGpioAddress());
        if (buttonPin != null && controlPin != null) {
            LampManager manager = new LampManager(buttonPin, controlPin, configuration);
            lampManagers.put(configuration.getUid(), manager);
            result = Optional.of(manager);
        }
        Log.i(LOG_TAG, String.format("Lamp control was created for button %s, control %s and label %s",
                configuration.getButtonPin(), configuration.getControlPin(), configuration.getLabel()));
        return result;
    }

    public void destroyLampManager(String uid) {
        Optional.ofNullable(lampManagers.get(uid)).ifPresent(manager -> {
            lampManagers.remove(uid);
            manager.releaseGpios().forEach(gpio -> unusedGpios.put(gpio.getName(), gpio));
            Log.i(LOG_TAG,
                    String.format("Lamp manager with uid: %s and label: %s was destroyed", uid, manager.getLabel()));
        });
    }

    public void destroyWarmFloorManager(String uid) {
        Optional.ofNullable(floorManagers.get(uid)).ifPresent(manager -> {
            floorManagers.remove(uid);
            manager.releaseGpios().forEach(gpio -> unusedGpios.put(gpio.getName(), gpio));
            Log.i(LOG_TAG,
                    String.format("Lamp manager with uid: %s and label: %s was destroyed", uid, manager.getLabel()));
            if (floorManagers.isEmpty()) {
                warmFloorEventSender.shutdown();
                warmFloorEventSender = null;
            }
        });
    }

    public boolean makeWarmFloorManager(WarmFloorConfiguration configuration) {
        Gpio buttonPin = openGpio(configuration.getSwitcherPin(), configuration.getGpioAddress());
        Gpio controlPin = openGpio(configuration.getControlPin(), configuration.getGpioAddress());
        if (buttonPin != null && controlPin != null) {
            try {
                WarmFloorManager manager = new WarmFloorManager(controlPin, buttonPin, configuration);
                floorManagers.put(configuration.getUid(), manager);
                if (warmFloorEventSender == null) {
                    warmFloorEventSender = new WarmFloorEventSender();
                    CompletableFuture.runAsync(warmFloorEventSender);
                }
                return true;
            } catch (IllegalStateException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }
        }
        return false;
    }

    public List<LampManager> getLampManagers() {
        return new ArrayList<>(lampManagers.values());
    }

    public LampManager getLampManager(String uid) {
        return lampManagers.get(uid);
    }

    public Optional<WarmFloorManager> getWarmFloorManager(String uid) {
        return Optional.ofNullable(floorManagers.get(uid));
    }

    public boolean isNotManagerExist(String uid) {
        return !lampManagers.containsKey(uid) && !floorManagers.containsKey(uid);
    }

    public boolean isWarmFloorManagerExist(String uid) {
        return floorManagers.containsKey(uid);
    }

    public void closeAllGpios() {
        lampManagers.values().forEach(this::close);
        floorManagers.values().forEach(this::close);
        lampManagers.clear();
        unusedGpios.values().forEach(this::close);
        unusedGpios.clear();
        peripheralGpioManager.closeGpioExpanders();
    }

    private void close(AutoCloseable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Can't be closed object: " + closeable);
        }
    }

    private class WarmFloorEventSender implements Runnable {

        private boolean shutdown = false;

        @Override
        public void run() {
            while (!shutdown) {
                List<WarmFloorView> views = floorManagers.values().stream()
                        .map(WarmFloorManager::toView)
                        .collect(Collectors.toList());
                Message<List<WarmFloorView>> message = new Message<>(Events.WARM_FLOOR.getCode(), views);
                webSocketService.publish(Channels.UPDATES.getCode(), message);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                }
            }
        }

        public void shutdown() {
            this.shutdown = true;
        }
    }
}
