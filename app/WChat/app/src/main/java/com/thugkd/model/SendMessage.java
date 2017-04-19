package com.thugkd.model;

import android.util.Log;

import com.thugkd.entity.MessageType;
import com.thugkd.util.MyTime;
import com.thugkd.entity.WChatMessage;
import com.thugkd.fragment.MineFragment;

import java.io.ObjectOutputStream;
import java.net.Socket;


public class SendMessage {
    public static void sendMes(String dfAccount, String content, String type) {
        try {
            String myAccount = MineFragment.me.getPhone();

            Socket socket = ManageClientConServer.getManager().getClientConServerThread(myAccount).getS();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            WChatMessage m = new WChatMessage();
            m.setType(type);
            m.setSender(myAccount);
            m.setReceiver(dfAccount);
            m.setContent(content);
            m.setSendTime(MyTime.geTimeNoS());
            Log.e("SendMess", m.toString());
            oos.writeObject(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        according to type：
        type = MessageType.DEL_BUDDY :删除好友
        type = MessageType.ADD_BUDDY :添加好友
        type = MessageType.DEL_GROUP :退出群
        type = MessageType.ADD_GROUP :添加群
    */
    public static void sendDealFriend(String myAccount, String dfAccount, String type) {

        try {
            Socket socket = ManageClientConServer.getManager().getClientConServerThread(myAccount).getS();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            WChatMessage m = new WChatMessage();
            m.setType(type);
            m.setSender(myAccount);
            m.setReceiver(dfAccount);
            Log.e("Mess", m.getType());
            oos.writeObject(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getFriend() {
        try {
            Socket socket = ManageClientConServer.getManager().getClientConServerThread(MineFragment.me.getPhone()).getS();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            WChatMessage m = new WChatMessage();
            m.setType(MessageType.GET_ONLINE_FRIENDS);
            m.setSender(MineFragment.me.getPhone());
            Log.e("Mess", m.getType());
            oos.writeObject(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logout() {
        Socket socket = null;
        try {
            socket = ManageClientConServer.getManager().getClientConServerThread(MineFragment.me.getPhone()).getS();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            WChatMessage m = new WChatMessage();
            m.setType(MessageType.LOGOUT);
            m.setSender(MineFragment.me.getPhone());
            oos.writeObject(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
