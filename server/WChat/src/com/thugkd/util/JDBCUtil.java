package com.thugkd.util;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @author: thugkd
 * @date: 15/03/2017 14:17
 */
public class JDBCUtil {

    private static String dburl;
    private static String user;
    private static String password;
    private static String driver;


    static {
        Properties prop = new Properties();
        try {
            InputStream in = JDBCUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
            prop.load(in);
        } catch (IOException e) {
            System.out.println("读取配置文件出错");
        }

        driver = prop.getProperty("driver");
        dburl = prop.getProperty("dburl");
        user = prop.getProperty("user");
        password = prop.getProperty("password");
    }

    /**
     * 建立数据库连接
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(dburl, user, password);
        return conn;
    }

    /**
     * 释放连接
     *
     * @param conn
     */
    private static void freeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 释放statement
     *
     * @param statement
     */
    private static void freeStatement(Statement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 释放resultset
     *
     * @param rs
     */
    private static void freeResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     *
     * @param conn
     * @param statement
     * @param rs
     */
    public static void free(Connection conn, Statement statement, ResultSet rs) {
        if (rs != null) {
            freeResultSet(rs);
        }
        if (statement != null) {
            freeStatement(statement);
        }
        if (conn != null) {
            freeConnection(conn);
        }
    }

}