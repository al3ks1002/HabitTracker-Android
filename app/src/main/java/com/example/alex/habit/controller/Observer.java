package com.example.alex.habit.controller;

public interface Observer {
    enum ObserverStatus {
        OK, FAIL
    }

    void update(ObserverStatus status, Object object);
}
