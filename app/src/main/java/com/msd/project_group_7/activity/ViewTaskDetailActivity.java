package com.msd.project_group_7.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.msd.project_group_7.R;
import com.msd.project_group_7.databinding.ActivityViewTaskBinding;
import com.msd.project_group_7.databinding.ActivityViewTaskDetailBinding;
import com.msd.project_group_7.model.TaskModel;
import com.msd.project_group_7.utils.DBHelper;
import com.msd.project_group_7.utils.Utils;

public class ViewTaskDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityViewTaskDetailBinding detailBinding;
    TaskModel taskModel;
    DBHelper db;
    String isFrom = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailBinding = ActivityViewTaskDetailBinding.inflate(getLayoutInflater());
        View view = detailBinding.getRoot();
        setContentView(view);

        isFrom = getIntent().getStringExtra("isFrom");
        taskModel = (TaskModel) getIntent().getSerializableExtra("taskModel");

        init();
    }

    private void init(){

        db = new DBHelper(this);

        setData();

        detailBinding.delete.setOnClickListener(this);
        detailBinding.update.setOnClickListener(this);

        if(isFrom.equals("notification")){
            detailBinding.bottomLout.setVisibility(View.GONE);
        } else {
            detailBinding.bottomLout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.delete) {
            int taskId = taskModel.getTaskId();
            boolean deleteStatus = db.deleteTaskById(taskModel);
            if(deleteStatus) {
                Utils.cancelAlarm(this, taskId);
                Toast.makeText(this,getString(R.string.task_delete_successfully), Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this,getString(R.string.failure), Toast.LENGTH_LONG).show();
            }
        } else if(view.getId() == R.id.update){
            startActivity(new Intent(this, TaskNameActivity.class)
                    .putExtra("isFrom", "edit")
                    .putExtra("taskModel", taskModel));
        }
    }

    private void setData(){
        if(taskModel.getTaskType().equals("Location")){
            detailBinding.imgCategory.setImageResource(R.drawable.location);
            detailBinding.txtAddress.setText(taskModel.getTaskAddress());
            detailBinding.txtAddress.setVisibility(View.VISIBLE);
            detailBinding.dateLout.setVisibility(View.GONE);
            detailBinding.timeLout.setVisibility(View.GONE);
        } else {
            detailBinding.imgCategory.setImageResource(Utils.getImageFromCategory(this, taskModel.getTaskCategory()));
            detailBinding.txtDate.setText(taskModel.getTaskDate());
            detailBinding.txtTime.setText(taskModel.getTaskTime());
            detailBinding.txtAddress.setVisibility(View.GONE);
            detailBinding.dateLout.setVisibility(View.VISIBLE);
            detailBinding.timeLout.setVisibility(View.VISIBLE);
        }

        detailBinding.txtName.setText(taskModel.getTaskName());
    }

}