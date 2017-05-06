package com.thugkd.app;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.thugkd.adapter.RecentChatAdapter;
import com.thugkd.db.MessageDB;
import com.thugkd.entity.ChatMsgEntity;
import com.thugkd.entity.RecentChatEntity;
import com.thugkd.entity.WChatMessage;
import com.thugkd.fragment.ChatFragment;

import java.util.LinkedList;

import static com.thugkd.fragment.ChatFragment.RecentChatList;
import static com.thugkd.fragment.ChatFragment.adapter;
import static com.thugkd.util.MyDate.getDate;

/**
 * Created by thugkd on 16/03/2017.
 * <p>
 * Get RequestQueue
 */

public class MyApplication extends Application {

    //当一个安卓程序启动时最新执行的是Application类，然后才是入口Activity
    //因为application在安卓中是全局的，且他的生命周期为整个app运行的生命周期
    //所以，可以考虑把一些多个activity都要用到的全局的东西在application中实例化\\

    private int MsgNum = 0, recentMsgNum = 0;
    private RecentChatAdapter mRecentAdapter;
    private LinkedList<RecentChatEntity> mRecentList;
    private MesBroadcastReceiver broadcastReceiver;

    private MessageDB messageDB;

    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        mRecentList = new LinkedList<RecentChatEntity>();
        mRecentAdapter = new RecentChatAdapter(getApplicationContext(), mRecentList);

        messageDB = new MessageDB(getApplicationContext());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.thugkd.wchat.mes");
        broadcastReceiver = new MesBroadcastReceiver();
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public LinkedList<RecentChatEntity> getmRecentList() {
        return mRecentList;
    }

    public void setmRecentList(LinkedList<RecentChatEntity> mRecentList) {
        this.mRecentList = mRecentList;
    }

    public RecentChatAdapter getmRecentAdapter() {
        return mRecentAdapter;
    }

    public void setmRecentAdapter(RecentChatAdapter mRecentAdapter) {
        this.mRecentAdapter = mRecentAdapter;
    }

    public int getRecentNum() {
        return recentMsgNum;
    }

    public void setRecentNum(int recentNum) {
        this.recentMsgNum = recentNum;
    }


    class MesBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            WChatMessage mes = (WChatMessage) intent.getSerializableExtra("mes");
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setSender(mes.getSender());
            entity.setReceiver(mes.getReceiver());
            entity.setContent(mes.getContent());
            entity.setSendTime(mes.getSendTime());

            if (mes.getGroupAccount() != null) {
                messageDB.saveMsg(mes.getGroupAccount(), entity);
            } else {
                messageDB.saveMsg(mes.getSender(), entity);
            }

            RecentChatEntity entity1;
            if (mes.getGroupAccount() != null) {
                entity1 = new RecentChatEntity(mes.getGroupAccount(),
                        null, 1, "wchatGroup", getDate(),
                        entity.getContent());
            } else {
                entity1 = new RecentChatEntity(entity.getSender(),
                        null, 1, "wchat" + entity.getSender().substring(7), getDate(),
                        entity.getContent());
            }

            RecentChatEntity entity3;
            for (int i = 0; i < RecentChatList.size(); i++) {
                entity3 = RecentChatList.get(i);
                if (entity3 != null) {
                    if (entity3.getName().equals(entity1.getName())) {
                        entity1.setCount(entity3.getCount() + 1);
                        RecentChatList.remove(entity3);
                        break;
                    }
                }
            }

            Log.e("count",entity1.getCount()+ "");
            ChatFragment.RecentChatList.addFirst(entity1);

            adapter.notifyDataSetChanged();
        }
    }
}
