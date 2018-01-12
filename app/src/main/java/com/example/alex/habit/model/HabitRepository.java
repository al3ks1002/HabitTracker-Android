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
        }
        return instance;
    }

    public void resetHabits(List<HabitEntity> habitEntities, String email) {
        for (HabitEntity habit : getHabitList(email)) {
            deleteHabit(habit);
        }
        for (HabitEntity habit : habitEntities) {
            addHabit(habit);
        }
    }

    public void resetHabitDates(List<HabitDateEntity> habitDateEntities) {
        for (HabitDateEntity habitDate : getHabitDateList()) {
            deleteHabitDate(habitDate);
        }

        for (HabitDateEntity habitDate : habitDateEntities) {
            addHabitDate(habitDate);
        }
    }

    public void resetUsers(List<UserEntity> userEntities) {
        for (UserEntity user : getUserList()) {
            deleteUser(user);
        }

        for (UserEntity user : userEntities) {
            addUser(user);
        }
    }

    public List<HabitEntity> getAllHabits() {
        return database.habitDao().getAllHabits();
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

    public List<HabitDateEntity> getHabitDateList() {
        return database.habitDao().getHabitDateList();
    }

    public void addHabitDate(HabitDateEntity habitDateEntity) {
        database.habitDao().addHabitDate(habitDateEntity);
    }

    public void deleteHabitDate(HabitDateEntity habitDateEntity) {
        database.habitDao().deleteHabitDate(habitDateEntity);
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

    public void deleteUser(UserEntity user) {
        database.habitDao().deleteUser(user);
    }
}
