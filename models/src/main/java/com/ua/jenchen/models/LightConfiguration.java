package com.ua.jenchen.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = AppConstants.LIGHT_CONFIGURATION_TABLE_NAME,
        indices = {@Index(value = "uid", unique = true),
                @Index(value = {"button_pin", "control_pin"}, unique = true)})
public class LightConfiguration {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "uid")
    private String uid;
    @ColumnInfo(name = "button_pin")
    private String buttonPin;
    @ColumnInfo(name = "control_pin")
    private String controlPin;
    @ColumnInfo(name = "label")
    private String label;
    @ColumnInfo(name = "gpio_address")
    private int gpioAddress;
    @ColumnInfo(name = "output_high_activation")
    private boolean isOutputHighActivation;

    public LightConfiguration() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getButtonPin() {
        return buttonPin;
    }

    public void setButtonPin(String buttonPin) {
        this.buttonPin = buttonPin;
    }

    public String getControlPin() {
        return controlPin;
    }

    public void setControlPin(String controlPin) {
        this.controlPin = controlPin;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isOutputHighActivation() {
        return isOutputHighActivation;
    }

    public void setOutputHighActivation(boolean outputHighActivation) {
        isOutputHighActivation = outputHighActivation;
    }

    public int getGpioAddress() {
        return gpioAddress;
    }

    public void setGpioAddress(int gpioAddress) {
        this.gpioAddress = gpioAddress;
    }
}
