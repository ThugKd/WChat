package com.thugkd.model;

import android.content.Context;
import android.util.Log;

import com.thugkd.entity.MessageType;
import com.thugkd.entity.User;
import com.thugkd.entity.WChatMessage;
import com.thugkd.fragment.FriendFragment;
import com.thugkd.wchat.MainActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class WChatClient {
    public static final String IP = "10.177.129.34";
    public static final int PORT = 12345;

    private Context context;
    public Socket s;

    public WChatClient(Context context) {
        this.context = context;
    }

    public boolean sendLoginInfo(Object obj) {
        boolean b = false;

        try {
            s = new Socket();
            try {
                s.connect(new InetSocketAddress(IP, PORT), 2000);
            } catch (SocketTimeoutException e) {
                return false;
            }
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            Log.e("WChat", oos.toString());
            oos.writeObject(obj);
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            WChatMessage ms = (WChatMessage) ois.readObject();
            if (ms.getType().equals(MessageType.SUCCESS)) {

                String[] returnStr = ms.getContent().split("&");
                MainActivity.myInfo = returnStr[0];

                String[] buddyRet = returnStr[1].split(",");
                FriendFragment.buddyStr = buddyRet[0];
                FriendFragment.groupStr = buddyRet[1];

                ClientConServerThread ccst = new ClientConServerThread(context, s);

                ManageClientConServer.getManager().addClientConServerThread(((User) obj).getPhone(), ccst);
                ccst.start();
                b = true;
            } else if (ms.getType().equals(MessageType.FAIL)) {
                b = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }

    public boolean sendRegisterInfo(Object obj) {
        boolean b = false;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            s = new Socket();
            try {
                s.connect(new InetSocketAddress(IP, PORT), 2000);
            } catch (SocketTimeoutException e) {
                return false;
            }
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(obj);
            ois = new ObjectInputStream(s.getInputStream());
            WChatMessage ms = (WChatMessage) ois.readObject();
            if (ms.getType().equals(MessageType.SUCCESS)) {
                b = true;
            } else if (ms.getType().equals(MessageType.FAIL)) {
                b = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }

    public boolean sendRetrievePassInfo(Object obj) {
        boolean b = false;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            s = new Socket();
            try {
                s.connect(new InetSocketAddress(IP, PORT), 2000);
            } catch (SocketTimeoutException e) {
                return false;
            }
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(obj);
            ois = new ObjectInputStream(s.getInputStream());
            WChatMessage ms = (WChatMessage) ois.readObject();
            if (ms.getType().equals(MessageType.SUCCESS)) {
                b = true;
            } else if (ms.getType().equals(MessageType.FAIL)) {
                b = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }

    private void closeIO(ObjectOutputStream oos, ObjectInputStream ois) {
        if (ois != null) {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (oos != null) {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
