package com.msd.project_group_7.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
            + TASK_COLUMN_LATITUDE + " LONG, "
            + TASK_COLUMN_LONGITUDE + " LONG, "
            + TASK_COLUMN_COMPLETED + " BOOLEAN); ";

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

    public boolean addTask(TaskModel task) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TASK_COLUMN_NAME,task.getTaskName());
        cv.put(TASK_COLUMN_CATEGORY,task.getTaskCategory());
        cv.put(TASK_COLUMN_TYPE,task.getTaskType());
        cv.put(TASK_COLUMN_DATE,task.getTaskDate());
        cv.put(TASK_COLUMN_TIME,task.getTaskTime());
        cv.put(TASK_COLUMN_MILLISECONDS,task.getTaskMilliseconds());
        cv.put(TASK_COLUMN_LATITUDE,task.getTaskLatitude());
        cv.put(TASK_COLUMN_LONGITUDE,task.getTaskLongitude());
        cv.put(TASK_COLUMN_COMPLETED,task.isTaskCompleted());
        long result = db.insert(TASK_TABLE_NAME, null,cv);
        return ((result==-1) ? false : true);
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

    public Cursor getTaskById(TaskModel task) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursorObj;
        cursorObj=db.rawQuery("SELECT * FROM " + TASK_TABLE_NAME + " WHERE " + TASK_COLUMN_ID + " = " + task.getTaskId(), null);
        if(cursorObj != null) {
            cursorObj.moveToFirst();
        }
        return cursorObj;
    }

    public Cursor getCompletedTask(boolean flag) {
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
