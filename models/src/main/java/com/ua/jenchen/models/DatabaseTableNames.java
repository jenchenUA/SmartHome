package com.ua.jenchen.models;

public enum DatabaseTableNames {

    LIGHT_CONFIGURATION("light_configuration"),
    LIGHT_STATE("light_state"),
    WARM_FLOOR_CONFIGURATION("warm_floor_configuration");

    private String name;

    DatabaseTableNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
