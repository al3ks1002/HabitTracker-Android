package com.example.alex.habit.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface HabitDao {
    @Query("SELECT * FROM habits WHERE email is :email")
    List<HabitEntity> getHabitList(String email);

    @Query("SELECT * FROM habits")
    List<HabitEntity> getAllHabits();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addHabit(HabitEntity habit);

    @Delete
    void deleteHabit(HabitEntity habit);

    @Query("Select * FROM habit_date")
    List<HabitDateEntity> getHabitDateList();

    @Query("Select date FROM habit_date WHERE habitId is :id")
    List<Date> getDatesForHabit(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addHabitDate(HabitDateEntity habitDateEntity);

    @Delete
    void deleteHabitDate(HabitDateEntity habitDateEntity);

    @Query("SELECT * FROM users WHERE email is :email")
    UserEntity getUser(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(UserEntity user);

    @Query("SELECT * FROM users")
    List<UserEntity> getUserList();

    @Delete
    void deleteUser(UserEntity user);
}
