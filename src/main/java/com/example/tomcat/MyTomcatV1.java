package com.example.tomcat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyTomcatV1 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("My Tomcat is running... port: 8080");
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String requestLine = null;
            System.out.println("============= 开始读取请求 =============");
            while ((requestLine = bufferedReader.readLine()) != null) {
                if (requestLine.length() == 0) {
                    break;
                }

                System.out.println(requestLine);
            }
            System.out.println("============= 读取请求结束 =============");

            OutputStream outputStream = socket.getOutputStream();
            String responseLineAndHeader = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html; charset=UTF-8\r\n\r\n";

            String response = responseLineAndHeader + "<h1>Hello, World! 世界</h1>";
            System.out.println("响应内容：" + response);
            outputStream.write(response.getBytes("UTF-8"));
            outputStream.flush();

            bufferedReader.close();

            socket.close();
        }
    }
}
