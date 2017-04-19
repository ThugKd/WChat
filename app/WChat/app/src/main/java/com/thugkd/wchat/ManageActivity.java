package com.thugkd.wchat;

import android.app.Activity;

import java.util.HashMap;

/**
 * Created by thugkd on 27/03/2017.
 */


public class ManageActivity {
    private static HashMap allActiviy=new HashMap<String,Activity>();

    public static void addActiviy(String name,Activity activity){
        allActiviy.put(name, activity);
    }

    public static Activity getActivity(String name){
        return (Activity)allActiviy.get(name);
    }
}