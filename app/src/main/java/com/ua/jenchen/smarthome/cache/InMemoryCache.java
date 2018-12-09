package com.ua.jenchen.smarthome.cache;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCache implements Cache {

    private static Cache instance;
    private Map<String, Object> cache;

    private InMemoryCache() {
        this.cache = new ConcurrentHashMap<>();
    }

    public static Cache getInstance() {
        if (instance == null) {
            synchronized (InMemoryCache.class) {
                if (instance == null) {
                    instance = new InMemoryCache();
                }
            }
        }
        return instance;
    }

    @Override
    public <T> void set(String key, T value) {
        cache.put(key, value);
    }

    @Override
    public <T> Optional<T> get(String key) {
        return Optional.ofNullable((T) cache.get(key));
    }
}
