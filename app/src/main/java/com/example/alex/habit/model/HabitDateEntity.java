package com.example.alex.habit.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import com.example.alex.habit.utils.DateConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

@Entity(primaryKeys = {"habitId", "date"}, tableName = "habit_date")
public class HabitDateEntity {
    @ColumnInfo(name = "habitId")
    private int habitId;

    @ColumnInfo(name = "date")
    @NonNull
    private Date date;

    public HabitDateEntity(int habitId, Date date) {
        this.habitId = habitId;
        this.date = date;
    }

    @Ignore
    public HabitDateEntity(JSONObject object) throws JSONException, JSONException {
        habitId = (Integer) object.get("habitId");
        date = DateConverter.fromTimestamp((Long) object.get("date"));
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("habitId", getHabitId());
        object.put("date", DateConverter.dateToTimestamp(getDate()));
        return object;
    }

    public int getHabitId() {
        return habitId;
    }

    public void setHabitId(int habitId) {
        this.habitId = habitId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HabitDateEntity{" +
                "habitId=" + habitId +
                ", date=" + date +
                '}';
    }
}
