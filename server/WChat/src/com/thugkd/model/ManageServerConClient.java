package com.thugkd.model;

import java.util.*;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @author: thugkd
 * @date: 24/03/2017 16:50
 */
public class ManageServerConClient {

    private volatile static ManageServerConClient manager = new ManageServerConClient();
    public HashMap<String,ServerConClientThread> hm = new HashMap<>();

    private ManageServerConClient() {

    }

    public static ManageServerConClient getManager() {
        if (manager == null) {
            synchronized (ManageServerConClient.class) {
                if (manager == null) {
                    manager = new ManageServerConClient();
                }
            }
        }
        return manager;
    }

    //添加一个客户端通信线程
    public void addClientThread(String account, ServerConClientThread cc) {
        hm.put(account, cc);
    }


    public void removeClientThread(String account){
        hm.remove(account);
    }

    //删除离线的客户端通信线程
    public void delClientThread(ServerConClientThread cc) {

        Iterator iter = hm.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            ServerConClientThread value = (ServerConClientThread) entry.getValue();
            if (cc == value) {
                hm.remove(key);
                break;
            }
        }
    }

    //得到一个客户端通信线程
    public ServerConClientThread getClientThread(String i) {
        return (ServerConClientThread) hm.get(i);
    }

    //返回当前在线人的情况
    public List getAllOnLineUserid() {
        List list = new ArrayList();
        //使用迭代器完成
        Iterator it = hm.keySet().iterator();
        while (it.hasNext()) {
            list.add((int) it.next());
        }
        return list;
    }

    public boolean isOnline(String phone) {
        List list = new ArrayList();
        //使用迭代器完成
        Iterator it = hm.keySet().iterator();
        while (it.hasNext()) {
            String account = (String) it.next();
            if (account.equals(phone)) {
                return true;
            }
        }
        return false;
    }
}
