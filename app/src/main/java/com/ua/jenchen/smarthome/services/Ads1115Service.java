package com.ua.jenchen.smarthome.services;

import android.util.Log;

import com.google.android.things.contrib.driver.adc.ads1xxx.Ads1xxx;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

public class Ads1115Service implements AdcService {

    private static final String LOG_TAG = Ads1115Service.class.getSimpleName();

    private static Ads1115Service instance;
    private Ads1xxx driver;

    private Ads1115Service() {
        PeripheralManager peripheralManager = PeripheralManager.getInstance();
        try {
            driver = new Ads1xxx(peripheralManager.getI2cBusList().get(0), Ads1xxx.Configuration.ADS1115);
            driver.setComparatorMode(Ads1xxx.COMPARATOR_MODE_1);
            driver.setInputRange(Ads1xxx.RANGE_6_144V);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Ads1115Service getInstance() {
        if (instance == null) {
            synchronized (Ads1115Service.class) {
                if (instance == null) {
                    instance = new Ads1115Service();
                }
            }
        }
        return instance;
    }

    @Override
    public float getVoltage(int channel) {
        try {
            return (float) driver.readSingleEndedVoltage(channel);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void close() {
        try {
            driver.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }
}
