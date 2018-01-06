package com.example.alex.myapplication.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "habits")
public class HabitEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    public HabitEntity(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public HabitEntity() {
        this.title = "";
        this.description = "";
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
