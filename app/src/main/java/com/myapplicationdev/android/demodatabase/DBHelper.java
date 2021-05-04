package com.myapplicationdev.android.demodatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasks.db";

    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = String.format("CREATE TABLE %s(", TABLE_TASK);
        createTableSql += String.format("%s INTEGER PRIMARY KEY AUTOINCREMENT,", COLUMN_ID);
        createTableSql += String.format("%s TEXT,", COLUMN_DATE);
        createTableSql += String.format("%s TEXT", COLUMN_DESCRIPTION);
        createTableSql += ")";

        db.execSQL(createTableSql);
        Log.e("info", "Created Task Table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Delete Task Table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        // Create Task Table
        onCreate(db);
    }

    public void insertTask(String description, String date) {
        SQLiteDatabase writableDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        // Values
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DATE, date);
        // Insertion
        writableDatabase.insert(TABLE_TASK, null, values);
        Log.e("info", "Inserted new task record");
        // Close Database writable
        writableDatabase.close();
    }

    public ArrayList<String> getTaskContent() {
        SQLiteDatabase readableDatabase = getReadableDatabase();

        ArrayList<String> tasks = new ArrayList<>();

        // Query Task
        String getTaskQuery = String.format("SELECT %s, %s, %s FROM %s", COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_DATE, TABLE_TASK);
        Cursor cursor = readableDatabase.rawQuery(getTaskQuery, null);

        if (cursor.moveToFirst()) {
            do  {
                tasks.add(cursor.getString(1));
                // 1st Column - id
                Log.e(TAG + "in getTaskContent: ", cursor.getInt(0) + "");
                // 2nd Column - description
                Log.e(TAG + "in getTaskContent: ", cursor.getString(1));
                // 3rd Column - date
                Log.e(TAG + "in getTaskContent: ", cursor.getString(2));
            } while ((cursor.moveToNext()));
        }

        cursor.close();
        readableDatabase.close();

        return tasks;
    }

    public ArrayList<Task> getTasks() {
        SQLiteDatabase readableDatabase = getReadableDatabase();

        ArrayList<Task> tasks = new ArrayList<>();

        // Query Task
        String getTaskQuery = String.format("SELECT %s, %s, %s FROM %s", COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_DATE, TABLE_TASK);
        Cursor cursor = readableDatabase.rawQuery(getTaskQuery, null);

        if (cursor.moveToFirst()) {
            do {
                // 1st Select Col - id
                int id = cursor.getInt(0);
                // 2nd Select Col - description
                String description = cursor.getString(1);
                // 3rd Select Col - date
                String date = cursor.getString(2);
                // Add Task to Array List
                tasks.add(new Task(id, description, date));
            } while (cursor.moveToNext());
        }

        Log.e(TAG + "in getTasks: ", tasks.toString());

        return tasks;
    }
}
