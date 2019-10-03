package com.ua.jenchen.models.systemupdate;


import com.google.android.things.update.PendingUpdateInfo;
import com.google.android.things.update.VersionInfo;

public class Update {

    private UpdateStatus updateStatus;
    private VersionInfo currentVersion;
    private PendingUpdateInfo newVersion;

    public UpdateStatus getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(UpdateStatus updateStatus) {
        this.updateStatus = updateStatus;
    }

    public VersionInfo getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(VersionInfo currentVersion) {
        this.currentVersion = currentVersion;
    }

    public PendingUpdateInfo getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(PendingUpdateInfo newVersion) {
        this.newVersion = newVersion;
    }
}
