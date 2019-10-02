package com.ua.jenchen.smarthome.listeners;

import android.util.Log;

import com.google.android.things.update.StatusListener;
import com.google.android.things.update.UpdateManagerStatus;
import com.ua.jenchen.models.Channels;
import com.ua.jenchen.models.Events;
import com.ua.jenchen.models.Message;
import com.ua.jenchen.models.Update;
import com.ua.jenchen.models.UpdateStatus;
import com.ua.jenchen.smarthome.services.WebSocketService;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class UpdateStatusListener implements StatusListener {

    private static final String LOG_TAG = UpdateStatusListener.class.getSimpleName();

    private WebSocketService webSocketService;

    @Inject
    public UpdateStatusListener(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Override
    public void onStatusUpdate(UpdateManagerStatus status) {
        Update update = prepareUpdate(status);
        Message<Update> message = new Message<>(Events.SYSTEM_UPDATE.getCode(), update);
        webSocketService.publish(Channels.UPDATES.getCode(), message);
    }

    @NotNull
    private Update prepareUpdate(UpdateManagerStatus status) {
        Log.i(LOG_TAG, "System update status: " + status.currentState);
        Update update = new Update();
        update.setNewVersion(status.pendingUpdateInfo);
        update.setCurrentVersion(status.currentVersionInfo);
        switch (status.currentState) {
            case UpdateManagerStatus.STATE_CHECKING_FOR_UPDATES: {
                update.setUpdateStatus(UpdateStatus.UPDATE_CHECKING);
                break;
            }
            case UpdateManagerStatus.STATE_DOWNLOADING_UPDATE: {
                update.setUpdateStatus(UpdateStatus.UPDATE_DOWNLOADING);
                break;
            }
            case UpdateManagerStatus.STATE_UPDATED_NEEDS_REBOOT: {
                update.setUpdateStatus(UpdateStatus.UPDATE_NEEDS_REBOOT);
                break;
            }
            case UpdateManagerStatus.STATE_IDLE: {
                update.setUpdateStatus(UpdateStatus.UPDATE_IDLE);
                break;
            }
            case UpdateManagerStatus.STATE_UPDATE_AVAILABLE: {
                update.setUpdateStatus(UpdateStatus.UPDATE_AVAILABLE);
                break;
            }
            case UpdateManagerStatus.STATE_FINALIZING_UPDATE: {
                update.setUpdateStatus(UpdateStatus.UPDATE_FINALIZING);
                break;
            }
            case UpdateManagerStatus.STATE_REPORTING_ERROR: {
                update.setUpdateStatus(UpdateStatus.UPDATE_ERROR);
                break;
            }
        }
        return update;
    }
}
