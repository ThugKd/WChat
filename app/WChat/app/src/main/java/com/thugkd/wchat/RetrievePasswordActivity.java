package com.thugkd.wchat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thugkd.entity.User;
import com.thugkd.model.WChatClient;

public class RetrievePasswordActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etPhoneNunmber;
    private EditText etVerifyCode;
    private Button btnGetVerifyCode;
    private Button btnConfirm;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        findView();
        initToolBar();

        onClickGetVerifyCode();
        onClickConfirm();
    }

    private void initToolBar() {
        tvTitle.setText(R.string.retrieve_pass);
        toolbar.setTitleTextAppearance(RetrievePasswordActivity.this, R.style.toolbar_title);
    }

    private void findView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        etPhoneNunmber = (EditText) findViewById(R.id.et_phoneNumber);
        etVerifyCode = (EditText) findViewById(R.id.et_verify_code);
        btnGetVerifyCode = (Button) findViewById(R.id.btn_get_verify_code);
        etPassword = (EditText) findViewById(R.id.et_password);
        etConfirmPassword = (EditText) findViewById(R.id.et_password2);
        tvTitle = (TextView) findViewById(R.id.titlebar_title);
        btnConfirm = (Button) findViewById(R.id.btn_next);
    }


    private void onClickConfirm() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = etPhoneNunmber.getText().toString();
                String verifyCode = etVerifyCode.getText().toString();
                final String password = etPassword.getText().toString();
                String password2 = etConfirmPassword.getText().toString();

                if (verifyCode.length() == 0) {
                    Toast.makeText(RetrievePasswordActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                } else if (password.length() == 0 || password2.length() == 0) {
                    Toast.makeText(RetrievePasswordActivity.this, " 密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!password.matches("^[a-zA-Z0-9]{6,15}$")) {
                    Toast.makeText(RetrievePasswordActivity.this, "密码需由6到15位数字和字母组成", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(password2)) {
                    Toast.makeText(RetrievePasswordActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                } else {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = new User();
                            user.setPhone(phone);
                            user.setPassword(password);
                            user.setOperation("retrieve");
                            boolean b = new WChatClient(RetrievePasswordActivity.this).sendRetrievePassInfo(user);

                            Message msg = new Message();
                            if (b) {
                                msg.what = 1;
                            } else {
                                msg.what = 2;
                            }

                            handler.sendMessage(msg);
                        }
                    }).start();
                }
            }
        });

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(RetrievePasswordActivity.this, "密码找回成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RetrievePasswordActivity.this, LoginActivity.class));
                    break;
                case 2:
                    Toast.makeText(RetrievePasswordActivity.this, "密码找回失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void onClickGetVerifyCode() {
        btnGetVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhoneNunmber.getText().toString();
                if (phone.length() == 0) {
                    Toast.makeText(RetrievePasswordActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else if (!phone.matches("^1[0-9]{10}$")) {
                    Toast.makeText(RetrievePasswordActivity.this, "手机号格式不对", Toast.LENGTH_SHORT).show();
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
                }
            }
        });
    }
}
