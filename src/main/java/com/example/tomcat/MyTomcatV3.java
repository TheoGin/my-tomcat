package com.example.tomcat;

import com.example.tomcat.handler.MyRequestHandler;
import com.example.tomcat.servlet.MyServlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MyTomcatV3 {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("My Tomcat is running... port: 8080");
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            new Thread(new MyRequestHandler(socket)).start();
        }
    }
}
