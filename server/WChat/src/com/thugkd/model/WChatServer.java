package com.thugkd.model;

import com.thugkd.dao.GroupDao;
import com.thugkd.dao.UserDao;
import com.thugkd.entity.MessageType;
import com.thugkd.entity.User;
import com.thugkd.entity.WChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @author: thugkd
 * @date: 24/03/2017 16:18
 */
public class WChatServer extends Thread {
    public static final int PORT = 10000;

    @Override
    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("服务器已启动 in " + new Date());

            while (true) {
                Socket socket = serverSocket.accept();
                //接受客户端发来的信息
                ois = new ObjectInputStream(socket.getInputStream());
                User user = (User) ois.readObject();
                WChatMessage message = new WChatMessage();
                oos = new ObjectOutputStream(socket.getOutputStream());
                if (user.getOperation().equals("login")) { //登录
                    System.out.println("login++");
                    String phone = user.getPhone();
                    UserDao userDao = new UserDao();
                    GroupDao groupDao = new GroupDao();
                    boolean islogin = userDao.login(phone, user.getPassword());//连接数据库验证用户
                    if (islogin) {
                        System.out.println("[" + phone + "]上线了！");
                        //更改数据库用户状态
                        userDao.changeState(phone, 1);
                        //得到个人信息
                        String userInfo = userDao.getUser(phone);
                        String res = userDao.getBuddy(phone) + "," + groupDao.getGroup(phone);
                        //返回一个成功登陆的信息包，并附带个人信息
                        message.setType(MessageType.SUCCESS);
                        message.setContent(userInfo + "&" + res);
                        oos.writeObject(message);
                        ServerConClientThread cct = new ServerConClientThread(socket);//单开一个线程，让该线程与该客户端保持连
                        cct.start();
                        ManageServerConClient.getManager().addClientThread(phone,cct);

                    } else {
                        message.setType(MessageType.FAIL);
                        oos.writeObject(message);
                    }
                } else if (user.getOperation().equals("register")) {//注册
                    UserDao udao = new UserDao();
                    if (udao.register(user)) {
                        //返回一个注册成功的信息包
                        message.setType(MessageType.SUCCESS);
                    } else {
                        message.setType(MessageType.REGISTER_USER_EXIST);
                    }
                    oos.writeObject(message);
                } else if (user.getOperation().equals("retrieve")) {//找回密码
                    UserDao udao = new UserDao();
                    if (udao.retrieve(user)) {
                        message.setType(MessageType.SUCCESS);
                        oos.writeObject(message);
                    } else {
                        message.setType(MessageType.FAIL);
                        oos.writeObject(message);
                    }
                } else if (user.getOperation().equals("line")) {
                    message.setType(MessageType.SUCCESS);
                    oos.writeObject(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}

