package com.msd.project_group_7;


import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;
import androidx.multidex.MultiDex;

import com.msd.project_group_7.utils.MyLocationListener;

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

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

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
