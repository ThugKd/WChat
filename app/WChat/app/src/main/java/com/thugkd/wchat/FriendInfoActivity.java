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
import com.thugkd.entity.User;
import com.thugkd.fragment.MineFragment;
import com.thugkd.model.ClientConServerThread;
import com.thugkd.model.SendMessage;
import com.thugkd.view.CircleImageView;

public class FriendInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CircleImageView civAvator;
    private TextView tvtitle;
    private TextView tvNick;
    private TextView tvPhone;
    private EditText etRemark;
    private Button btnSendMsg;
    private Button btnDeleteFriend;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);

        //take out actionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        user = (User) getIntent().getSerializableExtra("user");

        findView();
        initView();

        onClickSendMsg();
        onClickDeleteFriend();
    }

    private void findView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        civAvator = (CircleImageView) findViewById(R.id.mine_avator);
        tvtitle = (TextView) findViewById(R.id.titlebar_title);
        tvNick = (TextView) findViewById(R.id.nick_name);
        tvPhone = (TextView) findViewById(R.id.phoneNum);
        etRemark = (EditText) findViewById(R.id.nick_text);
        btnSendMsg = (Button) findViewById(R.id.send_msg);
        btnDeleteFriend = (Button) findViewById(R.id.delete_friend);
    }

    private void initView() {
        tvtitle.setText(user.getNick());
        tvNick.setText(user.getNick());
        tvPhone.setText(user.getPhone());
        etRemark.setText(user.getNick());
        if (user.getPhone().length() == 11) {
            btnDeleteFriend.setText("删除好友");
        } else {
            btnDeleteFriend.setText("退出群组");
        }

        if (user.getPhone().equals(MineFragment.me.getPhone())){
            btnDeleteFriend.setVisibility(View.GONE);
            btnSendMsg.setVisibility(View.GONE);
            civAvator.setImageResource(R.drawable.ai);
        }
    }

    private void onClickSendMsg() {
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void onClickDeleteFriend() {
        btnDeleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = user.getPhone();
                String type = "";
                if (account.length() == 11) {
                    type = MessageType.DEL_BUDDY;
                } else {
                    type = MessageType.DEL_GROUP;
                }
                SendMessage.sendDealFriend(MineFragment.me.getPhone(), account, type);

                if (ClientConServerThread.isFff) {
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
        });
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (user.getPhone().length() == 11) {
                        Toast.makeText(FriendInfoActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(FriendInfoActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(FriendInfoActivity.this, MainActivity.class));
                    break;
                case 2:
                    if (user.getPhone().length() == 11){
                        Toast.makeText(FriendInfoActivity.this, "删除失败", Toast.LENGTH_SHORT).show();

                    }{

                    Toast.makeText(FriendInfoActivity.this, "退出失败", Toast.LENGTH_SHORT).show();
                }
                    break;
            }
        }
    };
}
