package com.ua.jenchen.models.websockets;

public enum Channels {

    UPDATES("updates");

    private String code;

    Channels(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
