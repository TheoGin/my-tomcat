package com.example.tomcat.servlet;

import com.example.tomcat.http.MyHttpServletRequest;
import com.example.tomcat.http.MyHttpServletResponse;

public abstract class MyHttpServlet implements MyServlet {
    @Override
    public void service(MyHttpServletRequest request, MyHttpServletResponse response) {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            this.doGet(request, response);
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            this.doPost(request, response);
        }
    }

    public abstract void doGet(MyHttpServletRequest request, MyHttpServletResponse response);

    public abstract void doPost(MyHttpServletRequest request, MyHttpServletResponse response);
}
