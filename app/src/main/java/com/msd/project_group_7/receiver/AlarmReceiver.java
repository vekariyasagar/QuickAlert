package com.msd.project_group_7.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.msd.project_group_7.R;
import com.msd.project_group_7.activity.ViewTaskDetailActivity;
import com.msd.project_group_7.model.TaskModel;
import com.msd.project_group_7.utils.DBHelper;
import com.msd.project_group_7.utils.Utils;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        DBHelper db = new DBHelper(context);
        int taskId = intent.getIntExtra("TASK_ID", 0);
        TaskModel taskModel = db.getTaskById(taskId);
        Utils.createNotification(context, taskModel, db);
        Log.e("AA_S", "Notification Triggered");
        Log.e("AA_S", taskId + " --");

    }



}