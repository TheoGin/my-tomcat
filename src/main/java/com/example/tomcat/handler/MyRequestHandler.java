package com.example.tomcat.handler;

import com.example.tomcat.http.MyHttpServletRequest;
import com.example.tomcat.http.MyHttpServletResponse;
import com.example.tomcat.servlet.MyServlet;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MyRequestHandler implements Runnable {

    private Socket socket;
    private static final ConcurrentHashMap<String, MyServlet> servletMapping = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String> servletUrlMapping = new ConcurrentHashMap<>();

    public MyRequestHandler(Socket socket) {
        this.socket = socket;

        parseXML();
    }

    private void parseXML() {
        URL url = MyRequestHandler.class.getResource("/web.xml");

        // System.out.println(url); // file:/D:/Java/code/Javaweb/my-tomcat/target/classes/web.xml
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(url.getFile());
            Element rootElement = document.getRootElement();

            List<Element> elements = rootElement.elements();
            for (Element element : elements) {
                if ("servlet".equalsIgnoreCase(element.getName())) {
                    String servletName = element.elementText("servlet-name");
                    String servletClass = element.elementText("servlet-class");
                    MyServlet myServlet = (MyServlet) Class.forName(servletClass).getDeclaredConstructor().newInstance();
                    servletMapping.put(servletName, myServlet);
                } else if ("servlet-mapping".equalsIgnoreCase(element.getName())) {
                    String servletName = element.elementText("servlet-name");
                    String urlPattern = element.elementText("url-pattern");
                    servletUrlMapping.put(urlPattern, servletName);
                }
            }
            // System.out.println("servletMapping: " + servletMapping);
            // System.out.println("servletUrlMapping: " + servletUrlMapping);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        System.out.println("当前线程：" + Thread.currentThread().getName());

        try {
            InputStream inputStream = socket.getInputStream();
            MyHttpServletRequest myHttpServletRequest = new MyHttpServletRequest(inputStream);
            // System.out.println("myHttpServletRequest: " + myHttpServletRequest.toString());
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

            /* OutputStream outputStream = myHttpServletResponse.getOutputStream();
            String response = MyHttpServletResponse.RESPONSE_LINE_AND_HEADER + "<h1>Hello, World!世界2</h1>";
            outputStream.write(response.getBytes());
            outputStream.flush();
            outputStream.close(); */

            /* MyCalServlet myCalServlet = new MyCalServlet();
            myCalServlet.doGet(myHttpServletRequest, myHttpServletResponse); */
            String uri = myHttpServletRequest.getUri();
            String servletName = servletUrlMapping.get(uri);
            // Exception in thread "Thread-1" java.lang.NullPointerException
            // 解决1：用 HashMap
            // 解决2：给空字符串
            if (servletName == null) {
                servletName = ""; // 没有解决，先尝试一下 HashMap
            }
            MyServlet myServlet = servletMapping.get(servletName);
            myServlet.service(myHttpServletRequest, myHttpServletResponse);

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
