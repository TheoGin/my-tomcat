package com.example.tomcat;

import com.example.tomcat.handler.MyRequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyTomcatV2 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("My Tomcat is running... port: 8080");

        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            new Thread(new MyRequestHandler(socket)).start();
        }
    }
}
