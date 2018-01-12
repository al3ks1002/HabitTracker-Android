package com.example.alex.habit.model;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.alex.habit.utils.DateConverter;

@Database(entities = {HabitEntity.class, HabitDateEntity.class, UserEntity.class}, version = 3)
@TypeConverters(DateConverter.class)
public abstract class HabitDatabase extends RoomDatabase {
    public abstract HabitDao habitDao();
}
