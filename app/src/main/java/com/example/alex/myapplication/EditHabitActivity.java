package com.example.alex.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alex.myapplication.domain.HabitEntity;

public class EditHabitActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button saveChangesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);

        titleEditText = (EditText) findViewById(R.id.title_edit_text);
        descriptionEditText = (EditText) findViewById(R.id.description_edit_text);
        saveChangesButton = (Button) findViewById(R.id.save_changes_button);

        final HabitEntity habitEntity = (HabitEntity) getIntent()
                .getExtras().getSerializable("habit_entity");

        titleEditText.setText(habitEntity.getTitle());
        descriptionEditText.setText(habitEntity.getDescription());
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HabitEntity modifiedHabitEntity = new HabitEntity(
                        habitEntity.getId(),
                        String.valueOf(titleEditText.getText()),
                        String.valueOf(descriptionEditText.getText()));

                // Intent to modify the habit.
                Intent modifyIntent = new Intent(EditHabitActivity.this,
                        MainActivity.class);
                modifyIntent.putExtra("modified_habit_entity", modifiedHabitEntity);
                startActivity(modifyIntent);

                // Intent to send an email.
                Intent sendMailIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("mailto:" + Uri.encode("al3ks1002@gmail.com")));
                sendMailIntent.putExtra(Intent.EXTRA_SUBJECT, "Habit update");
                sendMailIntent.putExtra(Intent.EXTRA_TEXT, "Habit was updated!" + "\n"
                        + "New title: " + modifiedHabitEntity.getTitle()
                        + '\n' + "New description: " + modifiedHabitEntity.getDescription());
                startActivity(sendMailIntent);
            }
        });
    }

}
