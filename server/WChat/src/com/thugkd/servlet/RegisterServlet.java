package com.thugkd.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.thugkd.data.User;
import com.thugkd.util.JDBCHelper;
import com.thugkd.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @author: thugkd
 * @date: 16/03/2017 10:24
 */
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acceptJson = JSONUtil.acceptJSON(req);
        Gson gson = new Gson();
        User user = gson.fromJson(acceptJson, User.class);
        JsonObject jsonObject = new JsonObject();

        String sql = "select count(*) from tb_user where user_phone = " + user.getPhone() + ";";

        List<Map<String, String>> list = new ArrayList<>();

        if(list.size() == 0) {
            //success
            String insertSql = "insert into tb_user(user_phone,user_pass) values("+user.getPhone() + ","+ user.getPassword() + ");";
            try {
                JDBCHelper.insertWithReturnPrimeKey(insertSql);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            jsonObject.addProperty("status", 0);

        }else {
            //phone has existed
            jsonObject.addProperty("status", 1);
        }

        JSONUtil.returnJSON(resp, jsonObject.toString());
    }
}
