package com.ua.jenchen.smarthome.services;

import android.util.Log;

import com.ua.jenchen.models.websockets.Message;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.inject.Inject;

import io.javalin.websocket.WsContext;

public class WebSocketService {

    private static final String LOG_TAG = WebSocketService.class.getSimpleName();

    private Map<String, Set<WsContext>> subscribers;

    @Inject
    public WebSocketService() {
        subscribers = new ConcurrentHashMap<>();
    }

    public void subscribe(WsContext subscriber) {
        String channel = subscriber.pathParam("channel");
        Set<WsContext> contexts = subscribers.get(channel);
        if (contexts != null) {
            contexts.add(subscriber);
        } else {
            contexts = new CopyOnWriteArraySet<>();
            contexts.add(subscriber);
            subscribers.put(channel, contexts);
        }
    }

    public void unsubscribe(WsContext subscriber) {
        String channel = subscriber.pathParam("channel");
        Optional.ofNullable(subscribers.get(channel))
                .ifPresent(contexts -> contexts.remove(subscriber));
    }

    public void publish(String channel, Message message) {
        Log.i(LOG_TAG, String.format("Sending message: %s, to channel: %s", message, channel));
        Optional.ofNullable(subscribers.get(channel))
                .orElseGet(Collections::emptySet)
                .stream()
                .filter(context -> context.session.isOpen())
                .forEach(context -> context.send(message));
    }

    public void publishAsync(String channel, Message message) {
        CompletableFuture.runAsync(() -> publish(channel, message));
    }
}
