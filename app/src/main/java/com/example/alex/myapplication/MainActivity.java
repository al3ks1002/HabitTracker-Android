package com.example.alex.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import com.example.alex.myapplication.domain.HabitDataset;
import com.example.alex.myapplication.domain.HabitEntity;

import java.util.List;

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

        if (getIntent().hasExtra("modified_habit_entity")) {
            HabitEntity modifiedHabit = (HabitEntity) getIntent()
                    .getExtras().getSerializable("modified_habit_entity");
            habitDataset.addOrUpdateHabit(modifiedHabit);
        }

        // specify an adapter (see also next example)
        habitsAdapter = new HabitsAdapter(habitDataset.getList());
        habitsRecyclerView.setAdapter(habitsAdapter);
    }
}
