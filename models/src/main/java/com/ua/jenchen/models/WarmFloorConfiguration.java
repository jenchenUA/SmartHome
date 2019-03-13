package com.ua.jenchen.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = AppConstants.WARM_FLOOR_CONFIGURATION_TABLE_NAME,
        indices = @Index(value = "control_pin", unique = true))
public class WarmFloorConfiguration {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "control_pin")
    private String controlPin;
    @ColumnInfo(name = "switcher")
    private String swircherPin;
    @ColumnInfo(name = "b")
    private float bOfThermoresistor;
    @ColumnInfo(name = "thermoresistor")
    private float resistanceOfThresmoresistor;
    @ColumnInfo(name = "support_resistor")
    private float resistanceOfSupportResistor;
    @ColumnInfo(name = "voltage")
    private float voltage;
    @ColumnInfo(name = "channel")
    private int channel;
    @ColumnInfo(name = "measures")
    private int countOfMeasures;
    @ColumnInfo(name = "control_pin_high_activation")
    private boolean isControlPinHighActivation;

    public WarmFloorConfiguration() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return this.swircherPin + this.controlPin;
    }

    public String getControlPin() {
        return controlPin;
    }

    public void setControlPin(String controlPin) {
        this.controlPin = controlPin;
    }

    public String getSwircherPin() {
        return swircherPin;
    }

    public void setSwircherPin(String swircherPin) {
        this.swircherPin = swircherPin;
    }

    public float getBOfThermoresistor() {
        return bOfThermoresistor;
    }

    public void setbOfThermoresistor(float bOfThermoresistor) {
        this.bOfThermoresistor = bOfThermoresistor;
    }

    public float getResistanceOfThresmoresistor() {
        return resistanceOfThresmoresistor;
    }

    public void setResistanceOfThresmoresistor(float resistanceOfThresmoresistor) {
        this.resistanceOfThresmoresistor = resistanceOfThresmoresistor;
    }

    public float getResistanceOfSupportResistor() {
        return resistanceOfSupportResistor;
    }

    public void setResistanceOfSupportResistor(float resistanceOfSupportResistor) {
        this.resistanceOfSupportResistor = resistanceOfSupportResistor;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getCountOfMeasures() {
        return countOfMeasures;
    }

    public void setCountOfMeasures(int countOfMeasures) {
        this.countOfMeasures = countOfMeasures;
    }

    public boolean isControlPinHighActivation() {
        return isControlPinHighActivation;
    }

    public void setControlPinHighActivation(boolean controlPinHighActivation) {
        isControlPinHighActivation = controlPinHighActivation;
    }
}
