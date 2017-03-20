package com.thugkd.wchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class WelcomeActivity extends AppCompatActivity {

    // time of splash
    private static final int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);

        //take out actionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        new View(WelcomeActivity.this).postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sp = getSharedPreferences("loginState", Context.MODE_PRIVATE);
                boolean isLogin = sp.getBoolean("isLogin",false);

                Log.e("WelcomeActivity","" + isLogin);
                Intent intent = null;

                if(isLogin == true){
                     intent = new Intent(WelcomeActivity.this, MainActivity.class);
                }else {
                     intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                }

                startActivity(intent);
                finish();

            }
        }, SPLASH_TIME);
    }
}
