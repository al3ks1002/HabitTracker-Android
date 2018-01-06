package com.example.alex.myapplication.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.alex.myapplication.R;
import com.example.alex.myapplication.model.HabitEntity;

import java.util.List;

public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.ViewHolder> {
    private List<HabitEntity> habitDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView habitCardView;
        private TextView titleTextView;
        private TextView descriptionTextView;
        private Button editButton;
        private Button detailsButton;

        public ViewHolder(View v) {
            super(v);
            habitCardView = (CardView) v.findViewById(R.id.habit_card_view);
            titleTextView = (TextView) v.findViewById(R.id.habit_title_text_view);
            descriptionTextView = (TextView) v.findViewById(R.id.habit_description_text_view);
            editButton = (Button) v.findViewById(R.id.habit_edit_button);
            detailsButton = (Button) v.findViewById(R.id.habit_details_button);
        }

        public void setHabitEntity(final HabitEntity habitEntity) {
            titleTextView.setText(habitEntity.getTitle());
            descriptionTextView.setText(habitEntity.getDescription());
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent myIntent = new Intent(context, EditHabitActivity.class);
                    myIntent.putExtra("habit_entity", habitEntity);
                    ((Activity) context).startActivityForResult(myIntent, 1);
                }
            });
            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent myIntent = new Intent(context, ViewHabitActivity.class);
                    myIntent.putExtra("habit_entity", habitEntity);
                    ((Activity) context).startActivityForResult(myIntent, 2);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HabitsAdapter(List<HabitEntity> habitDataset) {
        this.habitDataset = habitDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HabitsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.setHabitEntity(habitDataset.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return habitDataset.size();
    }

    public void notifyUpdate(List<HabitEntity> habitList) {
        if (habitDataset != null) {
            habitDataset.clear();
            habitDataset.addAll(habitList);

        } else {
            habitDataset = habitList;
        }
        notifyDataSetChanged();
        for (HabitEntity habit : habitDataset) {
            System.out.println(habit.getId());
            System.out.println(habit.getTitle());
        }
    }
}
