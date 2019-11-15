package com.ua.jenchen.smarthome.managers;

import android.util.Log;

import com.google.android.things.contrib.driver.adc.ads1xxx.Ads1xxx;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AdcManager {

    private static final String LOG_TAG = AdcManager.class.getSimpleName();

    private Map<Integer, Ads1xxx> converters;

    public AdcManager() {
        converters = new ConcurrentHashMap<>();
    }

    public boolean createAds1115(int address) {
        PeripheralManager peripheralManager = PeripheralManager.getInstance();
        try {
            Ads1xxx driver = new Ads1xxx(peripheralManager.getI2cBusList().get(0), address,
                    Ads1xxx.Configuration.ADS1115);
            driver.setInputRange(Ads1xxx.RANGE_6_144V);
            converters.put(address, driver);
            Log.i(LOG_TAG, String.format("ADS1115 available on the address %s", address));
            return true;
        } catch (IOException e) {
            Log.e(LOG_TAG, "ADS1115 can't be created", e);
            return false;
        }
    }

    public boolean isAds1115Configured(int address) {
        return converters.containsKey(address);
    }

    public Ads1xxx getAds1115ByAddress(int address) {
        return converters.get(address);
    }

    public void close() {
        converters.values().forEach(this::close);
        converters.clear();
    }

    private void close(AutoCloseable autoCloseable) {
        try {
            autoCloseable.close();
        } catch (Exception e) {
            Log.e(LOG_TAG, "ADS1115 can't be closed", e);
        }
    }

    public Set<Integer> getOnlineAdcs() {
        return converters.keySet();
    }
}
