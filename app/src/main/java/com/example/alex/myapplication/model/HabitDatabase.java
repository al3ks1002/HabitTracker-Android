package com.example.alex.myapplication.model;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {HabitEntity.class, HabitDate.class}, version = 2)
@TypeConverters(DateConverter.class)
public abstract class HabitDatabase extends RoomDatabase {
    public abstract HabitDao habitDao();
}
