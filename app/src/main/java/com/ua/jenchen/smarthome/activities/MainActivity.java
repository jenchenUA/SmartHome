package com.ua.jenchen.smarthome.activities;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.things.pio.Gpio;
import com.ua.jenchen.smarthome.R;

import java.io.IOException;

public class MainActivity extends Activity {

    private Gpio gpioInput;
    private Gpio gpioOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
