package com.ua.jenchen.models;

public class Message<T> {

    private String event;
    private T data;

    public Message(String event, T data) {
        this.event = event;
        this.data = data;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "event='" + event + '\'' +
                ", data=" + data +
                '}';
    }
}
