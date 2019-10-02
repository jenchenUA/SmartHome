package com.ua.jenchen.models;

public enum Events {

    SYSTEM_UPDATE("system_update");

    private String code;

    Events(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
