package com.ua.jenchen.smarthome.services;

import com.ua.jenchen.models.extensions.Extension;
import com.ua.jenchen.models.extensions.ExtensionType;
import com.ua.jenchen.models.websockets.Channels;
import com.ua.jenchen.models.websockets.Events;
import com.ua.jenchen.models.websockets.Message;
import com.ua.jenchen.smarthome.database.dao.ExtensionDao;
import com.ua.jenchen.smarthome.managers.AdcManager;
import com.ua.jenchen.smarthome.managers.PeripheralGpioManager;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;

public class ExtensionService {

    private ExtensionDao extensionDao;
    private PeripheralGpioManager gpioManager;
    private AdcManager adcManager;
    private WebSocketService webSocketService;
    private boolean isAdcInited = false;
    private boolean isGpioProviderInited = false;

    @Inject
    public ExtensionService(ExtensionDao extensionDao, PeripheralGpioManager gpioManager, AdcManager adcManager,
                            WebSocketService webSocketService) {
        this.extensionDao = extensionDao;
        this.gpioManager = gpioManager;
        this.adcManager = adcManager;
        this.webSocketService = webSocketService;
    }

    public List<Extension> getAllExtensions() {
        List<Extension> extensions = extensionDao.getAll();
        extensions.forEach(this::isOnline);
        return extensions;
    }

    private void isOnline(Extension extension) {
        if (extension.getType() == ExtensionType.ADC) {
            extension.setOnline(adcManager.isAds1115Configured(extension.getAddress()));
        } else if (extension.getType() == ExtensionType.GPIO) {
            extension.setOnline(gpioManager.isProviderExists(extension.getAddress()));
        }
    }

    public void create(Extension extension) {
        extensionDao.insert(extension);
    }

    public void removeById(int id) {
        extensionDao.deleteById(id);
    }

    public void runAdcConfiguration(AppCompatActivity activity) {
        extensionDao.getByTypeAsLiveData(ExtensionType.ADC).observe(activity, configurations -> {
            configurations.stream()
                    .filter(configuration -> !adcManager.isAds1115Configured(configuration.getAddress()))
                    .forEach(this::createAdc);
            isAdcInited = true;
        });
    }

    public void initAdcConfigurations() {
        extensionDao.getByType(ExtensionType.ADC).stream()
                .filter(configuration -> !adcManager.isAds1115Configured(configuration.getAddress()))
                .forEach(this::createAdc);
    }

    public void runGpioExpanderConfiguration(AppCompatActivity activity) {
        extensionDao.getByTypeAsLiveData(ExtensionType.GPIO).observe(activity, configurations -> {
            configurations.stream()
                .filter(configuration -> !gpioManager.isProviderExists(configuration.getAddress()))
                .forEach(this::createGpioProvider);
            isGpioProviderInited = true;
        });
    }

    public void initGpioExpanderConfigurations() {
        extensionDao.getByType(ExtensionType.GPIO).stream()
                .filter(configuration -> !gpioManager.isProviderExists(configuration.getAddress()))
                .forEach(this::createGpioProvider);
    }

    public List<Extension> getAvailableAdc() {
        return adcManager.getOnlineAdcs().stream()
                .map(this::adcAddressToExtension)
                .collect(Collectors.toList());
    }

    public List<Extension> getAvailableGpioProviders() {
        return gpioManager.getAvailableProviders().stream()
                .map(this::gpioProviderAddressToExtension)
                .collect(Collectors.toList());
    }

    private Extension gpioProviderAddressToExtension(Integer address) {
        Extension result = new Extension();
        result.setAddress(address);
        result.setType(ExtensionType.GPIO);
        result.setOnline(true);
        return result;
    }

    private Extension adcAddressToExtension(Integer address) {
        Extension result = new Extension();
        result.setAddress(address);
        result.setOnline(true);
        result.setType(ExtensionType.ADC);
        return result;
    }

    private void createAdc(Extension configuration) {
        configuration.setOnline(adcManager.createAds1115(configuration.getAddress()));
        if (isAdcInited) {
            publishMessage(configuration);
        }
    }

    private void createGpioProvider(Extension configuration) {
        configuration.setOnline(gpioManager.createGpioProvider(configuration.getAddress()));
        if (isGpioProviderInited) {
            publishMessage(configuration);
        }
    }

    private void publishMessage(Extension configuration) {
        Message<Extension> message = new Message<>(Events.EXTENSIONS.getCode(), configuration);
        webSocketService.publishAsync(Channels.UPDATES.getCode(), message);
    }

}
