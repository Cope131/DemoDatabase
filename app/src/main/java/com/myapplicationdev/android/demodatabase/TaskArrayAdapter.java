package com.myapplicationdev.android.demodatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TaskArrayAdapter extends ArrayAdapter<Task> {

    private Context context;
    private ArrayList<Task> taskArrayList;
    private int resource;

    public TaskArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> taskArrayList) {
        super(context, resource, taskArrayList);
        this.context = context;
        this.taskArrayList = taskArrayList;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Inflate View
        View view = LayoutInflater.from(context).inflate(resource, parent, false);

        // Views
        TextView idTV, descTV, dateTV;
        idTV = view.findViewById(R.id.id_text_view);
        descTV = view.findViewById(R.id.description_text_view);
        dateTV = view.findViewById(R.id.date_text_view);

        // Get Data
        Task task = taskArrayList.get(position);

        // Set Data of View
        idTV.setText(task.getId() + "");
        descTV.setText(task.getDescription());
        dateTV.setText(task.getDate());

        return view;
    }
}
