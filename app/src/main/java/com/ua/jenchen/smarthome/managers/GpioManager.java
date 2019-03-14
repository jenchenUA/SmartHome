package com.ua.jenchen.smarthome.managers;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.ua.jenchen.models.LightConfiguration;
import com.ua.jenchen.models.WarmFloorConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class GpioManager {

    private static final String LOG_TAG = GpioManager.class.getSimpleName();

    private static GpioManager instance;
    private List<Gpio> gpios;
    private Map<String, LampManager> lampManagers;
    private Map<String, WarmFloorManager> floorManagers;
    private PeripheralManager peripheralManager;

    private GpioManager() {
        peripheralManager = PeripheralManager.getInstance();
        gpios = new CopyOnWriteArrayList<>();
        lampManagers = new ConcurrentHashMap<>();
        floorManagers = new ConcurrentHashMap<>();
    }

    public static GpioManager getInstance() {
        if (instance == null) {
            synchronized (GpioManager.class) {
                if (instance == null) {
                    instance = new GpioManager();
                }
            }
        }
        return instance;
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

    public Optional<LampManager> makeLampManager(LightConfiguration configuration) {
        Optional<LampManager> result = Optional.empty();
        try {
            Gpio buttonPin = peripheralManager.openGpio(configuration.getButtonPin());
            Gpio controlPin = peripheralManager.openGpio(configuration.getControlPin());
            gpios.add(controlPin);
            LampManager manager = new LampManager(buttonPin, controlPin, configuration);
            lampManagers.put(configuration.getUid(), manager);
            result = Optional.of(manager);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Lamp manager with input " + configuration.getButtonPin()
                    + " and output " + configuration.getControlPin() + " is unavailable", e);
        }
        return result;
    }

    public Optional<WarmFloorManager> makeWarmFloorManager(WarmFloorConfiguration configuration) {
        Optional<WarmFloorManager> result = Optional.empty();
        try {
            Gpio controlPin = peripheralManager.openGpio(configuration.getControlPin());
            gpios.add(controlPin);
            Gpio buttonPin = peripheralManager.openGpio(configuration.getSwircherPin());
            WarmFloorManager manager = new WarmFloorManager(controlPin, buttonPin, configuration);
            floorManagers.put(configuration.getUid(), manager);
            result = Optional.of(manager);
        } catch (IOException e) {

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
        return !lampManagers.containsKey(uid);
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
