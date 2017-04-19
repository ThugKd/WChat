package com.thugkd.model;

import com.thugkd.dao.GroupDao;
import com.thugkd.dao.UserDao;
import com.thugkd.entity.MessageType;
import com.thugkd.entity.WChatMessage;

import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @author: thugkd
 * @date: 25/03/2017 15:01
 */
public class DoWhatAndSendMes {
    public static UserDao udao = new UserDao();
    public static GroupDao gdao = new GroupDao();
    public static ManageServerConClient manager = ManageServerConClient.getManager();

    public static void sendMes(WChatMessage m) {
        try {

            //取得接收人的通信线程
            ServerConClientThread scc = manager.getClientThread(m.getReceiver());
            System.out.println(m.getSender() + "----" + m.getReceiver() + "===" + m.getContent());

            ObjectOutputStream oos = new ObjectOutputStream(scc.s.getOutputStream());
            //向接收人发送消息
            System.out.println("ddddd");
            oos.writeObject(m);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addBuddy(WChatMessage m) {
        if (udao.getUser(m.getReceiver()) != null && udao.getUser(m.getReceiver()).length() != 0) {
            try {
                if (udao.addBuddy(m.getSender(), m.getReceiver())) {
                    ServerConClientThread scc = manager.getClientThread(m.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(scc.s.getOutputStream());
                    WChatMessage ms = new WChatMessage();
                    ms.setType(MessageType.SUCCESS);
                    oos.writeObject(ms);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendBuddyList(WChatMessage m) {
        try {
            //操作数据库，返回好友列表，顺带群列表
            String res = udao.getBuddy(m.getSender()) + "," + gdao.getGroup(m.getSender());
            //发送好友列表到客户端
            ServerConClientThread scc = manager.getClientThread(m.getSender());
            ObjectOutputStream oos = new ObjectOutputStream(scc.s.getOutputStream());
            WChatMessage ms = new WChatMessage();
            ms.setType(MessageType.RET_ONLINE_FRIENDS);
            ms.setContent(res);
            oos.writeObject(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getUerInfo(WChatMessage m) {
        try {
            ServerConClientThread scc = manager.getClientThread(m.getSender());
            ObjectOutputStream oos = new ObjectOutputStream(scc.s.getOutputStream());
            WChatMessage ms = new WChatMessage();
            UserDao uDao = new UserDao();
            String userInfo = uDao.getUser(m.getReceiver());
            ms.setType(MessageType.GET_USER_INFO);
            ms.setContent(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void delBuddy(WChatMessage m) {
        try {
            if (udao.delBuddy(m.getSender(), m.getReceiver())) {
                ServerConClientThread scc = manager.getClientThread(m.getSender());
                ObjectOutputStream oos = new ObjectOutputStream(scc.s.getOutputStream());
                WChatMessage ms = new WChatMessage();
                ms.setType(MessageType.SUCCESS);
                oos.writeObject(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addGroup(WChatMessage m) {
        try {
            ServerConClientThread scc = manager.getClientThread(m.getSender());
            ObjectOutputStream oos = new ObjectOutputStream(scc.s.getOutputStream());
            WChatMessage ms = new WChatMessage();
            if (gdao.getGroupNick(Integer.parseInt(m.getReceiver())) != null && gdao.getGroupNick(Integer.parseInt(m.getReceiver())).length() != 0) {
                if (udao.addGroup(Integer.parseInt(m.getReceiver()), m.getSender())) {
                    ms.setType(MessageType.SUCCESS);
                } else {
                    ms.setType(MessageType.FAIL);
                }


            } else {
                ms.setType(MessageType.FAIL);
            }
            System.out.println(ms.getType());
            oos.writeObject(ms);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void delGroup(WChatMessage m) {

        if (udao.delGroup(Integer.parseInt(m.getReceiver()), m.getSender())) {
            try {
                ServerConClientThread scc = manager.getClientThread(m.getSender());
                ObjectOutputStream oos = new ObjectOutputStream(scc.s.getOutputStream());
                WChatMessage ms = new WChatMessage();
                ms.setType(MessageType.SUCCESS);
                oos.writeObject(ms);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void getGroupMemberInfo(WChatMessage m) {
        try {
            List<String> listGroupMem = gdao.getGroupMember(m.getReceiver());
            ServerConClientThread scc = manager.getClientThread(m.getSender());
            ObjectOutputStream oos = new ObjectOutputStream(scc.s.getOutputStream());
            WChatMessage ms = new WChatMessage();
            ms.setType(MessageType.GET_GROUP_MEMBER);
            ms.setContent(listGroupMem.toString());
            oos.writeObject(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendGroupMes(WChatMessage m) {
        try {
            List<String> list = gdao.getGroupMember(m.getReceiver());
            for (String raccount : list) {
                //暂只支持向在线的群成员发送消息
                if (manager.isOnline(raccount)) {
                    System.out.println(raccount + ":" + manager.isOnline(raccount));
                    //如果是自己则不发送
                    if (!raccount.equals(m.getSender())) {
                        System.out.println(m.getSender() + "-" + raccount + "已发送");
                        ServerConClientThread scc = ManageServerConClient.getManager().getClientThread(raccount);
                        ObjectOutputStream oos = new ObjectOutputStream(scc.s.getOutputStream());
                        //只需改变接收者和发送者信息
                        WChatMessage mes = new WChatMessage();

                        mes.setGroupAccount(m.getReceiver());
                        mes.setReceiver(raccount);
                        mes.setContent(m.getContent());
                        mes.setSendTime(m.getSendTime());
                        mes.setType(m.getType());
                        mes.setSender(m.getSender());
                        oos.writeObject(mes);
                        oos.flush();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logout(WChatMessage m) {
        UserDao userDao = new UserDao();
        userDao.changeState(m.getSender(), 0);
        ManageServerConClient.getManager().removeClientThread(m.getSender());
        System.out.println("[" + m.getSender() + "]下线了");
    }

}

