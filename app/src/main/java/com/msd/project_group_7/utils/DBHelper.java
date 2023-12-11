package com.msd.project_group_7.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.msd.project_group_7.model.TaskModel;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "QuickAlert.db";
    public static final String TASK_TABLE_NAME = "Task";
    public static final String TASK_COLUMN_ID = "taskId";
    public static final String TASK_COLUMN_NAME = "taskName";
    public static final String TASK_COLUMN_CATEGORY = "taskCategory";
    public static final String TASK_COLUMN_TYPE = "taskType";
    public static final String TASK_COLUMN_DATE = "taskDate";
    public static final String TASK_COLUMN_TIME = "taskTime";
    public static final String TASK_COLUMN_MILLISECONDS = "taskMilliseconds";
    public static final String TASK_COLUMN_ADDRESS = "taskAddress";
    public static final String TASK_COLUMN_LATITUDE = "taskLatitude";
    public static final String TASK_COLUMN_LONGITUDE = "taskLongitude";
    public static final String TASK_COLUMN_COMPLETED = "taskCompleted";

    static final String CREATE_TABLE="create table " + TASK_TABLE_NAME + " (" + TASK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK_COLUMN_NAME + " TEXT, "
            + TASK_COLUMN_CATEGORY + " TEXT, "
            + TASK_COLUMN_TYPE + " TEXT, "
            + TASK_COLUMN_DATE + " TEXT, "
            + TASK_COLUMN_TIME + " TEXT, "
            + TASK_COLUMN_MILLISECONDS + " LONG, "
            + TASK_COLUMN_ADDRESS + " TEXT, "
            + TASK_COLUMN_LATITUDE + " DOUBLE, "
            + TASK_COLUMN_LONGITUDE + " DOUBLE, "
            + TASK_COLUMN_COMPLETED + " INTEGER); ";

    static final String DROP_TABLE="DROP TABLE IF EXISTS " + TASK_TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public long addTask(TaskModel task) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TASK_COLUMN_NAME,task.getTaskName());
        cv.put(TASK_COLUMN_CATEGORY,task.getTaskCategory());
        cv.put(TASK_COLUMN_TYPE,task.getTaskType());
        cv.put(TASK_COLUMN_DATE,task.getTaskDate());
        cv.put(TASK_COLUMN_TIME,task.getTaskTime());
        cv.put(TASK_COLUMN_MILLISECONDS,task.getTaskMilliseconds());
        cv.put(TASK_COLUMN_ADDRESS,task.getTaskAddress());
        cv.put(TASK_COLUMN_LATITUDE,task.getTaskLatitude());
        cv.put(TASK_COLUMN_LONGITUDE,task.getTaskLongitude());
        cv.put(TASK_COLUMN_COMPLETED,task.isTaskCompleted());
        return db.insert(TASK_TABLE_NAME, null,cv);
    }

    public boolean updateTaskById(TaskModel taskModel) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASK_COLUMN_NAME, taskModel.getTaskName());
        values.put(TASK_COLUMN_CATEGORY, taskModel.getTaskCategory());
        values.put(TASK_COLUMN_TYPE, taskModel.getTaskType());
        values.put(TASK_COLUMN_DATE, taskModel.getTaskDate());
        values.put(TASK_COLUMN_TIME, taskModel.getTaskTime());
        values.put(TASK_COLUMN_MILLISECONDS, taskModel.getTaskMilliseconds());
        values.put(TASK_COLUMN_ADDRESS, taskModel.getTaskAddress());
        values.put(TASK_COLUMN_LATITUDE, taskModel.getTaskLatitude());
        values.put(TASK_COLUMN_LONGITUDE, taskModel.getTaskLongitude());
        values.put(TASK_COLUMN_COMPLETED, taskModel.isTaskCompleted());

        String selection = TASK_COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(taskModel.getTaskId())};

        long result = db.update(TASK_TABLE_NAME, values, selection, selectionArgs);
        db.close();
        return ((result==-1) ? false : true);
    }

    public void setTaskCompletedById(TaskModel taskModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_COLUMN_COMPLETED, 1);
        String selection = TASK_COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(taskModel.getTaskId())};
        db.update(TASK_TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }

    public Cursor getAllTask() {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursorObj;
        cursorObj=db.rawQuery("SELECT * FROM " + TASK_TABLE_NAME, null);
        if(cursorObj != null) {
            cursorObj.moveToFirst();
        }
        return cursorObj;
    }

    public TaskModel getTaskById(int taskId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define columns to retrieve
        String[] projection = {
                TASK_COLUMN_ID,
                TASK_COLUMN_NAME,
                TASK_COLUMN_CATEGORY,
                TASK_COLUMN_TYPE,
                TASK_COLUMN_DATE,
                TASK_COLUMN_TIME,
                TASK_COLUMN_MILLISECONDS,
                TASK_COLUMN_ADDRESS,
                TASK_COLUMN_LATITUDE,
                TASK_COLUMN_LONGITUDE,
                TASK_COLUMN_COMPLETED
        };

        // Define selection criteria
        String selection = TASK_COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(taskId)};

        Log.e("AA_S", taskId+"--");

        // Query the database
        Cursor cursor = db.query(
                TASK_TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        TaskModel task = null;

        if (cursor != null && cursor.moveToFirst()) {
            // Extract data from the cursor and create a Task object
            task = new TaskModel();
            task.setTaskId(cursor.getInt(cursor.getColumnIndexOrThrow(TASK_COLUMN_ID)));
            task.setTaskName(cursor.getString(cursor.getColumnIndexOrThrow(TASK_COLUMN_NAME)));
            task.setTaskCategory(cursor.getString(cursor.getColumnIndexOrThrow(TASK_COLUMN_CATEGORY)));
            task.setTaskType(cursor.getString(cursor.getColumnIndexOrThrow(TASK_COLUMN_TYPE)));
            task.setTaskDate(cursor.getString(cursor.getColumnIndexOrThrow(TASK_COLUMN_DATE)));
            task.setTaskTime(cursor.getString(cursor.getColumnIndexOrThrow(TASK_COLUMN_TIME)));
            task.setTaskMilliseconds(cursor.getLong(cursor.getColumnIndexOrThrow(TASK_COLUMN_MILLISECONDS)));
            task.setTaskAddress(cursor.getString(cursor.getColumnIndexOrThrow(TASK_COLUMN_ADDRESS)));
            task.setTaskLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(TASK_COLUMN_LATITUDE)));
            task.setTaskLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(TASK_COLUMN_LONGITUDE)));
            task.setTaskCompleted(cursor.getInt(cursor.getColumnIndexOrThrow(TASK_COLUMN_COMPLETED)));

            // Close the cursor
            cursor.close();
        }

        // Close the database
        db.close();

        return task;
    }

    // 0 - not completed
    // 1 - completed
    public Cursor getCompletedTask(int flag) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursorObj;
        cursorObj=db.rawQuery("SELECT * FROM " + TASK_TABLE_NAME + " WHERE " + TASK_COLUMN_COMPLETED + " = " + flag, null);
        if(cursorObj != null) {
            cursorObj.moveToFirst();
        }
        return cursorObj;
    }

    public boolean deleteTaskById(TaskModel task) {
        SQLiteDatabase db=this.getWritableDatabase();
        long result = db.delete(TASK_TABLE_NAME, TASK_COLUMN_ID+"=?", new String[] {Integer.toString(task.getTaskId())});
        return ((result==-1) ? false : true);
    }

}
