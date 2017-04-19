package com.thugkd.entity;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @author: thugkd
 * @date: 24/03/2017 16:19
 */
public class MessageType {
    public static final String SUCCESS = "1";//表明是否成功
    public static final String FAIL = "2";//表明失败
    public static final String COM_MES = "3";//普通信息包
    public static final String GET_ONLINE_FRIENDS = "4";//要求在线好友的包
    public static final String RET_ONLINE_FRIENDS = "5";//返回在线好友的包
    public static final String LOGIN = "7";//请求验证登陆
    public static final String ADD_BUDDY = "8";//添加好友
    public static final String DEL_BUDDY = "9";//删除好友
    public static final String GROUP_MES = "10";//群消息
    public static final String FAIL_NO_USER = "11"; //登录失败，用户不存在
    public static final String REGISTER_USER_EXIST = "12"; //注册失败，用户已存在
    public static final String GET_USER_INFO = "13"; //获取用户信息
    public static final String REGISTER = "14"; //请求注册
    public static final String GET_GROUP_MEMBER = "15";//获取群成员信息
    public static final String ADD_GROUP = "16";////请求添加群
    public static final String DEL_GROUP = "17";////请求退出加群
    public static final String LOGOUT = "18";////请求退出

}
