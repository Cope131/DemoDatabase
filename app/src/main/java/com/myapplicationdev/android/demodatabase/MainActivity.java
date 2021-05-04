package com.myapplicationdev.android.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private DBHelper db = new DBHelper(this);

    // Views
    private Button insertButton, getTasksButton;
    private TextView tasksTextView;
    private ListView tasksListView;

    private ArrayAdapter taskArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init Views
        insertButton = findViewById(R.id.insert_button);
        insertButton.setOnClickListener(this::onClick);
        getTasksButton = findViewById(R.id.get_tasks_button);
        getTasksButton.setOnClickListener(this::onClick);

        tasksTextView = findViewById(R.id.task_text_view);
        tasksListView = findViewById(R.id.tasks_list_view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert_button:
                // Insert new Task Record
                db.insertTask("Submit RJ", "25 April 2016");
                db.close();
                Toast.makeText(this, "New Task Added", Toast.LENGTH_SHORT).show();
                break;
            case R.id.get_tasks_button:
                // Get Tasks
                ArrayList<String> tasks = db.getTaskContent();

                // Task Text
                String dbContent = "";
                int count = 0;
                for (String taskDescription: tasks) {
                    dbContent += String.format("%d %s \n", count, taskDescription);
                    count++;
                }
                tasksTextView.setText(dbContent);

                // Task Object
                ArrayList<Task> taskArrayList = db.getTasks();
                Log.e(TAG, taskArrayList.toString());
                db.close();

                taskArrayAdapter = new TaskArrayAdapter(this, R.layout.task_list_item, taskArrayList);
                tasksListView.setAdapter(taskArrayAdapter);

                break;
        }
    }
}