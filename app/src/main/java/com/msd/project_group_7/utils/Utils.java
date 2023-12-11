package com.msd.project_group_7.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.msd.project_group_7.R;
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

}
