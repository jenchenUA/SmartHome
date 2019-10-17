package com.ua.jenchen.smarthome.services;

import com.ua.jenchen.models.extensions.Extension;
import com.ua.jenchen.models.extensions.ExtensionType;
import com.ua.jenchen.smarthome.database.dao.ExtensionDao;
import com.ua.jenchen.smarthome.managers.AdcManager;
import com.ua.jenchen.smarthome.managers.PeripheralGpioManager;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;

public class ExtensionService {

    private ExtensionDao extensionDao;
    private PeripheralGpioManager gpioManager;
    private AdcManager adcManager;

    @Inject
    public ExtensionService(ExtensionDao extensionDao, PeripheralGpioManager gpioManager, AdcManager adcManager) {
        this.extensionDao = extensionDao;
        this.gpioManager = gpioManager;
        this.adcManager = adcManager;
    }

    public List<Extension> getAllExtensions() {
        return extensionDao.getAll();
    }

    public void create(Extension extension) {
        extensionDao.insert(extension);
    }

    public void removeById(int id) {
        extensionDao.deleteById(id);
    }

    public void runAdcConfiguration(AppCompatActivity activity) {
        extensionDao.getByType(ExtensionType.ADC).observe(activity, configurations -> {
            configurations.stream()
                    .filter(configuration -> !adcManager.isAds1115Configured(configuration.getAddress()))
                    .forEach(configuration -> adcManager.createAds1115(configuration.getAddress()));
        });
    }

    public void runGpioExpanderConfiguration(AppCompatActivity activity) {
        extensionDao.getByType(ExtensionType.GPIO).observe(activity, configurations -> {
            configurations.stream()
                    .filter(configuration -> !gpioManager.isProviderExists(configuration.getAddress()))
                    .forEach(configuration -> gpioManager.createGpioProvider(configuration.getAddress()));
        });
    }
}
