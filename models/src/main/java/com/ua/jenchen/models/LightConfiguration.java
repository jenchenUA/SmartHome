package com.ua.jenchen.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = AppConstants.LIGHT_CONFIGURATION_TABLE_NAME,
        indices = {@Index(value = "uid", unique = true),
                @Index(value = {"input", "output"}, unique = true)})
public class LightConfiguration {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "uid")
    private String uid;
    @ColumnInfo(name = "input")
    private String inputPin;
    @ColumnInfo(name = "output")
    private String outputPin;
    @ColumnInfo(name = "label")
    private String label;
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

    public String getInputPin() {
        return inputPin;
    }

    public void setInputPin(String inputPin) {
        this.inputPin = inputPin;
    }

    public String getOutputPin() {
        return outputPin;
    }

    public void setOutputPin(String outputPin) {
        this.outputPin = outputPin;
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
}
