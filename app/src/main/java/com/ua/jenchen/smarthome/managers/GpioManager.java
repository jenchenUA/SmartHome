package com.ua.jenchen.smarthome.managers;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GpioManager {

    private static final String LOG_TAG = GpioManager.class.getSimpleName();

    private static GpioManager instance;
    private List<Gpio> gpios;

    private GpioManager() {
        gpios = new ArrayList<>();
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

    public Optional<LampManager> makeLampManager(String inputName, String outputName) {
        Optional<LampManager> result = Optional.empty();
        try {
            Gpio input = PeripheralManager.getInstance().openGpio(inputName);
            Gpio output = PeripheralManager.getInstance().openGpio(outputName);
            result = Optional.of(new LampManager(input, output));
        } catch (IOException e) {
            Log.e(LOG_TAG, "Lamp manager with input "
                    + inputName + " and output " + outputName + " is unavailable", e);
        }
        return result;
    }

    public void closeAllGpios() {
        for (Gpio gpio : gpios) {
            try {
                gpio.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "GPIO with name " + gpio.getName() + " can't be closed", e);
            }
        }
    }
}
