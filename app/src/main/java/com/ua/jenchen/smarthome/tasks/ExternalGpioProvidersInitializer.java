package com.ua.jenchen.smarthome.tasks;

import android.os.AsyncTask;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ua.jenchen.models.AppConstants;

public class ExternalGpioProvidersInitializer extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase
                .getReference(AppConstants.GPIO_EXPANDER_CONFIGURATION_TABLE_NAME);
//        reference.addValueEventListener(new ExpernalGpioProviderConfigurationValueListener());
        return null;
    }
}
