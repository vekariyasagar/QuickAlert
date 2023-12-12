package com.msd.project_group_7.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.msd.project_group_7.R;
import com.msd.project_group_7.activity.ViewTaskDetailActivity;
import com.msd.project_group_7.model.TaskModel;
import com.msd.project_group_7.receiver.AlarmReceiver;

public class Utils {

    public static int getImageFromCategory(Context activity, String category) {

        if(category.equals(activity.getString(R.string.work))){
            return R.drawable.work;
        } else if(category.equals(activity.getString(R.string.education))){
            return R.drawable.education;
        } else if(category.equals(activity.getString(R.string.health))){
            return R.drawable.health;
        } else if(category.equals(activity.getString(R.string.shopping))){
            return R.drawable.shopping;
        } else if(category.equals(activity.getString(R.string.social))){
            return R.drawable.social;
        } else if(category.equals(activity.getString(R.string.travel))){
            return R.drawable.travel;
        } else if(category.equals(activity.getString(R.string.entertainment))){
            return R.drawable.entertainment;
        } else if(category.equals(activity.getString(R.string.family))){
            return R.drawable.family;
        } else {
            return R.drawable.hobbies;
        }
    }

    public static void setAlarm(Activity activity, TaskModel task) {
        Log.e("AA_S", task.toString());
        Log.e("AA_S", task.getTaskId() + " --");
        Intent intent = new Intent(activity, AlarmReceiver.class);
        intent.putExtra("TASK_ID", task.getTaskId());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, task.getTaskId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, task.getTaskMilliseconds(),  pendingIntent);
        Log.e("AA_S", "Set Alarm");
    }

    public static void cancelAlarm(Activity activity, int taskId) {
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity,taskId,new Intent(activity, AlarmReceiver.class),0);
        alarmManager.cancel(pendingIntent);
    }

    public static Bitmap getBitmapFromDrawableResource(Context context, int drawableResourceId) {
        Drawable drawable = context.getResources().getDrawable(drawableResourceId);
        return getBitmapFromDrawable(drawable);
    }

    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        // Get the intrinsic width and height of the drawable
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();

        // Create a Bitmap with the same dimensions as the drawable
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Create a Canvas and draw the drawable onto the Bitmap
        android.graphics.Canvas canvas = new android.graphics.Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);

        return bitmap;
    }

    public static void createNotification(Context context, TaskModel task, DBHelper db) {

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
