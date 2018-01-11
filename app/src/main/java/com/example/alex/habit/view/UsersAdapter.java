package com.example.alex.habit.view;

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

import com.example.alex.habit.R;
import com.example.alex.habit.model.HabitEntity;
import com.example.alex.habit.model.UserEntity;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private List<UserEntity> userDataset;
    private UserEntity userEntity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView userCardView;
        private TextView userTextView;

        public ViewHolder(View v) {
            super(v);
            userCardView = (CardView) v.findViewById(R.id.user_card_view);
            userTextView = (TextView) v.findViewById(R.id.user_text_view);
        }

        public void setUserEntity(final UserEntity userEntity) {
            if (userEntity.isAdmin()) {
                userTextView.setText(String.format("%s is admin", userEntity.getEmail()));
            } else {
                userTextView.setText(String.format("%s is not admin", userEntity.getEmail()));
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UsersAdapter(List<UserEntity> userDataset) {
        this.userDataset = userDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - getHabitList element from your dataset at this position
        // - replace the contents of the view with that element
        holder.setUserEntity(userDataset.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return userDataset.size();
    }
}
