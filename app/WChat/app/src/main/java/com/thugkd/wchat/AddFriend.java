package com.thugkd.wchat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thugkd.entity.MessageType;
import com.thugkd.fragment.MineFragment;
import com.thugkd.model.ClientConServerThread;
import com.thugkd.model.SendMessage;

public class AddFriend extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvTitle;
    private EditText etAccount;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        //take out actionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        findView();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String account = etAccount.getText().toString();
                if (account.length() > 11 || account.length() == 0) {
                    Toast.makeText(AddFriend.this, "账号格式不对", Toast.LENGTH_SHORT).show();
                } else if(MineFragment.me.getPhone().equals(account)) {
                        Toast.makeText(AddFriend.this,"不能添加自己为好友",Toast.LENGTH_SHORT).show();
                }else {
                    String delType = "";
                    if (account.length() == 11) {
                        delType = MessageType.ADD_BUDDY;
                    } else {
                        delType = MessageType.ADD_GROUP;
                    }

                    SendMessage.sendDealFriend(MineFragment.me.getPhone(), account, delType);
                    if (ClientConServerThread.isFff){
                        try {
                            Thread.currentThread().sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    Message msg = new Message();
                    if (ClientConServerThread.dealResult) {
                        msg.what = 1;
                        Log.e("addFriend", MineFragment.me.getPhone() + "-----2--");
                        SendMessage.getFriend();
                    } else {
                        msg.what = 2;
                        Log.e("addFriend", MineFragment.me.getPhone() + "------3--");

                    }
                    handler.sendMessage(msg);
                }
            }


        });
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(AddFriend.this, "添加成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddFriend.this, MainActivity.class));
                    break;
                case 2:
                    Toast.makeText(AddFriend.this, "添加失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void findView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.titlebar_title);
        etAccount = (EditText) findViewById(R.id.account);
        btnAdd = (Button) findViewById(R.id.add_btn);
        tvTitle.setText(R.string.add_friend);
    }
}
