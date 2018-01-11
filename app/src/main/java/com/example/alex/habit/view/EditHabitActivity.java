package com.example.alex.habit.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alex.habit.R;
import com.example.alex.habit.model.HabitEntity;
import com.example.alex.habit.model.UserEntity;

public class EditHabitActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button saveChangesButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);

        titleEditText = (EditText) findViewById(R.id.title_edit_text);
        descriptionEditText = (EditText) findViewById(R.id.description_edit_text);
        saveChangesButton = (Button) findViewById(R.id.save_changes_button);
        deleteButton = (Button) findViewById(R.id.delete_butoon);

        final HabitEntity habitEntity = (HabitEntity) getIntent()
                .getExtras().getSerializable("habit_entity");

        final UserEntity userEntity = (UserEntity) getIntent()
                .getExtras().getSerializable("user_entity");

        titleEditText.setText(habitEntity.getTitle());
        descriptionEditText.setText(habitEntity.getDescription());
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HabitEntity modifiedHabitEntity = new HabitEntity(
                        habitEntity.getId(),
                        String.valueOf(titleEditText.getText()),
                        String.valueOf(descriptionEditText.getText()),
                        userEntity.getEmail());

                // Intent to send an email.
                Intent sendMailIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("mailto:" + Uri.encode("al3ks1002@gmail.com")));
                sendMailIntent.putExtra(Intent.EXTRA_SUBJECT, "Habit update");
                sendMailIntent.putExtra(Intent.EXTRA_TEXT, "Habit was updated!" + "\n"
                        + "New title: " + modifiedHabitEntity.getTitle()
                        + '\n' + "New description: " + modifiedHabitEntity.getDescription());
                startActivity(sendMailIntent);

                // Intent to modify the habit.
                Intent returnIntent = new Intent();
                returnIntent.putExtra("habit_entity", modifiedHabitEntity);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        if (!getIntent().hasExtra("create")) {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Intent to deleteHabit the habit.
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("habit_entity", habitEntity);
                    returnIntent.putExtra("deleteHabit", true);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            });
        }
    }

}
