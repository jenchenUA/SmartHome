package com.ua.jenchen.smarthome.services;

import android.util.Log;

import com.google.android.things.update.UpdateManager;
import com.google.android.things.update.UpdatePolicy;
import com.google.android.things.update.VersionInfo;

import javax.inject.Inject;

public class SystemUpdateService {

    private static final String LOG_TAG = SystemUpdateService.class.getSimpleName();

    private UpdateManager manager;

    @Inject
    public SystemUpdateService(UpdateManager manager) {
        this.manager = manager;
    }

    public VersionInfo getCurrentVersion() {
        return manager.getStatus().currentVersionInfo;
    }

    public boolean checkUpdate() {
        boolean result = manager.performUpdateNow(UpdatePolicy.POLICY_CHECKS_ONLY);
        Log.i(LOG_TAG, "System update checking result: " + result);
        return result;
    }

    public boolean performUpdate() {
        boolean result = manager.performUpdateNow(UpdatePolicy.POLICY_APPLY_ONLY);
        Log.i(LOG_TAG, "System update performing result: " + result);
        return result;
    }

    public boolean performUpdateAndReboot() {
        boolean result = manager.performUpdateNow(UpdatePolicy.POLICY_APPLY_AND_REBOOT);
        Log.i(LOG_TAG, "System update performing and device rebooting result: " + result);
        return result;
    }
}
