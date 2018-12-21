package com.ua.jenchen.smarthome.activities;

import android.app.Activity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.smarthome.R;
import com.ua.jenchen.smarthome.listeners.LampStateValueEventListener;
import com.ua.jenchen.smarthome.managers.GpioManager;
import com.ua.jenchen.smarthome.managers.LampManager;
import com.ua.jenchen.smarthome.tasks.LightInitializer;


public class MainActivity extends Activity {

    private GpioManager manager;
    private LampManager lampManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseReference lightStateReference = FirebaseDatabase.getInstance()
                .getReference(AppConstants.LIGHT_STATE_TABLE_NAME);
        lightStateReference.setValue(null);
        lightStateReference.addValueEventListener(new LampStateValueEventListener());

        LightInitializer lightInitializer = new LightInitializer(getApplicationContext());
        lightInitializer.execute();
    }

    @Override
    protected void onDestroy() {
        manager.closeAllGpios();
        super.onDestroy();
    }
}
