package com.ua.jenchen.smarthome.application;

import android.app.Application;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class SmartHomeApplication extends Application {

    private static final String LOG_TAG = SmartHomeApplication.class.getSimpleName();
    private static final String FRONT_END_FOLDER_NAME = "front";

    @Override
    public void onCreate() {
        super.onCreate();
        copyFrontEndToInternalStorage();
        File staticResourcesFolder = new File(getApplicationContext().getFilesDir(), FRONT_END_FOLDER_NAME);
        Javalin.create(config -> config.addStaticFiles(staticResourcesFolder.getPath(), Location.EXTERNAL))
                .start(8080);
    }

    private void copyFrontEndToInternalStorage() {
        try {
            List<String> files = Optional.ofNullable(getAssets().list(""))
                    .map(Arrays::asList)
                    .orElseGet(Collections::emptyList);
            copyFilesToInternalStorage(files);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to get asset file list.", e);
        }
    }

    private void copyFilesToInternalStorage(List<String> files) {
        File frontendDirectory = prepareTargetDirectory();
        for (String filename : files) {
            File outFile = new File(frontendDirectory, filename);
            try (InputStream in = getAssets().open(filename); OutputStream out = new FileOutputStream(outFile)) {
                copyFile(in, out);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Failed to copy asset file: " + filename, e);
            }
        }
    }

    private File prepareTargetDirectory() {
        File frontendDirectory = new File(getApplicationContext().getFilesDir(), FRONT_END_FOLDER_NAME);
        if (!frontendDirectory.exists()) {
            frontendDirectory.mkdir();
        } else {
            Arrays.stream(frontendDirectory.listFiles()).forEach(File::delete);
        }
        return frontendDirectory;
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}
