package com.example.paulosousa.todolist;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
            AdapterView.OnItemLongClickListener {

    public static final String TASKS_TAG = "tasks";

    EditText newTask;
    Button buttonNewTask;
    ListView tasksList;

    ArrayList<String> tasks;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksList = (ListView) findViewById(R.id.tasksList);
        newTask = (EditText)findViewById(R.id.new_task);
        buttonNewTask = (Button)findViewById(R.id.button_new_task);
        buttonNewTask.setOnClickListener(this);

        tasks = getTasks(savedInstanceState);
        adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                tasks);
        tasksList.setAdapter(adapter);
        tasksList.setOnItemLongClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList(TASKS_TAG, tasks);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        String task = newTask.getText().toString().trim();
        switch (view.getId()){
            case R.id.button_new_task:
                updateTasksList(task);
                newTask.setText("");
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        updateTasksList(position);
        return true;
    }

    private ArrayList<String> getTasks(Bundle savedInstanceState) {
        if(savedInstanceState == null) return new ArrayList<>();

        return savedInstanceState.getStringArrayList(TASKS_TAG);
    }

    private void updateTasksList(String task){
        if(task.isEmpty()) return;

        tasks.add(task);
        adapter.notifyDataSetChanged();
    }

    private void updateTasksList(int position){
        tasks.remove(position);
        adapter.notifyDataSetChanged();
    }
}
