package com.example.alex.habit.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
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

    @ColumnInfo(name = "email")
    private String email;

    @Ignore
    public HabitEntity(int id, String title, String description, String email) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.email = email;
    }

    public HabitEntity(String email) {
        this.title = "";
        this.description = "";
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "HabitEntity{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
