package com.thugkd.util;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @author: thugkd
 * @date: 16/03/2017 15:02
 */
public class JSONUtil {

    public static String acceptJSON(HttpServletRequest request) {

        String acceptjson = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer("");
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            br.close();
            acceptjson = sb.toString();
            System.out.print(acceptjson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return acceptjson;
    }

    public static void returnJSON(HttpServletResponse response,String jsonStr){

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }
}

