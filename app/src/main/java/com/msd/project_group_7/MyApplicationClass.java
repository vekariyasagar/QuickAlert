package com.msd.project_group_7;


import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class MyApplicationClass extends Application {
    public static MyApplicationClass application;
    private static final String TAG = MyApplicationClass.class.getSimpleName();

    public MyApplicationClass() {
        application = this;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static MyApplicationClass getApp() {
        if (application == null) {
            application = new MyApplicationClass();
        }
        return application;
    }

    public static Context getAppContext() {
        if (application == null) {
            application = new MyApplicationClass();
        }
        return application;
    }

}
