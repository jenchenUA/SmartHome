package com.ua.jenchen.smarthome.activities;

import android.app.Activity;
import android.os.Bundle;

import com.ua.jenchen.smarthome.R;
import com.ua.jenchen.smarthome.managers.GpioManager;

public class MainActivity extends Activity {

    private GpioManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = GpioManager.getInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.closeAllGpios();
    }
}
