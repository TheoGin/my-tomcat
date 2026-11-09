package com.example.tomcat.servlet;

import com.example.tomcat.http.MyHttpServletRequest;
import com.example.tomcat.http.MyHttpServletResponse;
import com.example.tomcat.utils.WebUtils;

import javax.servlet.ServletConfig;
import java.io.IOException;
import java.io.OutputStream;

public class MyCalServlet extends MyHttpServlet {
    @Override
    public void init(ServletConfig var1) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doGet(MyHttpServletRequest request, MyHttpServletResponse response) {
        String num1Str = request.getParameter("num1");
        String num2Str = request.getParameter("num2");
        int num1 = WebUtils.parseInt(num1Str, 0);
        int num2 = WebUtils.parseInt(num2Str, 0);
        int result = num1 + num2;
        OutputStream outputStream = response.getOutputStream();
        String resp = MyHttpServletResponse.RESPONSE_LINE_AND_HEADER + "<h1>" + num1 + " + " + num2 + " = " + result + "我的 tomcat"+ "</h1>";

        try {
            outputStream.write(resp.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPost(MyHttpServletRequest request, MyHttpServletResponse response) {
        this.doGet(request, response);
    }
}
