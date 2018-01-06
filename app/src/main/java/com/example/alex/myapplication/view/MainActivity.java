package com.example.alex.myapplication.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.alex.myapplication.R;
import com.example.alex.myapplication.model.HabitDate;
import com.example.alex.myapplication.model.HabitEntity;
import com.example.alex.myapplication.model.HabitRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView habitsRecyclerView;
    private HabitsAdapter habitsAdapter;
    private RecyclerView.LayoutManager habitsLayoutManager;
    private HabitRepository habitRepository;

    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (FloatingActionButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), EditHabitActivity.class);
                myIntent.putExtra("habit_entity", new HabitEntity());
                myIntent.putExtra("create", true);
                startActivityForResult(myIntent, 1);
            }
        });

        habitRepository = HabitRepository.getInstance(getApplicationContext());
        habitsRecyclerView = (RecyclerView) findViewById(R.id.habits_recycler_view);

        // use a linear layout manager
        habitsLayoutManager = new LinearLayoutManager(this);
        habitsRecyclerView.setLayoutManager(habitsLayoutManager);

        // specify an adapter
        habitsAdapter = new HabitsAdapter(habitRepository.get());
        habitsRecyclerView.setAdapter(habitsAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                HabitEntity habit = (HabitEntity) data.
                        getExtras().getSerializable("habit_entity");
                if (data.hasExtra("delete")) {
                    habitRepository.delete(habit);
                } else {
                    habitRepository.add(habit);
                }
                List<HabitEntity> modifiedHabitList = habitRepository.get();
                habitsAdapter.notifyUpdate(modifiedHabitList);
            }
        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                int habitId = data.getExtras().getInt("habitId");
                Date date = (Date) data.getExtras().getSerializable("date");

                if (data.hasExtra("addOperation")) {
                    habitRepository.addHabitDate(new HabitDate(habitId, date));
                } else {
                    habitRepository.deleteHabitDate(new HabitDate(habitId, date));
                }

                List<Date> dates = habitRepository.getDates(habitId);
                for (Date mlc : dates) {
                    System.out.println(mlc);
                }
            }
        }
    }
}
