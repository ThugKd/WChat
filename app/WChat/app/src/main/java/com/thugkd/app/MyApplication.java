package com.thugkd.app;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by thugkd on 16/03/2017.
 *
 * Get RequestQueue
 */

public class MyApplication  extends Application{

    public static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getHttpQueue(){
        return requestQueue;
    }

}
