package com.msd.project_group_7.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;

import com.msd.project_group_7.R;
import com.msd.project_group_7.databinding.ActivityMainBinding;
import com.msd.project_group_7.databinding.ActivityTaskNameBinding;
import com.msd.project_group_7.model.TaskModel;

import java.util.List;

public class TaskNameActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityTaskNameBinding taskNameBinding;
    private static final int SPEECH_REQUEST_CODE = 100;
    TaskModel taskModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskNameBinding = ActivityTaskNameBinding.inflate(getLayoutInflater());
        View view = taskNameBinding.getRoot();
        setContentView(view);

        init();

    }

    private void init(){

        taskNameBinding.mic.setOnClickListener(this);
        taskNameBinding.next.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.mic){
            Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            startActivityForResult(voiceIntent, SPEECH_REQUEST_CODE);
        } else if(view.getId() == R.id.next){
            if(taskNameBinding.edtTaskName.getText().toString().equals("")){
                Toast.makeText(this, getString(R.string.enter_task_name), Toast.LENGTH_SHORT).show();
            } else {
                taskModel = new TaskModel();
                taskModel.setTaskName(taskNameBinding.edtTaskName.getText().toString());
                startActivity(new Intent(this, LocationActivity.class)
                        .putExtra("isFrom","Add").putExtra("taskModel", taskModel));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && results.size() > 0){
                String txtSpoken = results.get(0);
                taskNameBinding.edtTaskName.setText(txtSpoken);
            }
        }

    }

}