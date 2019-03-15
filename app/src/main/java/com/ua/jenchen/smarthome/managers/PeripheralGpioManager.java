package com.ua.jenchen.smarthome.managers;

import android.util.Log;

import com.google.android.things.pio.PeripheralManager;
import com.ua.jenchen.drivers.mcp23017.MCP23017;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PeripheralGpioManager {

    private static final String LOG_TAG = PeripheralGpioManager.class.getSimpleName();

    private static PeripheralGpioManager instance;

    private Map<Integer, Object> gpioProviders;

    private PeripheralGpioManager() {
        this.gpioProviders = new ConcurrentHashMap<>();
        gpioProviders.put(0, PeripheralManager.getInstance());
    }

    public static PeripheralGpioManager getInstance() {
        if (instance == null) {
            synchronized (GpioManager.class) {
                if (instance == null) {
                    instance = new PeripheralGpioManager();
                }
            }
        }
        return instance;
    }

    public void createGpioProvider(int address) {
        List<String> i2cBusList = PeripheralManager.getInstance().getI2cBusList();
        try {
            MCP23017 mcp23017 = new MCP23017(i2cBusList.get(0), address);
            gpioProviders.put(address, mcp23017);
        } catch (IOException e) {
            Log.e(LOG_TAG, "GPIO provider can't be created", e);
        }
    }

    public boolean isProviderExists(int address) {
        return gpioProviders.containsKey(address);
    }

    public Optional<Object> getGpioProvider(int address) {
        return Optional.ofNullable(gpioProviders.get(address));
    }
}
