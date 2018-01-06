package com.example.alex.myapplication.model;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.Date;
import java.util.List;

public class HabitRepository {
    private HabitDatabase database;

    private static HabitRepository instance = null;

    private HabitRepository(Context context) {
        this.database = Room.databaseBuilder(context,
                HabitDatabase.class, "habits-db").allowMainThreadQueries().build();
    }

    public static HabitRepository getInstance(Context context) {
        if (instance == null) {
            instance = new HabitRepository(context);
        }
        return instance;
    }

    public List<HabitEntity> get() {
        return database.habitDao().getHabitList();
    }

    public void add(HabitEntity habit) {
        database.habitDao().addHabit(habit);
    }

    public void delete(HabitEntity habit) {
        database.habitDao().deleteHabit(habit);
    }

    public List<Date> getDates(int id) {
        return database.habitDao().getDatesForHabit(id);
    }

    public void addHabitDate(HabitDate habitDate) {
        database.habitDao().addHabitDate(habitDate);
    }

    public void deleteHabitDate(HabitDate habitDate) {
        database.habitDao().deleteHabitDate(habitDate);
    }
}
