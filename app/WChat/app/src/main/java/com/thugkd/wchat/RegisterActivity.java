package com.thugkd.wchat;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etPhoneNunmber;
    private EditText etVerifyCode;
    private Button btnGetVerifyCode;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;
    private TextView tvTitle;

    private int time = 60;

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
        onClickGetVerifyCode();
        onClickRegister();
    }

    private void findView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        etPhoneNunmber = (EditText) findViewById(R.id.et_phoneNumber);
        etVerifyCode = (EditText) findViewById(R.id.et_verify_code);
        btnGetVerifyCode = (Button) findViewById(R.id.btn_get_verify_code);
        etPassword = (EditText) findViewById(R.id.et_password);
        etConfirmPassword = (EditText) findViewById(R.id.et_password2);
        btnRegister = (Button) findViewById(R.id.btn_register);
        tvTitle = (TextView) findViewById(R.id.titlebar_title);
    }

    private void initToolBar() {
        tvTitle.setText(R.string.register);
        toolbar.setTitleTextAppearance(RegisterActivity.this, R.style.toolbar_title);
    }

    private void onClickRegister() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhoneNunmber.getText().toString();
                String verifyCode = etVerifyCode.getText().toString();
                String password = etPassword.getText().toString();
                String password2 = etConfirmPassword.getText().toString();

                if (verifyCode.length() == 0){
                    Toast.makeText(RegisterActivity.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
                }else if(password.length() ==0 || password2.length() ==0) {
                    Toast.makeText(RegisterActivity.this," 密码不能为空",Toast.LENGTH_SHORT).show();
                }else if(!password.equals(password2)){
                    Toast.makeText(RegisterActivity.this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                }else {

                }
            }
        });

    }

    private void onClickGetVerifyCode() {
        btnGetVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhoneNunmber.getText().toString();
                if (phone.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else if (!phone.matches("^1[0-9]{10}$")) {
                    Toast.makeText(RegisterActivity.this, "手机号格式不对", Toast.LENGTH_SHORT).show();
                } else {
                    btnGetVerifyCode.setBackgroundColor(getResources().getColor(R.color.gray));
                    new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            btnGetVerifyCode.setEnabled(false);
                            btnGetVerifyCode.setText(String.format("%ds后重新获取", millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {
                            btnGetVerifyCode.setEnabled(true);
                            btnGetVerifyCode.setBackgroundColor(getResources().getColor(R.color.green));
                            btnGetVerifyCode.setText("获取验证码");
                        }
                    }.start();

                    //短信验证码
                }
            }
        });
    }

}
