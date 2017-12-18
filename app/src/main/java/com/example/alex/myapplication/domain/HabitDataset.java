package com.example.alex.myapplication.domain;

import java.util.ArrayList;
import java.util.List;

public class HabitDataset {
    private List<HabitEntity> habitList;

    private static HabitDataset instance = null;

    private HabitDataset() {
        habitList = new ArrayList<>();
        habitList.add(new HabitEntity(1, "Gym", "Go to gym"));
        habitList.add(new HabitEntity(2, "Read", "Read at least 15 minutes"));
        habitList.add(new HabitEntity(3, "Water", "Drink at least 2 liters of water"));
        habitList.add(new HabitEntity(4, "habit4", "habit4 description"));
        habitList.add(new HabitEntity(5, "habit5", "habit5 description"));
        habitList.add(new HabitEntity(6, "habit6", "habit6 description"));
    }

    public static HabitDataset getInstance() {
        if(instance == null) {
            instance = new HabitDataset();
        }
        return instance;
    }

    public List<HabitEntity> getList() {
        return habitList;
    }

    public void addOrUpdateHabit(HabitEntity newHabit) {
        for (HabitEntity habit : habitList) {
            if (habit.getId() == newHabit.getId()) {
                habit.setTitle(newHabit.getTitle());
                habit.setDescription(newHabit.getDescription());
                return;
            }
        }
        habitList.add(newHabit);
    }
}
