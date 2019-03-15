package com.ua.jenchen.smarthome.tasks;

import android.os.AsyncTask;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.smarthome.listeners.WarmFloorConfigurationValueListener;
import com.ua.jenchen.smarthome.listeners.WarmFloorStateValueListener;

public class WarmFloorInitializer extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        DatabaseReference reference = firebaseDatabase.getReference(AppConstants.WARM_FLOOR_STATE_TABLE_NAME);
        reference.setValue(null);
        reference.addValueEventListener(new WarmFloorStateValueListener());
        firebaseDatabase.getReference(AppConstants.WARM_FLOOR_CONFIGURATION_TABLE_NAME)
                .addValueEventListener(new WarmFloorConfigurationValueListener());
        return null;
    }
}
