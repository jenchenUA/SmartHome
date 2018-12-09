package com.ua.jenchen.smarthome.cache;

import java.util.Optional;

public interface Cache {

    <T> void set(String key, T value);

    <T> Optional<T> get(String key);
}
