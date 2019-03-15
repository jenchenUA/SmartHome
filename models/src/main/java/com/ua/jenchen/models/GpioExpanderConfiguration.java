package com.ua.jenchen.models;

public class GpioExpanderConfiguration {

    private int address;

    public GpioExpanderConfiguration() {
    }

    public GpioExpanderConfiguration(int address) {
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
}
