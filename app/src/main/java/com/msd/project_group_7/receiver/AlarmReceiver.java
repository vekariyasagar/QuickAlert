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
        createNotification(context, taskModel, db);
        Log.e("AA_S", "Notification Triggered");
        Log.e("AA_S", taskId + " --");

    }

    private void createNotification(Context context, TaskModel task, DBHelper db) {

        Intent i = new Intent(context, ViewTaskDetailActivity.class);
        i.putExtra("taskModel", task);
        i.putExtra("isFrom", "notification");
        PendingIntent notificationIntent = PendingIntent.getActivity(context,
                0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String id = "my_channel_01";
        CharSequence name = context.getString(R.string.app_name);
        String description = "reminder";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        mChannel.setDescription(description);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mNotificationManager.createNotificationChannel(mChannel);

        Bitmap bitmap;
        String subText = "";

        if(task.getTaskType().equals("Normal")){
            subText = task.getTaskDate() + " " + task.getTaskTime();
            bitmap = Utils.getBitmapFromDrawableResource(context, Utils.getImageFromCategory(context, task.getTaskCategory()));
            db.setTaskCompletedById(task);
        } else  {
            subText = task.getTaskAddress();
            bitmap = Utils.getBitmapFromDrawableResource(context, R.drawable.location);
        }

        int notifyID = task.getTaskId();
        String CHANNEL_ID = "my_channel_01";
        Notification notification = new Notification.Builder(context)
                .setContentTitle(task.getTaskName())
                .setContentText(subText)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bitmap)
                .setStyle(new Notification.BigPictureStyle().bigPicture(bitmap))
                .setChannelId(CHANNEL_ID)
                .setAutoCancel(true)
                .setContentIntent(notificationIntent)
                .build();

        mNotificationManager.notify(notifyID, notification);

    }

}