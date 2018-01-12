package com.example.alex.habit.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Ignore
    public HabitEntity(JSONObject object) throws JSONException {
        id = (Integer) object.get("id");
        title = (String) object.get("title");
        description = (String) object.get("description");
        email = (String) object.get("email");
    }

    public HabitEntity(String email) {
        this.title = "";
        this.description = "";
        this.email = email;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id", getId());
        object.put("title", getTitle());
        object.put("description", getDescription());
        object.put("email", getEmail());
        return object;
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
