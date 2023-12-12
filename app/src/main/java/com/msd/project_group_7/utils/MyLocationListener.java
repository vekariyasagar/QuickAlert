package com.msd.project_group_7.utils;

import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.msd.project_group_7.MyApplicationClass;
import com.msd.project_group_7.model.TaskModel;

import java.util.ArrayList;

public class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location loc) {

        DBHelper db = new DBHelper(MyApplicationClass.getAppContext());
        ArrayList<TaskModel> taskList = GetLocationBasedTasks(db);
        if(taskList.size() > 0) {
            for (int i = 0; i < taskList.size(); i++) {


                String longitude = "Longitude: " + loc.getLongitude();
                Log.v("AA_S", longitude);
                String latitude = "Latitude: " + loc.getLatitude();
                Log.v("AA_S", latitude);

                Location dest = new Location("custom_provider"); // You can provide a custom provider name
                dest.setLatitude(taskList.get(i).getTaskLatitude());
                dest.setLongitude(taskList.get(i).getTaskLongitude());

                float distance = loc.distanceTo(new Location(dest));
                Log.e("AA_S", distance + " -- ");

                if(distance <= 200){
                    db.setTaskCompletedById(taskList.get(i));
                    Utils.createNotification(MyApplicationClass.getAppContext(), taskList.get(i), db);
                }

            }
        }
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    ArrayList<TaskModel> GetLocationBasedTasks(DBHelper db) {

        ArrayList<TaskModel> taskList = new ArrayList<>();
        Cursor cursor = db.getLocationBasedTask();
        if(cursor != null) {
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
        return taskList;
    }
}