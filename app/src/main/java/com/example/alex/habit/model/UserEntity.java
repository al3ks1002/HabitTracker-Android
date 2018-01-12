package com.example.alex.habit.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

@Entity(tableName = "users")
public class UserEntity implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = "email")
    @NonNull
    private String email;

    @ColumnInfo(name = "isAdmin")
    private boolean isAdmin;

    public UserEntity(@NonNull String email, boolean isAdmin) {
        this.email = email;
        this.isAdmin = isAdmin;
    }

    @Ignore
    public UserEntity(JSONObject object) throws JSONException {
        email = (String) object.get("email");
        isAdmin = (boolean) object.get("isAdmin");
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("email", getEmail());
        object.put("isAdmin", isAdmin());
        return object;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
