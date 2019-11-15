package com.ua.jenchen.smarthome.services;

import com.ua.jenchen.models.warmfloor.WarmFloorConfiguration;
import com.ua.jenchen.models.warmfloor.WarmFloorState;
import com.ua.jenchen.models.warmfloor.WarmFloorView;
import com.ua.jenchen.models.websockets.Channels;
import com.ua.jenchen.models.websockets.Events;
import com.ua.jenchen.models.websockets.Message;
import com.ua.jenchen.smarthome.database.dao.WarmFloorDao;
import com.ua.jenchen.smarthome.database.dao.WarmFloorStateDao;
import com.ua.jenchen.smarthome.managers.GpioManager;
import com.ua.jenchen.smarthome.managers.WarmFloorManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;

public class WarmFloorService {

    private WarmFloorDao warmFloorDao;
    private WarmFloorStateDao warmFloorStateDao;
    private GpioManager gpioManager;
    private WebSocketService webSocketService;

    @Inject
    public WarmFloorService(WarmFloorDao warmFloorDao, GpioManager gpioManager, WebSocketService webSocketService,
                            WarmFloorStateDao warmFloorStateDao) {
        this.warmFloorDao = warmFloorDao;
        this.warmFloorStateDao = warmFloorStateDao;
        this.gpioManager = gpioManager;
        this.webSocketService = webSocketService;
    }

    public void create(WarmFloorConfiguration configuration) {
        warmFloorDao.insert(configuration);
        publishMessage(toView(configuration));
    }

    public List<WarmFloorView> getAllViews() {
        return warmFloorDao.getAllConfigurations().stream()
                .map(this::toView)
                .collect(Collectors.toList());
    }

    public void runAllConfigurations(AppCompatActivity activity) {
        warmFloorDao.getAllLiveData().observe(activity, configurations -> {
            configurations.stream()
                    .filter(configuration -> !gpioManager.isWarmFloorManagerExist(configuration.getUid()))
                    .forEach(gpioManager::makeWarmFloorManager);
        });
    }

    public void disableAllWarmFloorManagers() {
        warmFloorDao.getAllConfigurations().stream()
                .filter(configuration -> !gpioManager.isWarmFloorManagerExist(configuration.getUid()))
                .forEach(this::firstMakeWarmFloorManager);
    }

    private void firstMakeWarmFloorManager(WarmFloorConfiguration configuration) {
        warmFloorStateDao.updateState(configuration.getUid(), false);
        gpioManager.getWarmFloorManager(configuration.getUid()).ifPresent(manager -> manager.setState(false));
    }

    public void deleteConfiguration(String uid) {
        warmFloorDao.deleteByUid(uid);
        gpioManager.destroyWarmFloorManager(uid);
    }

    private void publishMessage(WarmFloorView view) {
        Message<WarmFloorView> message = new Message<>(Events.WARM_FLOOR.getCode(), view);
        webSocketService.publishAsync(Channels.UPDATES.getCode(), message);
    }

    private WarmFloorView toView(WarmFloorConfiguration configuration) {
        return gpioManager.getWarmFloorManager(configuration.getUid()).map(WarmFloorManager::toView)
                .orElseGet(() -> getViewWithEmptyCurrentStatus(configuration));
    }

    @NotNull
    private WarmFloorView getViewWithEmptyCurrentStatus(WarmFloorConfiguration configuration) {
        WarmFloorView result = new WarmFloorView();
        result.setUid(configuration.getUid());
        result.setLabel(configuration.getLabel());
        result.setCurrentTemperature(0);
        result.setEnabled(false);
        result.setWarmingEnabled(false);
        result.setThreshold(configuration.getThreshold());
        result.setOnline(false);
        return result;
    }


    public void changeState(String uid) {
        gpioManager.getWarmFloorManager(uid).ifPresent(manager -> {
            WarmFloorState state = Optional.ofNullable(warmFloorStateDao.getByUid(uid))
                    .orElseGet(() -> prepareState(uid));
            boolean newState = !state.getState();
            state.setState(newState);
            warmFloorStateDao.updateState(uid, newState);
            manager.setState(newState);
            publishMessage(manager.toView());
        });
    }

    private WarmFloorState prepareState(String uid) {
        WarmFloorState result = new WarmFloorState();
        result.setState(false);
        result.setUid(uid);
        warmFloorStateDao.insert(result);
        return result;
    }

    public void changeThreshold(String uid, float threshold) {
        warmFloorDao.setThreshold(uid, threshold);
        gpioManager.getWarmFloorManager(uid).ifPresent(manager -> {
            manager.setThreshold(threshold);
            publishMessage(manager.toView());
        });
    }
}
