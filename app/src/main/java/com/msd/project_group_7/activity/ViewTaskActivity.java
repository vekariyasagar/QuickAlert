package com.msd.project_group_7.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.wear.widget.WearableLinearLayoutManager;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.msd.project_group_7.R;
import com.msd.project_group_7.adapter.CategoryAdapter;
import com.msd.project_group_7.adapter.TaskAdapter;
import com.msd.project_group_7.databinding.ActivityTaskNameBinding;
import com.msd.project_group_7.databinding.ActivityViewTaskBinding;
import com.msd.project_group_7.model.TaskModel;
import com.msd.project_group_7.utils.DBHelper;

import java.util.ArrayList;

public class ViewTaskActivity extends AppCompatActivity {

    ActivityViewTaskBinding taskBinding;
    ArrayList<TaskModel> taskList = new ArrayList<>();
    DBHelper db;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskBinding = ActivityViewTaskBinding.inflate(getLayoutInflater());
        View view = taskBinding.getRoot();
        setContentView(view);

        init();

    }

    private void init(){
        setLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new DBHelper(this);
        GetAllTasks();
        bindAdapter();
    }

    private void setLayout(){
        taskBinding.rvList.setHasFixedSize(true);
        WearableLinearLayoutManager layoutManager = new WearableLinearLayoutManager(this);
        taskBinding.rvList.setLayoutManager(layoutManager);
    }

    private void bindAdapter(){
        taskAdapter = new TaskAdapter(this, taskList);
        taskBinding.rvList.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();
    }

    void GetAllTasks() {
        taskList = new ArrayList<>();
        Cursor cursor = db.getAllTask();
        if(cursor == null){
            Toast.makeText(this,"Task not found", Toast.LENGTH_LONG).show();
        } else {
            if (cursor.getCount() > 0 ) {
                cursor.moveToFirst();
                do {
                    TaskModel task = new TaskModel();
                    task.setTaskId(cursor.getInt(0));
                    task.setTaskName(cursor.getString(1));
                    task.setTaskCategory(cursor.getString(2));
                    task.setTaskType(cursor.getString(3));
                    task.setTaskDate(cursor.getString(4));
                    task.setTaskTime(cursor.getString(5));
                    task.setTaskMilliseconds(cursor.getLong(6));
                    task.setTaskAddress(cursor.getString(7));
                    task.setTaskLatitude(cursor.getDouble(8));
                    task.setTaskLongitude(cursor.getDouble(9));
                    task.setTaskCompleted(cursor.getInt(10));
                    taskList.add(task);
                } while (cursor.moveToNext());
                cursor.close();
                db.close();
            }
        }
    }


}