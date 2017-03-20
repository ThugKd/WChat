package com.thugkd.wchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import com.thugkd.fragment.ChatFragment;
import com.thugkd.fragment.FriendFragment;
import com.thugkd.fragment.MineFragment;

public class MainActivity extends FragmentActivity {

    protected static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private TextView tvTitle;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        init();
    }

    public void init(){
        getSupportFragmentManager().beginTransaction().replace(R.id.content, new ChatFragment()).commit();
        tvTitle.setText("会话");
    }
    //按下返回键  直接返回桌面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void findView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle  = (TextView) findViewById(R.id.titlebar_title);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new ChatFragment();
                    tvTitle.setText("会话");
                    break;
                case R.id.navigation_dashboard:
                    fragment = new FriendFragment();
                    tvTitle.setText("好友");
                    break;
                case R.id.navigation_notifications:
                    fragment = new MineFragment();
                    tvTitle.setText("我的");
                    break;
                default:
                    break;
            }

            if(fragment != null){
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content,fragment);
                fragmentTransaction.commit();
            }

            return true;
        }
    };

}



