package com.example.tomcat.servlet;

import com.example.tomcat.http.MyHttpServletRequest;
import com.example.tomcat.http.MyHttpServletResponse;

import javax.servlet.ServletConfig;

public interface MyServlet {
    void init(ServletConfig var1);

    void service(MyHttpServletRequest request, MyHttpServletResponse response);

    void destroy();
}
