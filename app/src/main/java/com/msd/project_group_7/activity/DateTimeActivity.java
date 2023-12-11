package com.msd.project_group_7.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.msd.project_group_7.R;
import com.msd.project_group_7.databinding.ActivityDateTimeBinding;
import com.msd.project_group_7.databinding.ActivityLocationBinding;
import com.msd.project_group_7.model.TaskModel;
import com.msd.project_group_7.utils.DBHelper;

import java.util.Calendar;

public class DateTimeActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityDateTimeBinding dateTimeBinding;
    String dueDate = "", dueTime = "";
    TimePickerDialog timePickerDialog;
    int mYear, mMonth, mDay;
    Calendar calSet;
    String isFrom = "";
    TaskModel taskModel;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dateTimeBinding = ActivityDateTimeBinding.inflate(getLayoutInflater());
        View view = dateTimeBinding.getRoot();
        setContentView(view);

        isFrom = getIntent().getStringExtra("isFrom");
        taskModel = (TaskModel) getIntent().getSerializableExtra("taskModel");

        init();

    }

    private void init() {

        db = new DBHelper(this);

        dateTimeBinding.dateLout.setOnClickListener(this);
        dateTimeBinding.timeLout.setOnClickListener(this);
        dateTimeBinding.done.setOnClickListener(this);

        if(isFrom.equals("edit")) {

            dueDate = taskModel.getTaskDate();
            dueTime = taskModel.getTaskTime();
            dateTimeBinding.tvDate.setText(dueDate);
            dateTimeBinding.tvTime.setText(dueTime);

        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.dateLout){
            datePickerDialog();
        } else if(view.getId() == R.id.timeLout){
            timePickerDialog();
        } else if(view.getId() == R.id.done){
            if(dueDate.equals("")) {
                Toast.makeText(this, getString(R.string.enter_task_date), Toast.LENGTH_SHORT).show();
            } else if(dueTime.equals("")) {
                Toast.makeText(this, getString(R.string.enter_task_time), Toast.LENGTH_SHORT).show();
            } else {
                if(isFrom.equals("edit")) {
                    if(!taskModel.getTaskDate().equals(dateTimeBinding.tvDate.getText().toString())){
                        taskModel.setTaskMilliseconds(calSet.getTimeInMillis());
                        taskModel.setTaskDate(dueDate);
                        taskModel.setTaskTime(dueTime);
                        taskModel.setTaskAddress("");
                        taskModel.setTaskLatitude(0);
                        taskModel.setTaskLongitude(0);
                    }
                    boolean updateStatus = db.updateTaskById(taskModel);
                    if (updateStatus) {
                        Toast.makeText(this, getString(R.string.task_updated_successfully), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else {
                        Toast.makeText(this, getString(R.string.failure), Toast.LENGTH_LONG).show();
                    }
                } else {
                    taskModel.setTaskDate(dueDate);
                    taskModel.setTaskTime(dueTime);
                    taskModel.setTaskMilliseconds(calSet.getTimeInMillis());
                    boolean insertStatus = db.addTask(taskModel);
                    if (insertStatus) {
                        Toast.makeText(this, getString(R.string.task_added_successfully), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else {
                        Toast.makeText(this, getString(R.string.failure), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private void datePickerDialog(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dueDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                dateTimeBinding.tvDate.setText(dueDate);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.setTitle(getString(R.string.select_date));
        datePickerDialog.show();
    }

    private void timePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar calNow = Calendar.getInstance();
                        calSet = (Calendar) calNow.clone();
                        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calSet.set(Calendar.MINUTE, minute);
                        calSet.set(Calendar.SECOND, 0);
                        calSet.set(Calendar.MILLISECOND, 0);
                        if (calSet.compareTo(calNow) <= 0) {
                            calSet.add(Calendar.DATE, 1);
                        }

                        String timeSet = "";
                        if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            timeSet = "PM";
                        } else if (hourOfDay == 0) {
                            hourOfDay += 12;
                            timeSet = "AM";
                        } else if (hourOfDay == 12){
                            timeSet = "PM";
                        }else{
                            timeSet = "AM";
                        }

                        String min = "";
                        if (minute < 10)
                            min = "0" + minute ;
                        else
                            min = String.valueOf(minute);

                        // Append in a StringBuilder
                        String aTime = new StringBuilder().append(hourOfDay).append(':')
                                .append(min).append(" ").append(timeSet).toString();
                        dueTime = aTime;
                        dateTimeBinding.tvTime.setText(dueTime);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), false);
        timePickerDialog.setTitle(getString(R.string.select_time));
        timePickerDialog.show();
    }


}