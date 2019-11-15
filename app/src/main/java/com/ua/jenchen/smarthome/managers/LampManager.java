package com.ua.jenchen.smarthome.managers;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.ua.jenchen.models.light.LightConfiguration;
import com.ua.jenchen.models.light.LightView;
import com.ua.jenchen.models.websockets.Channels;
import com.ua.jenchen.models.websockets.Events;
import com.ua.jenchen.models.websockets.Message;
import com.ua.jenchen.smarthome.application.SmartHomeApplication;
import com.ua.jenchen.smarthome.button.Button;
import com.ua.jenchen.smarthome.listeners.LightButtonListener;
import com.ua.jenchen.smarthome.services.WebSocketService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class LampManager implements AutoCloseable {

    private static final String LOG_TAG = LampManager.class.getSimpleName();

    private Button button;
    private Gpio controlPin;
    private Gpio buttonPin;
    private LightConfiguration configuration;
    @Inject
    WebSocketService socketService;

    public LampManager(Gpio buttonPin, Gpio controlPin, LightConfiguration configuration) {
        try {
            SmartHomeApplication.appComponent.inject(this);
            this.controlPin = configureOutput(controlPin);
            this.buttonPin = buttonPin;
            button = new Button(buttonPin, Button.LogicState.PRESSED_WHEN_LOW);
            button.setOnButtonEventListener(new LightButtonListener(getUid(), controlPin));
            this.configuration = configuration;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Lamp manager can't be created", e);
        }
    }

    public void changeStateOfLamp(boolean state) {
        try {
            controlPin.setValue(state);
            broadcastEvent(state);
            Log.i(LOG_TAG,"State of lamp with label: " + getLabel() + " was changed");
        } catch (IOException e) {
            Log.e(LOG_TAG, "State of lamp can't be changed", e);
        }
    }

    private void broadcastEvent(boolean state) {
        Message<LightView> message = new Message<>(Events.LIGHT_STATE.getCode(), new LightView(getUid(), state));
        socketService.publishAsync(Channels.UPDATES.getCode(), message);
    }

    @Override
    public void close() throws Exception {
        disable(controlPin);
        button.close();
        controlPin.close();
    }

    public String getLabel() {
        return configuration.getLabel();
    }

    public List<Gpio> releaseGpios() {
        button.unregisterCallback();
        disable(controlPin);
        return Arrays.asList(buttonPin, controlPin);
    }

    private String getUid() {
        return button.getName() + controlPin.getName();
    }

    private Gpio configureOutput(Gpio gpio) throws IOException {
        gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        gpio.setActiveType(Gpio.ACTIVE_LOW);
        gpio.setValue(false);
        return gpio;
    }

    private void disable(Gpio gpio) {
        try {
            gpio.setValue(false);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Can't be disabled object: " + gpio);
        }
    }
}
