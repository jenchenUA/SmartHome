package com.ua.jenchen.smarthome.managers;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.ua.jenchen.drivers.mcp23017.MCP23017;
import com.ua.jenchen.drivers.mcp23017.MCP23017GPIO;
import com.ua.jenchen.models.LightConfiguration;
import com.ua.jenchen.models.WarmFloorConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.Nullable;

public class GpioManager {

    private static final String LOG_TAG = GpioManager.class.getSimpleName();

    private static GpioManager instance;
    private List<Gpio> gpios;
    private Map<String, LampManager> lampManagers;
    private Map<String, WarmFloorManager> floorManagers;
    private PeripheralManager peripheralManager;
    private PeripheralGpioManager peripheralGpioManager;

    public GpioManager(PeripheralGpioManager manager) {
        peripheralManager = PeripheralManager.getInstance();
        peripheralGpioManager = manager;
        gpios = new CopyOnWriteArrayList<>();
        lampManagers = new ConcurrentHashMap<>();
        floorManagers = new ConcurrentHashMap<>();
    }

    public Optional<Gpio> getGpio(String name) {
        Optional<Gpio> result = Optional.empty();
        try {
            Gpio gpio = peripheralManager.openGpio(name);
            gpios.add(gpio);
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
            return manager.openGpio(name);
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
            manager.close();
            Log.i(LOG_TAG,
                    String.format("Lamp manager with uid: %s and label: %s was destroyed", uid, manager.getLabel()));
        });
    }

    public Optional<WarmFloorManager> makeWarmFloorManager(WarmFloorConfiguration configuration) {
        Optional<WarmFloorManager> result = Optional.empty();
        Gpio buttonPin = openGpio(configuration.getSwircherPin(), configuration.getGpioAddress());
        Gpio controlPin = openGpio(configuration.getControlPin(), configuration.getGpioAddress());
        if (buttonPin != null && controlPin != null) {
            gpios.add(controlPin);
            WarmFloorManager manager = new WarmFloorManager(controlPin, buttonPin, configuration);
            floorManagers.put(configuration.getUid(), manager);
            result = Optional.of(manager);
        }
        return result;
    }

    public List<LampManager> getLampManagers() {
        return new ArrayList<>(lampManagers.values());
    }

    public LampManager getLampManager(String uid) {
        return lampManagers.get(uid);
    }

    public WarmFloorManager getWarmFloorManager(String uid) {
        return floorManagers.get(uid);
    }

    public boolean isNotManagerExist(String uid) {
        return !lampManagers.containsKey(uid) && !floorManagers.containsKey(uid);
    }

    public void closeAllGpios() {
        lampManagers.values().forEach(this::close);
        floorManagers.values().forEach(this::close);
        gpios.forEach(this::close);
        gpios.clear();
        lampManagers.clear();
    }

    private void close(AutoCloseable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Can't be closed object: " + closeable);
        }
    }
}
