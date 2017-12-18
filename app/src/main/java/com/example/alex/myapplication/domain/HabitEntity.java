package com.example.alex.myapplication.domain;

import java.io.Serializable;

public class HabitEntity implements Serializable {
    private int id;
    private String title;
    private String description;

    public HabitEntity(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "HabitEntity{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
