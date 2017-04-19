package com.thugkd.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @author: thugkd
 * @date: 24/03/2017 16:38
 */
public class DBUtil {
    private static DBUtil dbutil;
    private DBUtil(){

    }

    public synchronized static DBUtil getDBUtil(){
        if(dbutil==null){
            dbutil=new DBUtil();
        }

        return dbutil;
    }

    public Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/wchat?characterEncoding=utf8&useSSL=false",
                    "root","wang");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void closeConnection(Connection con){
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
