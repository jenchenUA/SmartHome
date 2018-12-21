package com.ua.jenchen.smarthome.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.models.AppConstants;
import com.ua.jenchen.smarthome.listeners.LightConfigurationValueListener;

public class LightInitializer extends AsyncTask<Void, Void, Void> {

    private Context context;

    public LightInitializer(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase
                .getReference(AppConstants.LIGHT_CONFIGURATION_TABLE_NAME);
        reference.addValueEventListener(new LightConfigurationValueListener(context));
        return null;
    }
}
