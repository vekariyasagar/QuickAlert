package com.msd.project_group_7.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.msd.project_group_7.R;
import com.msd.project_group_7.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        init();

    }

    private void init() {

        mainBinding.addTask.setOnClickListener(this);
        mainBinding.viewTask.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.addTask){
            startActivity(new Intent(this, TaskNameActivity.class)
                    .putExtra("isFrom", "Add"));
        } else if(view.getId() == R.id.viewTask){
            startActivity(new Intent(this, ViewTaskActivity.class));
        }
    }
}