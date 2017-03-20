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
 * @date: 15/03/2017 16:40
 */
public class LoginServlet extends HttpServlet {

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

        String sql = "select user_pass from tb_user where user_phone = " + user.getPhone() + ";";

        List<Map<String, String>> list = new ArrayList<>();
        if (list.size() == 1) {
            if (list.get(1).containsValue(user.getPassword())) {
                //success

                jsonObject.addProperty("status", 0);

                String updateStateSql = "update tb_user set user_state = 1 where user_phone = " + user.getPhone() + ";";
                try {
                    JDBCHelper.update(updateStateSql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                //pass error
                jsonObject.addProperty("status", 1);
            }
        } else if (list.size() == 0) {
            //phone error
            jsonObject.addProperty("status", 2);
        }

        JSONUtil.returnJSON(resp,jsonObject.toString());
    }
}
