package com.example.alex.habit.controller;

public interface Subject {
    void attach(Observer observer);

    void notifyObservers(Observer.ObserverStatus status, Object object);
}
