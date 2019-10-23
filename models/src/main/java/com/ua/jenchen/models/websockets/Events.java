package com.ua.jenchen.models.websockets;

public enum Events {

    SYSTEM_UPDATE("system_update"),
    LIGHT_STATE("light"),
    EXTENSIONS("extensions");

    private String code;

    Events(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
