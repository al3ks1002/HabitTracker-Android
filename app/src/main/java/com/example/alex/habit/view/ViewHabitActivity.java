package com.example.alex.habit.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.alex.habit.R;
import com.example.alex.habit.model.DateUtils;
import com.example.alex.habit.model.HabitEntity;
import com.example.alex.habit.model.HabitRepository;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ViewHabitActivity extends AppCompatActivity {
    private HabitRepository habitRepository;

    private TextView titleText;
    private TextView descriptionText;
    private Button addDateButton;
    private Button removeDateButton;
    private GraphView graph;

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            Intent returnIntent = new Intent();

            returnIntent.putExtra("habitId", getArguments().getInt("habitId"));
            returnIntent.putExtra("date", new Date(year, month, day));
            if (getArguments().getString("operation").equals("addHabit")) {
                returnIntent.putExtra("addOperation", true);
            }

            getActivity().setResult(Activity.RESULT_OK, returnIntent);
            getActivity().finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);

        habitRepository = HabitRepository.getInstance(getApplicationContext());

        titleText = (TextView) findViewById(R.id.title_text);
        descriptionText = (TextView) findViewById(R.id.description_text);
        addDateButton = (Button) findViewById(R.id.add_date_button);
        removeDateButton = (Button) findViewById(R.id.remove_date_button);
        graph = (GraphView) findViewById(R.id.graph);

        final HabitEntity habitEntity = (HabitEntity) getIntent()
                .getExtras().getSerializable("habit_entity");

        titleText.setText(habitEntity.getTitle());
        descriptionText.setText(habitEntity.getDescription());

        addDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                Bundle bundle = new Bundle(2);
                bundle.putString("operation", "addHabit");
                bundle.putInt("habitId", habitEntity.getId());
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        removeDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                Bundle bundle = new Bundle(2);
                bundle.putString("operation", "deleteHabit");
                bundle.putInt("habitId", habitEntity.getId());
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        List<Date> habitDates = habitRepository.getDates(habitEntity.getId());
        if (habitDates.isEmpty()) {
            return;
        }
        Collections.sort(habitDates);

        List<DataPoint> dataPointList = new ArrayList<>();
        dataPointList.add(new DataPoint(habitDates.get(0), 1));
        for (int i = 0; i < habitDates.size() - 1; ++i) {
            LocalDate start = DateUtils.asLocalDate(habitDates.get(i));
            start = start.plusDays(1);

            dataPointList.add(new DataPoint(habitDates.get(i + 1), 1));
            LocalDate end = DateUtils.asLocalDate(habitDates.get(i + 1));
            end = end.minusDays(1);

            while (!start.isAfter(end)) {
                dataPointList.add(new DataPoint(DateUtils.asDate(start), 0));
                start = start.plusDays(1);
            }
        }

        DataPoint[] dataPoints = new DataPoint[dataPointList.size()];
        dataPointList.toArray(dataPoints);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);

        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer()
                .setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
        graph.getGridLabelRenderer()
                .setNumHorizontalLabels(habitDates.size());

        // set manual x bounds to have nice steps
        if (!habitDates.isEmpty()) {
            graph.getViewport().setMinX(habitDates.get(0).getTime());
            graph.getViewport().setMaxX(habitDates.get(habitDates.size() - 1).getTime());
            graph.getViewport().setXAxisBoundsManual(true);
        }

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
    }
}

