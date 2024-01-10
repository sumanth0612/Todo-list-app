package com.example.todolist;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {
    private EditText taskNameEditText;
    private EditText taskDescriptionEditText;
    private RadioGroup taskPriorityEditText;
    private TextView taskDueDateTextView;
    private RadioGroup radiogrp;
    private RadioButton radiobtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskNameEditText = findViewById(R.id.taskNameEditText);
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
        taskPriorityEditText = findViewById(R.id.priorityRadioGroup);
        taskDueDateTextView = findViewById(R.id.dueDateTextView);

        taskDueDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                String dueDate = year + "-" + (month + 1) + "-" + day;
                                taskDueDateTextView.setText(dueDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        radiogrp = (RadioGroup) findViewById(R.id.priorityRadioGroup);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = taskNameEditText.getText().toString();
                String taskDescription = taskDescriptionEditText.getText().toString();

                int selectedId = radiogrp.getCheckedRadioButtonId();
                radiobtn = (RadioButton) findViewById(selectedId);
                String taskPriority = radiobtn.getText().toString();
                String taskDueDate = taskDueDateTextView.getText().toString();
                String taskStatus="New";
                Task task = new Task(-1, taskName, taskDescription, taskDueDate, taskPriority, taskStatus);
                TaskDbHelper dbHelper = new TaskDbHelper(AddTaskActivity.this);
                dbHelper.addTask(task);

                finish();
            }
        });
    }
}

