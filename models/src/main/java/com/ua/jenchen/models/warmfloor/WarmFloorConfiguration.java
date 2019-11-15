package com.ua.jenchen.models.warmfloor;

import com.ua.jenchen.models.AppConstants;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = AppConstants.WARM_FLOOR_CONFIGURATION_TABLE_NAME,
        indices = {@Index(value = {"control_pin", "button_pin", "gpio_address"}, unique = true),
                @Index(value = {"channel", "adc_address"}, unique = true)})
public class WarmFloorConfiguration {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "control_pin")
    private String controlPin;
    @ColumnInfo(name = "button_pin")
    private String switcherPin;
    @ColumnInfo(name = "b")
    private float bOfThermoresistor;
    @ColumnInfo(name = "thermoresistor")
    private float resistanceOfThermoresistor;
    @ColumnInfo(name = "support_resistor")
    private float resistanceOfSupportResistor;
    @ColumnInfo(name = "voltage")
    private float voltage;
    @ColumnInfo(name = "channel")
    private int channel;
    @ColumnInfo(name = "measures")
    private int countOfMeasures;
    @ColumnInfo(name = "label")
    private String label;
    @ColumnInfo(name = "gpio_address")
    private int gpioAddress;
    @ColumnInfo(name = "adc_address")
    private int adcAddress;
    @ColumnInfo(name = "uid")
    private String uid;
    @ColumnInfo(name = "threshold")
    private float threshold;

    public WarmFloorConfiguration() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return this.switcherPin + this.controlPin;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getControlPin() {
        return controlPin;
    }

    public void setControlPin(String controlPin) {
        this.controlPin = controlPin;
    }

    public String getSwitcherPin() {
        return switcherPin;
    }

    public void setSwitcherPin(String switcherPin) {
        this.switcherPin = switcherPin;
    }

    public float getBOfThermoresistor() {
        return bOfThermoresistor;
    }

    public void setBOfThermoresistor(float bOfThermoresistor) {
        this.bOfThermoresistor = bOfThermoresistor;
    }

    public float getResistanceOfThermoresistor() {
        return resistanceOfThermoresistor;
    }

    public void setResistanceOfThermoresistor(float resistanceOfThermoresistor) {
        this.resistanceOfThermoresistor = resistanceOfThermoresistor;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getGpioAddress() {
        return gpioAddress;
    }

    public void setGpioAddress(int gpioAddress) {
        this.gpioAddress = gpioAddress;
    }

    public int getAdcAddress() {
        return adcAddress;
    }

    public void setAdcAddress(int adcAddress) {
        this.adcAddress = adcAddress;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }
}
