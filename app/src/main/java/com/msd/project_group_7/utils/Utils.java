package com.msd.project_group_7.utils;

import android.app.Activity;

import com.msd.project_group_7.R;

public class Utils {

    public static int getImageFromCategory(Activity activity, String category) {
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

}
