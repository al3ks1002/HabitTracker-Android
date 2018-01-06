package com.example.alex.myapplication.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(primaryKeys = {"habitId", "date"}, tableName = "habit_dates")
public class HabitDate {
    @ColumnInfo(name = "habitId")
    private int habitId;

    @ColumnInfo(name = "date")
    @NonNull
    private Date date;

    public HabitDate(int habitId, Date date) {
        this.habitId = habitId;
        this.date = date;
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
        return "HabitDate{" +
                "habitId=" + habitId +
                ", date=" + date +
                '}';
    }
}
