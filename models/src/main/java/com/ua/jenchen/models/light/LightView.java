package com.ua.jenchen.models.light;

public class LightView {

    private String uid;
    private boolean state;
    private String label;

    public LightView(LightConfiguration configuration, LampState state) {
        this.uid = configuration.getUid();
        this.label = configuration.getLabel();
        this.state = state.getState();
    }

    public LightView(LampState state) {
        this.uid = state.getUid();
        this.state = state.getState();
    }

    public LightView(LightConfiguration configuration) {
        this.uid = configuration.getUid();
        this.label = configuration.getLabel();
        this.state = false;
    }

    public LightView(String uid, boolean state) {
        this.uid = uid;
        this.state = state;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
