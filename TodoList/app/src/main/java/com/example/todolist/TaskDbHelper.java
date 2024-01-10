package com.example.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class TaskDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TASKS_TABLE =
            "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + " (" +
                    TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TaskContract.TaskEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                    TaskContract.TaskEntry.COLUMN_DESCRIPTION + " TEXT," +
                    TaskContract.TaskEntry.COLUMN_DUE_DATE + " TEXT," +
                    TaskContract.TaskEntry.COLUMN_PRIORITY + " TEXT," +
                    TaskContract.TaskEntry.COLUMN_STATUS + " TEXT NOT NULL)";

    private static final String SQL_DELETE_TASKS_TABLE =
            "DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NAME;

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TASKS_TABLE);
        onCreate(db);
    }
    public List<Task> getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> taskList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TaskContract.TaskEntry.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return taskList;
    }

    public long addTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TITLE, task.getTitle());
        values.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, task.getDescription());
        values.put(TaskContract.TaskEntry.COLUMN_DUE_DATE, task.getDueDate());
        values.put(TaskContract.TaskEntry.COLUMN_PRIORITY, task.getPriority());
        values.put(TaskContract.TaskEntry.COLUMN_STATUS, task.getStatus());

        long newRowId = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);

        db.close();

        return newRowId;
    }

    public List<Task> getTasks() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COLUMN_TITLE,
                TaskContract.TaskEntry.COLUMN_DESCRIPTION,
                TaskContract.TaskEntry.COLUMN_DUE_DATE,
                TaskContract.TaskEntry.COLUMN_PRIORITY,
                TaskContract.TaskEntry.COLUMN_STATUS
        };

        String sortOrder =
                TaskContract.TaskEntry.COLUMN_STATUS + " ASC, " +
                        TaskContract.TaskEntry.COLUMN_DUE_DATE + " ASC";

        Cursor cursor = db.query(
                TaskContract.TaskEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<Task> tasks = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_DESCRIPTION));
            String dueDate = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_DUE_DATE));
            String priority = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_PRIORITY));
            String status = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_STATUS));

            Task task = new Task(id, title, description, dueDate, priority, status);
            tasks.add(task);
        }

        cursor.close();
        db.close();

        return tasks;
    }

    public int updateTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TITLE, task.getTitle());
        values.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, task.getDescription());
        values.put(TaskContract.TaskEntry.COLUMN_DUE_DATE, task.getDueDate());
        values.put(TaskContract.TaskEntry.COLUMN_PRIORITY, task.getPriority());
        values.put(TaskContract.TaskEntry.COLUMN_STATUS, task.getStatus());

        String selection = TaskContract.TaskEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(task.getId())};

        int count = db.update(
                TaskContract.TaskEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        db.close();

        return count;
    }

    public int deleteTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = TaskContract.TaskEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(task.getId())};

        int count = db.delete(
                TaskContract.TaskEntry.TABLE_NAME,
                selection,
                selectionArgs
        );

        db.close();

        return count;
    }
}

