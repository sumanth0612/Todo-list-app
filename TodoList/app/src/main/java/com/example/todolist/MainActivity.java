package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private TaskDbHelper myDB;
    private List<Task> mList;
    private TaskListAdapter adapter;
    public static final String TAG = "AddNewTask";

    @Override
    public void onDialogClose() {
        TaskDbHelper dbHelper = new TaskDbHelper(this);
        List<Task> tasks = dbHelper.getTasks();
        Collections.reverse(tasks);
        TaskListAdapter adapter = new TaskListAdapter(tasks);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);
        myDB = new TaskDbHelper(MainActivity.this);
        mList = new ArrayList<>();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        mList = myDB.getAllTasks();
        Collections.reverse(mList);
        adapter = new TaskListAdapter(mList);



    }


}




