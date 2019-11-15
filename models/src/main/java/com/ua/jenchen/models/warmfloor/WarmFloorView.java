package com.ua.jenchen.models.warmfloor;

public class WarmFloorView {

    private String uid;
    private String label;
    private float threshold;
    private float currentTemperature;
    private boolean enabled;
    private boolean warmingEnabled;
    private boolean online;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public float getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(float currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isWarmingEnabled() {
        return warmingEnabled;
    }

    public void setWarmingEnabled(boolean warmingEnabled) {
        this.warmingEnabled = warmingEnabled;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
