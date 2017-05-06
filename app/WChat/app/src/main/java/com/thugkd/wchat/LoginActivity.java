package com.thugkd.wchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thugkd.entity.User;
import com.thugkd.model.WChatClient;


public class LoginActivity extends AppCompatActivity {

    public static String userInfo;

    private EditText etPhone;
    private EditText etPassword;
    private TextView tvForgetPass;
    private Button btnLogin;
    private Button btnRegister;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        //take out actionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        findView();
        initView();

        onClickLogin();
        onClickRegister();
        onClickRetrievePassword();
    }

    private void findView() {
        etPhone = (EditText) findViewById(R.id.account);
        etPassword = (EditText) findViewById(R.id.password);
        tvForgetPass = (TextView) findViewById(R.id.tv_forget_password);
        btnLogin = (Button) findViewById(R.id.login);
        btnRegister = (Button) findViewById(R.id.register);
    }

    public void initView(){
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        String spAccount = sp.getString("account", null);
        String spPass = sp.getString("pass", null);
        if (spAccount != null){
            etPhone.setText(spAccount);
        }
        if (spPass != null){
            etPassword.setText(spPass);
        }
    }

    private void onClickLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString();
                String password = etPassword.getText().toString();

                if (phone.length() == 0) {
                    Toast.makeText(LoginActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else if (password.length() == 0) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!phone.matches("^1[0-9]{10}$")) {
                    Toast.makeText(LoginActivity.this, "手机号格式不对", Toast.LENGTH_SHORT).show();
                }else if(!password.matches("^[a-zA-Z0-9]{6,15}$")){
                    Toast.makeText(LoginActivity.this, "密码需由6到15位数字和字母组成", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {
                        public void run() {
                            boolean b = login(etPhone.getText().toString(), etPassword.getText().toString());
                            Log.e("Login",b + "====");
                            Message msg = new Message();
                            if (b) {
                                msg.what = 1;
                            } else {
                                msg.what = 2;
                            }
                            handler.sendMessage(msg);
                        }
                    }
                    ).start();
                }
            }
        });
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    saveSP();
                    //转到主界面
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    break;
                case 2:
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private boolean login(String phone, String pass) {
        final User user = new User();
        user.setPhone(phone);
        user.setPassword(pass);
        user.setOperation("login");
        boolean b = new WChatClient(LoginActivity.this).sendLoginInfo(user);
        //登陆成功
        if (b) {
            this.finish();
            return true;
        } else {
            return false;
        }
    }


    public void saveSP() {
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("account", etPhone.getText().toString().trim());
        editor.putString("pass", etPassword.getText().toString().trim());
        editor.commit();
    }

    private void onClickRegister() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void onClickRetrievePassword() {
        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RetrievePasswordActivity.class);
                startActivity(intent);
            }
        });
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

}
