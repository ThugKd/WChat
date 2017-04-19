package com.thugkd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @author: thugkd
 * @date: 25/03/2017 15:00
 */
public class GroupDao {

    public String getGroup(String phone) {
        String g = "";
        try {
            String sql = "select tb_group.gaccount,gnick from tb_group_member,tb_group WHERE tb_group.gaccount=tb_group_member.gaccount AND uphone = " + phone;
            Connection conn = DBUtil.getDBUtil().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                g = rs.getInt("gaccount") + "_" + rs.getString("gnick") + " ";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return g;
    }

    public String getGroupNick(int gaccount) {
        String g = "";
        try {
            String sql = "select * from tb_group where gaccount=" + gaccount;
            Connection conn = DBUtil.getDBUtil().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                g = rs.getString("gnick");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return g;
    }

    public List<String> getGroupMember(String gaccount) {
        List<String> res = new ArrayList<String>();
        try {
            String sql = "select * from tb_group_member where gaccount=" + gaccount;
            Connection conn = DBUtil.getDBUtil().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                res.add(rs.getString("uphone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
