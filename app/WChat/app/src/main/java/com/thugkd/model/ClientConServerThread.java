/**
 * �ͻ��˺ͷ������˱���ͨ�ŵ��߳�
 * ���ϵض�ȡ����������������
 */
package com.thugkd.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.thugkd.entity.MessageType;
import com.thugkd.entity.WChatMessage;
import com.thugkd.fragment.FriendFragment;
import com.thugkd.wchat.MainActivity;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConServerThread extends Thread {
    private Context context;
    private Socket s;
    public static boolean dealResult = false;
    public static boolean isFff = true;

    public Socket getS() {
        return s;
    }

    public ClientConServerThread(Context context, Socket s) {
        this.context = context;
        this.s = s;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ObjectInputStream ois = null;
                WChatMessage m;
                isFff = true;

                ois = new ObjectInputStream(s.getInputStream());
                m = (WChatMessage) ois.readObject();

                if (m.getType().equals(MessageType.COM_MES)
                        || m.getType().equals(MessageType.GROUP_MES)) {
                    Log.e("MES", m.getSender() + "---" + m.getReceiver() + "==" + m.getContent());
                    Intent intent = new Intent("com.thugkd.wchat.mes");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("mes", m);
                    intent.putExtras(bundle);
                    context.sendBroadcast(intent);
                } else if (m.getType().equals(MessageType.RET_ONLINE_FRIENDS)) {

                    String s[] = m.getContent().trim().split(",");

                    FriendFragment.buddyStr = s[0];
                    FriendFragment.groupStr = s[1];
                } else if (m.getType().equals(MessageType.GET_USER_INFO)) {
                    MainActivity.myInfo = m.getContent();
                } else if (m.getType().equals(MessageType.SUCCESS)) {
                    isFff = false;
                    dealResult = true;
                    Log.e("Add", "success");
                } else if (m.getType().equals(MessageType.FAIL)) {
                    isFff = false;
                    dealResult = false;
                    Log.e("Add", "fail");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
