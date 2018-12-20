package com.ua.jenchen.models;

public class LightConfiguration {

    private String uid;
    private String inputPin;
    private String outputPin;
    private String label;

    public LightConfiguration() {
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
}
