package com.thugkd.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thugkd.entity.ChatMsgEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thugkd on 06/04/2017.
 */

public class MessageDB {


    private SQLiteDatabase db;

    public MessageDB(Context context) {
        db = context.openOrCreateDatabase("wchat.db",
                Context.MODE_PRIVATE, null);
    }

    public void saveMsg(String id, ChatMsgEntity entity) {
        db.execSQL("CREATE table IF NOT EXISTS report"
                + id
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,sender TEXT, receiver TEXT,date TEXT,message TEXT)");
        db.execSQL(
                "insert into report" + id
                        + " (sender,receiver,date,message) values(?,?,?,?)",
                new Object[]{entity.getSender(), entity.getReceiver(),
                        entity.getSendTime(), entity.getContent()});
    }

    public List<ChatMsgEntity> getMsg(String id) {
        List<ChatMsgEntity> list = new ArrayList<ChatMsgEntity>();
        db.execSQL("CREATE table IF NOT EXISTS report"
                + id
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,sender TEXT, receiver TEXT,date TEXT,message TEXT)");
        Cursor c = db.rawQuery("SELECT * from report" + id + " ORDER BY _id DESC LIMIT 5", null);
        while (c.moveToNext()) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setSender(c.getString(c.getColumnIndex("sender")));
            entity.setReceiver(c.getString(c.getColumnIndex("receiver")));
            entity.setSendTime(c.getString(c.getColumnIndex("date")));
            entity.setContent(c.getString(c.getColumnIndex("message")));
            list.add(entity);
        }
        c.close();
        return list;
    }

    public void close() {
        if (db != null)
            db.close();
    }

}
