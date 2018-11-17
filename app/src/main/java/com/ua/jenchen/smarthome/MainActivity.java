package com.ua.jenchen.smarthome;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

public class MainActivity extends Activity {

    private Gpio gpioInput;
    private Gpio gpioOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeripheralManager manager = PeripheralManager.getInstance();
        try {
            gpioOutput = manager.openGpio("BCM17");
            configureOutput(gpioOutput);
            gpioInput = manager.openGpio("BCM27");
            configureInput(gpioInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureOutput(Gpio gpio) throws IOException {
        gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        gpio.setActiveType(Gpio.ACTIVE_HIGH);
        gpio.setValue(true);
    }

    private void configureInput(Gpio gpio) throws IOException {
        gpio.setDirection(Gpio.DIRECTION_IN);
        gpio.setActiveType(Gpio.ACTIVE_HIGH);
        gpio.setEdgeTriggerType(Gpio.EDGE_FALLING);
        gpio.registerGpioCallback(inputCallback);
    }

    private GpioCallback inputCallback = new GpioCallback() {

        @Override
        public boolean onGpioEdge(Gpio gpio) {
            boolean value = true;
            try {
                if (!gpio.getValue()) {
                    return true;
                }
                if (gpioOutput.getValue()) {
                    gpioOutput.setValue(false);
                } else {
                    gpioOutput.setValue(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        public void onGpioError(Gpio gpio, int error) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            gpioInput.close();
            gpioOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
