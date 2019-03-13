package com.ua.jenchen.smarthome.workers;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.ua.jenchen.smarthome.managers.WarmFloorManager;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class WarmFloorListener extends AsyncTask<Void, Void, Void> {

    private static final String LOG_TAG = WarmFloorListener.class.getSimpleName();

    private WarmFloorManager manager;
    private Gpio controlPin;
    private boolean shutdown;
    private AtomicInteger temperatureThreshold;

    public WarmFloorListener(WarmFloorManager manager, Gpio controlPin, float threshold) {
        this.manager = manager;
        this.controlPin = controlPin;
        this.shutdown = false;
        this.temperatureThreshold = new AtomicInteger(Float.floatToIntBits(threshold));
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            while (!shutdown) {
                double temperatureInCelsius = manager.getTemperatureInCelsius();
                Log.i(LOG_TAG, "Temperature: " + temperatureInCelsius);
                float threshold = Float.intBitsToFloat(temperatureThreshold.get());
                int i = BigDecimal.valueOf(temperatureInCelsius).compareTo(BigDecimal.valueOf(threshold));
                if (i >= 0 && controlPin.getValue()) {
                    Log.i(LOG_TAG, "Warm floor with label: " + temperatureInCelsius);
                    controlPin.setValue(false);
                } else if (i < 0 && !controlPin.getValue()) {
                    controlPin.setValue(true);
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Temperature listening error", e);
        }
        return null;
    }

    public void setThreshold(float threshold) {
        temperatureThreshold.set(Float.floatToIntBits(threshold));
    }

    public void shutdown() {
        this.shutdown = true;
    }
}
