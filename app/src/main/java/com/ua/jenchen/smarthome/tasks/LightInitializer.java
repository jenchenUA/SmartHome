package com.ua.jenchen.smarthome.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.smarthome.listeners.LampStateValueEventListener;
import com.ua.jenchen.smarthome.listeners.LightConfigurationValueListener;

public class LightInitializer extends AsyncTask<Void, Void, Void> {

    private Context context;

    public LightInitializer(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference(AppConstants.LIGHT_CONFIGURATION_TABLE_NAME)
                .addValueEventListener(new LightConfigurationValueListener(context));
        firebaseDatabase.getReference(AppConstants.LIGHT_STATE_TABLE_NAME)
                .addValueEventListener(new LampStateValueEventListener());
        return null;
    }
}
