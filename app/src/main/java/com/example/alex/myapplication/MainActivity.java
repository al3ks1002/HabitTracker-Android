package com.example.alex.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import com.example.alex.myapplication.domain.HabitDataset;
import com.example.alex.myapplication.domain.HabitEntity;

public class MainActivity extends AppCompatActivity {
    private RecyclerView habitsRecyclerView;
    private RecyclerView.Adapter habitsAdapter;
    private RecyclerView.LayoutManager habitsLayoutManager;
    private HabitDataset habitDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        habitDataset = HabitDataset.getInstance();

        habitsRecyclerView = (RecyclerView) findViewById(R.id.habits_recycler_view);

        // use a linear layout manager
        habitsLayoutManager = new LinearLayoutManager(this);
        habitsRecyclerView.setLayoutManager(habitsLayoutManager);

        // specify an adapter
        habitsAdapter = new HabitsAdapter(habitDataset.getList());
        habitsRecyclerView.setAdapter(habitsAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                HabitEntity modifiedHabit = (HabitEntity) data.
                        getExtras().getSerializable("modified_habit_entity");
                habitDataset.addOrUpdateHabit(modifiedHabit);
                habitsAdapter.notifyDataSetChanged();
            }
        }
    }
}
