package com.thugkd.dao;

import com.thugkd.entity.User;

import java.sql.*;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @author: thugkd
 * @date: 24/03/2017 16:37
 */
public class UserDao {

    public boolean login(String phone, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            String sql = "select * from tb_user where uphone=? and upassword=?";
            conn = DBUtil.getDBUtil().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, phone);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next() == true) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            DBUtil.getDBUtil().closeConnection(conn);
        }
        return false;
    }

    public boolean register(User u) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            if (getUser(u.getPhone()) == null || getUser(u.getPhone()).length() == 0) {
                String sql = "insert into tb_user(uphone,upassword,unick,uaddtime) values(?,?,?,?)";
                conn = DBUtil.getDBUtil().getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, u.getPhone());
                ps.setString(2, u.getPassword());
                ps.setString(3, "wchat" + u.getPhone().substring(7));//以wchat+手机号后四位作为昵称
                ps.setDate(4, new Date(System.currentTimeMillis()));
                int r = ps.executeUpdate();
                if (r > 0) {
                    //insert default group
                    sql = "insert into tb_group_member  values (1,?);";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, u.getPhone());
                    ps.execute();

                    //insert tb_buddy with admin
                    sql = "INSERT INTO tb_buddy VALUES(?,?,?)";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, u.getPhone());
                    ps.setString(2, "18710896384");
                    ps.setDate(3, new Date(System.currentTimeMillis()));
                    ps.execute();

                    //insert tb_buddy with admin
                    sql = "INSERT INTO tb_buddy VALUES(?,?,?)";
                    ps = conn.prepareStatement(sql);
                    ps.setString(2, u.getPhone());
                    ps.setString(1, "18710896384");
                    ps.setDate(3, new Date(System.currentTimeMillis()));
                    ps.execute();

                    //update group member num
                    sql = "UPDATE tb_group set gcount=gcount+1 WHERE gaccount = 1";
                    ps = conn.prepareStatement(sql);
                    ps.executeUpdate();
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            DBUtil.getDBUtil().closeConnection(conn);
        }
        return false;
    }

    public boolean retrieve(User u) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            if (getUser(u.getPhone()) != null && getUser(u.getPhone()).length() != 0) {
                String sql = "update tb_user set upassword=? where uphone=?";
                conn = DBUtil.getDBUtil().getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, u.getPassword());
                ps.setString(2, u.getPhone());
                int r = ps.executeUpdate();
                if (r > 0) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            DBUtil.getDBUtil().closeConnection(conn);
        }
        return false;
    }

    public boolean addBuddy(String myAccount, String dfAccount) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            if (getUser(dfAccount) != null && getUser(dfAccount).length() != 0) {
                if (!isBuddy(myAccount, dfAccount)) {
                    String sql = "insert into tb_buddy VALUES (?,?,?)";
                    conn = DBUtil.getDBUtil().getConnection();
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, myAccount);
                    ps.setString(2, dfAccount);
                    ps.setDate(3, new Date(System.currentTimeMillis()));
                    int r = ps.executeUpdate();
                    if (r > 0) {
                        sql = "insert into tb_buddy VALUES (?,?,?)";
                        ps = conn.prepareStatement(sql);
                        ps.setString(2, myAccount);
                        ps.setString(1, dfAccount);
                        ps.setDate(3, new Date(System.currentTimeMillis()));
                        ps.executeUpdate();
                        return true;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            DBUtil.getDBUtil().closeConnection(conn);
        }
        return false;
    }

    //判断是否已经是好友
    public boolean isBuddy(String myAccount, String dfAccount) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            String sql = "select * from tb_buddy where uphone=? and bphone=?";
            conn = DBUtil.getDBUtil().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, myAccount);
            ps.setString(2, dfAccount);
            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            DBUtil.getDBUtil().closeConnection(conn);
        }

        return false;
    }

    public boolean delBuddy(String myAccount, String dfAccount) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            String sql = "delete  from tb_buddy where uphone=? and bphone=?";
            conn = DBUtil.getDBUtil().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, myAccount);
            ps.setString(2, dfAccount);
            int r = ps.executeUpdate();
            if (r > 0) {
                ps.setString(1, dfAccount);
                ps.setString(2, myAccount);
                int p = ps.executeUpdate();
                if (p > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            DBUtil.getDBUtil().closeConnection(conn);
        }
        return false;
    }

    public String getBuddy(String account) {
        String res = "";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        try {
            String sql = "select * from tb_buddy where uphone=" + account;
            conn = DBUtil.getDBUtil().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String s = "";
                String sql2 = "select * from tb_user where uphone=" + rs.getString("bphone");
                ps = conn.prepareStatement(sql2);
                rs2 = ps.executeQuery();
                while (rs2.next()) {
                    s = rs2.getString("uphone") + "_" + rs2.getString("unick") + "_"
                            + rs2.getString("uavatar") + "_" + rs2.getInt("uisonline") + " ";
                }
                res += s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs2.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            DBUtil.getDBUtil().closeConnection(conn);
        }
        return res;
    }

    public String getUser(String account) {
        String res = "";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from tb_user where uphone=" + account;
            conn = DBUtil.getDBUtil().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                res = res + rs.getString("uphone") + "_" + rs.getString("unick") + "_"
                        + rs.getString("uavatar") + "_"
                        + rs.getInt("usex") + "_" + rs.getInt("uage");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            DBUtil.getDBUtil().closeConnection(conn);
        }
        return res;
    }

    public boolean changeState(String account, int state) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            String sql = "update tb_user set uisonline=? where uphone=?";
            conn = DBUtil.getDBUtil().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, state);
            ps.setString(2, account);
            int r = ps.executeUpdate();
            if (r > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            DBUtil.getDBUtil().closeConnection(conn);
        }
        return false;
    }

    public boolean addGroup(int gaccount, String uphone) {

        try {
            String sqlQuery = "Select * from tb_group_member where gaccount=? and uphone=?";
            Connection conn = DBUtil.getDBUtil().getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlQuery);
            ps.setInt(1, gaccount);
            ps.setString(2, uphone);
            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next() == true) {
                return false;
            } else {
                String sql = "insert into tb_group_member values(?,?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, gaccount);
                ps.setString(2, uphone);
                int r = ps.executeUpdate();
                if (r > 0) {
                    sql = "UPDATE tb_group set gcount=gcount+1 WHERE gaccount = " + gaccount;
                    ps = conn.prepareStatement(sql);
                    ps.executeUpdate();
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delGroup(int gaccount, String uphone) {
        try {
            String sql = " DELETE FROM tb_group_member WHERE gaccount=? AND uphone=?";
            Connection conn = DBUtil.getDBUtil().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, gaccount);
            ps.setString(2, uphone);
            int r = ps.executeUpdate();
            if (r > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
