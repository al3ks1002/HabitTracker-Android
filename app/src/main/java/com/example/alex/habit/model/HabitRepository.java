package com.example.alex.habit.model;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.Date;
import java.util.List;

public class HabitRepository {
    private HabitDatabase database;

    private static HabitRepository instance = null;

    private HabitRepository(Context context) {
        this.database = Room.databaseBuilder(context,
                HabitDatabase.class, "habits-db").allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public static HabitRepository getInstance(Context context) {
        if (instance == null) {
            instance = new HabitRepository(context);
            instance.addUser(new UserEntity("al3ksfl@gmail.com", true));
            instance.addUser(new UserEntity("test@test.com", false));
        }
        return instance;
    }

    public List<HabitEntity> getHabitList(String email) {
        return database.habitDao().getHabitList(email);
    }

    public void addHabit(HabitEntity habit) {
        database.habitDao().addHabit(habit);
    }

    public void deleteHabit(HabitEntity habit) {
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

    public UserEntity getUser(String email) {
        return database.habitDao().getUser(email);
    }

    public List<UserEntity> getUserList() {
        return database.habitDao().getUserList();
    }

    public void addUser(UserEntity user) {
        database.habitDao().addUser(user);
    }
}
