package com.msd.project_group_7.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.msd.project_group_7.R;
import com.msd.project_group_7.databinding.ActivityCategoryBinding;
import com.msd.project_group_7.databinding.ActivityLocationBinding;
import com.msd.project_group_7.model.TaskModel;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityLocationBinding locationBinding;
    String isFrom = "";
    TaskModel taskModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationBinding = ActivityLocationBinding.inflate(getLayoutInflater());
        View view = locationBinding.getRoot();
        setContentView(view);

        isFrom = getIntent().getStringExtra("isFrom");
        taskModel = (TaskModel) getIntent().getSerializableExtra("taskModel");

        init();

    }

    private void init() {

        locationBinding.no.setOnClickListener(this);
        locationBinding.yes.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.no) {
            taskModel.setTaskType("Normal");
            startActivity(new Intent(this, CategoryActivity.class)
                    .putExtra("isFrom",isFrom).putExtra("taskModel", taskModel));
        } else if(view.getId() == R.id.yes) {
            taskModel.setTaskType("Location");
            startActivity(new Intent(this, AddressActivity.class)
                    .putExtra("isFrom",isFrom).putExtra("taskModel", taskModel));
        }
    }
}