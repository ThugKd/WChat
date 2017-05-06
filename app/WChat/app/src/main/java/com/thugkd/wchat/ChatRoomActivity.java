package com.thugkd.wchat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.thugkd.adapter.ChatMsgViewAdapter;
import com.thugkd.app.MyApplication;
import com.thugkd.db.MessageDB;
import com.thugkd.entity.ChatMsgEntity;
import com.thugkd.entity.MessageType;
import com.thugkd.entity.RecentChatEntity;
import com.thugkd.entity.User;
import com.thugkd.entity.WChatMessage;
import com.thugkd.model.SendMessage;
import com.thugkd.util.MyTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static com.thugkd.fragment.ChatFragment.RecentChatList;
import static com.thugkd.fragment.ChatFragment.adapter;
import static com.thugkd.fragment.MineFragment.me;
import static com.thugkd.wchat.R.id.chat_bottom_edit;
import static com.thugkd.wchat.R.id.titlebar_title;

public class ChatRoomActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvTitle;
    private EditText etMsg;
    private Button btnSend;
    private ListView mListView;
    private ChatMsgViewAdapter mAdapter;
    private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
    private User user;
    private MessageDB messageDB;
    private MyApplication application;
    private MessageBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        //take out actionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        application = (MyApplication) getApplicationContext();
        findView();
        initView();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.thugkd.wchat.mes");
        broadcastReceiver = new MessageBroadcastReceiver();
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void findView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(titlebar_title);
        etMsg = (EditText) findViewById(chat_bottom_edit);
        btnSend = (Button) findViewById(R.id.chat_bottom_send);
        mListView = (ListView) findViewById(R.id.chat_content_list);
    }

    private void initView() {

        toolbar.inflateMenu(R.menu.info_navigation);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        mAdapter = new ChatMsgViewAdapter(ChatRoomActivity.this, mDataArrays);
        mListView.setAdapter(mAdapter);

        user = (User) getIntent().getSerializableExtra("user");
        tvTitle.setText(user.getNick());
        messageDB = new MessageDB(this);
        initData();

        btnSend.setOnClickListener(chatSendOnclickListener);
    }

    public void initData() {
        List<ChatMsgEntity> list = null;
        list = messageDB.getMsg(user.getPhone());
        if (list.size() > 0) {
            for (ChatMsgEntity entity : list) {
                mDataArrays.add(entity);
            }
            Collections.reverse(mDataArrays);
        }
        mAdapter.notifyDataSetChanged();
        mListView.setSelection(mAdapter.getCount() - 1);
    }

    //查看用户信息按钮
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.friend_info:
                    Intent intent = new Intent(ChatRoomActivity.this, FriendInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private View.OnClickListener chatSendOnclickListener = new View.OnClickListener() {

        public void onClick(View v) {
            // TODO Auto-generated method stub
            String contString = etMsg.getText().toString();
            if (contString.trim().length() == 0) {
                Toast.makeText(ChatRoomActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
            } else {
                ChatMsgEntity entity = new ChatMsgEntity();
                entity.setSender(me.getPhone());
                entity.setReceiver(user.getPhone());
                entity.setSendTime(MyTime.geTimeNoS());
                //entity.setMsgType(false);
                //entity.setName(user.getNick());
                entity.setContent(contString);
                mDataArrays.add(entity);
                mAdapter.notifyDataSetChanged();
                mListView.setSelection(mListView.getCount() - 1);

                messageDB.saveMsg(user.getPhone(), entity);
                String type = "";
                if (user.getPhone().length() < 11) {
                    type = MessageType.GROUP_MES;
                } else {
                    type = MessageType.COM_MES;
                }
                SendMessage.sendMes(user.getPhone(), contString, type);
                etMsg.setText("");
            }

            //下面是添加到RecentListView
            RecentChatEntity entity1 = new RecentChatEntity(user.getPhone(),
                    user.getAvatar(), 0, user.getNick(), getDate(),
                    contString);
            RecentChatEntity entity3;
            for (int i =0 ;i < RecentChatList.size();i++){
                entity3 = RecentChatList.get(i);
                if (entity3 != null){
                    if (entity3.getName().equals(entity1.getName())){
                        RecentChatList.remove(entity3);
                        break;
                    }
                }
            }

            RecentChatList.addFirst(entity1);

            adapter.notifyDataSetChanged();
        }

    };

    class MessageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            WChatMessage mes = (WChatMessage) intent.getSerializableExtra("mes");
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setSender(mes.getSender());
            entity.setReceiver(mes.getReceiver());
            entity.setContent(mes.getContent());
            entity.setSendTime(mes.getSendTime());
            mDataArrays.add(entity);
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(mListView.getCount() - 1);
        }
    }

    private String getDate() {
        Calendar c = Calendar.getInstance();

        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(c.get(Calendar.HOUR));
        String mins = String.valueOf(c.get(Calendar.MINUTE));

        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(month + "-" + day + " " + hour + ":" + mins);
        return sbBuffer.toString();
    }

    @Override
    public void finish() {
        unregisterReceiver(broadcastReceiver);
        super.finish();
    }
}

