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
    }

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
