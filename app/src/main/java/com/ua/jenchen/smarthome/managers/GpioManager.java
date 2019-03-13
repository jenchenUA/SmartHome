package com.ua.jenchen.smarthome.managers;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.ua.jenchen.models.LightConfiguration;
import com.ua.jenchen.models.WarmFloorConfiguration;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class GpioManager {

    private static final String LOG_TAG = GpioManager.class.getSimpleName();

    private static GpioManager instance;
    private List<Gpio> gpios;
    private Map<String, Object> managers;

    private GpioManager() {
        gpios = new CopyOnWriteArrayList<>();
        managers = new ConcurrentHashMap<>();
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
            Gpio gpio = PeripheralManager.getInstance().openGpio(name);
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
            Gpio input = PeripheralManager.getInstance().openGpio(configuration.getInputPin());
            Gpio output = PeripheralManager.getInstance().openGpio(configuration.getOutputPin());
            LampManager manager = new LampManager(input, output,
                    configuration.isOutputHighActivation());
            managers.put(configuration.getUid(), manager);
            result = Optional.of(manager);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Lamp manager with input " + configuration.getInputPin()
                    + " and output " + configuration.getOutputPin() + " is unavailable", e);
        }
        return result;
    }

    public Optional<WarmFloorManager> makeWarmFloorManager(WarmFloorConfiguration configuration) {
        Optional<WarmFloorManager> result = Optional.empty();
        try {
            Gpio controlPin = PeripheralManager.getInstance().openGpio(configuration.getControlPin());
            Gpio swithcerPin = PeripheralManager.getInstance().openGpio(configuration.getSwircherPin());
            WarmFloorManager manager = new WarmFloorManager(controlPin, swithcerPin, configuration);
            managers.put(configuration.getUid(), manager);
            result = Optional.of(manager);
        } catch (IOException e) {

        }
        return result;
    }

    public List<LampManager> getLampManagers() {
        return managers.values().stream()
                .filter(value -> value instanceof LampManager)
                .map(value -> (LampManager) value)
                .collect(Collectors.toList());
    }

    public <T> T getManager(String uid) {
        return (T) managers.get(uid);
    }

    public boolean isNotManagerExist(String uid) {
        return !managers.containsKey(uid);
    }

    public void closeAllGpios() {
        for (Gpio gpio : gpios) {
            try {
                gpio.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "GPIO with name " + gpio.getName() + " can't be closed", e);
            }
        }
        gpios.clear();
        managers.clear();
    }
}
