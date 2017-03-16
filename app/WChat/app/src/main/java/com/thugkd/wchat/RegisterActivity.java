package com.thugkd.wchat;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etPhoneNunmber;
    private EditText etVerifyCode;
    private Button btnGetVerifyCode;
    private EditText etPassword;
    private EditText etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        findView();
        initToolBar();
    }

    private void initToolBar() {
        toolbar.setTitle("注册");
        toolbar.setTitleTextAppearance(RegisterActivity.this,R.style.toolbar_title);
    }

    private void findView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        etPhoneNunmber = (EditText) findViewById(R.id.et_phoneNumber);
        etVerifyCode = (EditText) findViewById(R.id.et_verify_code);
        btnGetVerifyCode = (Button) findViewById(R.id.btn_get_verify_code);
        etPassword = (EditText) findViewById(R.id.et_password);
        etConfirmPassword = (EditText) findViewById(R.id.et_password2);
    }


}
