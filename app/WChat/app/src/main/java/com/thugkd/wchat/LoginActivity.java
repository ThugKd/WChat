package com.thugkd.wchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private EditText etPhone;
    private EditText etPassword;
    private TextView tvForgetPass;
    private Button btnLogin;
    private Button btnRegister;


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

        login();
        register();
        retrievePassword();
    }

    private void findView() {
        etPhone = (EditText) findViewById(R.id.account);
        etPassword = (EditText) findViewById(R.id.password);
        tvForgetPass = (TextView) findViewById(R.id.tv_forget_password);
        btnLogin = (Button) findViewById(R.id.login);
        btnRegister = (Button) findViewById(R.id.register);
    }

    private void login() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString();
                String password = etPassword.getText().toString();

                if (phone.length() == 0) {
                    Toast.makeText(LoginActivity.this, "手机号不能为空！", Toast.LENGTH_SHORT).show();
                } else if (password.length() == 0) {
                    Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                } else if (phone.matches("^1[0-9]{10}$")) {
                    Toast.makeText(LoginActivity.this, "手机号格式不对！", Toast.LENGTH_SHORT).show();
                } else {
                    String url = "http://10.177.167.64:8080/wchat/login";
                    JSONObject jsonObject = new JSONObject();

                    try {
                        jsonObject.put("phone",phone);
                        jsonObject.put("password",password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(),
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject jsonObject) {

                                }
                            },
                            new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                }
                            });
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void register() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void retrievePassword() {
        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RetrievePasswordActivity.class);
                startActivity(intent);
            }
        });

    }

}
