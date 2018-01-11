package com.example.alex.habit.view;

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
import android.widget.TextView;

import com.example.alex.habit.R;
import com.example.alex.habit.model.HabitDate;
import com.example.alex.habit.model.HabitEntity;
import com.example.alex.habit.model.HabitRepository;
import com.example.alex.habit.model.UserEntity;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private HabitRepository habitRepository;

    private RecyclerView habitsRecyclerView;
    private RecyclerView.LayoutManager habitsLayoutManager;
    private HabitsAdapter habitsAdapter;

    private RecyclerView usersRecyclerView;
    private RecyclerView.LayoutManager usersLayoutManager;
    private UsersAdapter usersAdapter;

    private FloatingActionButton addButton;
    private UserEntity userEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        habitRepository = HabitRepository.getInstance(getApplicationContext());

        userEntity = (UserEntity) getIntent()
                .getExtras().getSerializable("user_entity");

        TextView welcomeMessage = (TextView) findViewById(R.id.user_mail);
        if (!userEntity.isAdmin()) {
            welcomeMessage.setText(String.format("Welcome %s - you are a regular user", userEntity.getEmail()));

            addButton = (FloatingActionButton) findViewById(R.id.add_button);
            addButton.setVisibility(View.VISIBLE);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(getApplicationContext(), EditHabitActivity.class);
                    myIntent.putExtra("habit_entity", new HabitEntity(userEntity.getEmail()));
                    myIntent.putExtra("user_entity", userEntity);
                    myIntent.putExtra("create", true);
                    startActivityForResult(myIntent, 1);
                }
            });

            habitsRecyclerView = (RecyclerView) findViewById(R.id.habits_recycler_view);
            habitsRecyclerView.setVisibility(View.VISIBLE);

            // use a linear layout manager
            habitsLayoutManager = new LinearLayoutManager(this);
            habitsRecyclerView.setLayoutManager(habitsLayoutManager);

            // specify an adapter
            habitsAdapter = new HabitsAdapter(habitRepository.getHabitList(userEntity.getEmail()), userEntity);
            habitsRecyclerView.setAdapter(habitsAdapter);
        } else {
            welcomeMessage.setText(String.format("Welcome %s - you are an admin user", userEntity.getEmail()));

            usersRecyclerView = (RecyclerView) findViewById(R.id.users_recycler_view);
            usersRecyclerView.setVisibility(View.VISIBLE);

            // use a linear layout manager
            usersLayoutManager = new LinearLayoutManager(this);
            usersRecyclerView.setLayoutManager(usersLayoutManager);

            // specify an adapter
            usersAdapter = new UsersAdapter(habitRepository.getUserList());
            usersRecyclerView.setAdapter(usersAdapter);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                HabitEntity habit = (HabitEntity) data.
                        getExtras().getSerializable("habit_entity");
                if (data.hasExtra("deleteHabit")) {
                    habitRepository.deleteHabit(habit);
                } else {
                    habitRepository.addHabit(habit);
                }
                List<HabitEntity> modifiedHabitList = habitRepository.getHabitList(userEntity.getEmail());
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
