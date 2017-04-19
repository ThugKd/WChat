package com.thugkd.model;

import com.thugkd.entity.MessageType;
import com.thugkd.entity.WChatMessage;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @author: thugkd
 * @date: 24/03/2017 16:50
 */
public class ServerConClientThread extends Thread {
    Socket s;

    public ServerConClientThread(Socket s) {
        this.s = s;
    }

    public Socket getS(){
        return s;
    }

    public void run() {
        try {
            while (true) {
                ObjectInputStream ois = null;
                WChatMessage m = null;
                ois = new ObjectInputStream(s.getInputStream());
                m = (WChatMessage) ois.readObject();
                System.out.println(m.getType() + "-------" + m.getSender() + "===" + m.getReceiver());
                //对从客户端取得的消息进行类型判断，做相应的处理
                if (m.getType().equals(MessageType.COM_MES)) {//普通消息包
                    DoWhatAndSendMes.sendMes(m);
                    System.out.println(2222);
                } else if (m.getType().equals(MessageType.GROUP_MES)) { //群消息
                    DoWhatAndSendMes.sendGroupMes(m);
                    System.out.println(3333);
                } else if (m.getType().equals(MessageType.GET_ONLINE_FRIENDS)) {//请求好友列表
                    DoWhatAndSendMes.sendBuddyList(m);
                } else if (m.getType().equals(MessageType.DEL_BUDDY)) { //删除好友
                    DoWhatAndSendMes.delBuddy(m);
                } else if (m.getType().equals(MessageType.ADD_BUDDY)) {//添加好友
                    DoWhatAndSendMes.addBuddy(m);
                } else if (m.getType().equals(MessageType.GET_USER_INFO)) {//获取用户信息
                    DoWhatAndSendMes.getUerInfo(m);
                } else if (m.getType().equals(MessageType.GET_GROUP_MEMBER)) {//获取群成员
                    DoWhatAndSendMes.getGroupMemberInfo(m);
                } else if (m.getType().equals(MessageType.ADD_GROUP)) {//添加群
                    System.out.println(MessageType.ADD_GROUP + "444444");
                    DoWhatAndSendMes.addGroup(m);
                } else if (m.getType().equals(MessageType.DEL_GROUP)) {//退出群
                    DoWhatAndSendMes.delGroup(m);
                } else if (m.getType().equals(MessageType.LOGOUT)){
                    DoWhatAndSendMes.logout(m);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
