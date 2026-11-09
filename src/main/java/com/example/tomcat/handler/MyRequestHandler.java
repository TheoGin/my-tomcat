package com.example.tomcat.handler;

import com.example.tomcat.http.MyHttpServletRequest;
import com.example.tomcat.http.MyHttpServletResponse;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MyRequestHandler implements Runnable {

    private Socket socket;

    public MyRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("当前线程：" + Thread.currentThread().getName());

        try {
            InputStream inputStream = socket.getInputStream();
            MyHttpServletRequest myHttpServletRequest = new MyHttpServletRequest(inputStream);
            System.out.println("myHttpServletRequest: " + myHttpServletRequest.toString());
            /* BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String requestMessage = null;

            System.out.println("============= 开始读取请求 =============");
            while ((requestMessage = bufferedReader.readLine()) != null) {
                if (requestMessage.isEmpty()) {
                    break;
                }

                System.out.println(requestMessage);
            }
            System.out.println("============= 读取请求结束 ============="); */

            /* OutputStream outputStream = socket.getOutputStream();
            String responseLineAndHeader = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html; charset=UTF-8\r\n\r\n";
            String response = responseLineAndHeader + "<h1>Hello, World!世界2</h1>";
            outputStream.write(response.getBytes());
            outputStream.flush();
            outputStream.close(); */

            MyHttpServletResponse myHttpServletResponse = new MyHttpServletResponse(socket.getOutputStream());
            OutputStream outputStream = myHttpServletResponse.getOutputStream();
            String response = MyHttpServletResponse.RESPONSE_LINE_AND_HEADER + "<h1>Hello, World!世界2</h1>";
            outputStream.write(response.getBytes());
            outputStream.flush();
            outputStream.close();
            // bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
