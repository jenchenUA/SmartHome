package com.ua.jenchen.smarthome.tasks;

import android.os.AsyncTask;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.smarthome.listeners.LampStateValueEventListener;
import com.ua.jenchen.smarthome.listeners.LightConfigurationValueListener;

public class LightInitializer extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference(AppConstants.LIGHT_STATE_TABLE_NAME);
        reference.setValue(null);
        reference.addValueEventListener(new LampStateValueEventListener());
        firebaseDatabase.getReference(AppConstants.LIGHT_CONFIGURATION_TABLE_NAME)
                .addValueEventListener(new LightConfigurationValueListener());
        return null;
    }
}
