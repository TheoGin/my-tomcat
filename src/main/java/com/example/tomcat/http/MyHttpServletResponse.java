package com.example.tomcat.http;

import java.io.OutputStream;

public class MyHttpServletResponse {
    // 给输出流，默认响应头
    private OutputStream outputStream;
    public static final String RESPONSE_LINE_AND_HEADER = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/html; charset=UTF-8\r\n\r\n";

    public MyHttpServletResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }
}
