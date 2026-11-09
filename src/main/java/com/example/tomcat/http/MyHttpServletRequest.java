package com.example.tomcat.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class MyHttpServletRequest {
    // GET /calServlet?num1=11&num2=22 HTTP/1.1
    private String method;
    private String uri;
    private HashMap<String, String> parameterMap = new HashMap<>();
    private InputStream inputStream;

    public MyHttpServletRequest(InputStream inputStream) {
        this.inputStream = inputStream;

        parseRequestLine();
    }

    private void parseRequestLine() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String requestLine = bufferedReader.readLine();
            String[] requestLineParts = requestLine.split(" ");
            method = requestLineParts[0];
            String uriPartWithParams = requestLineParts[1];
            int queryIndex = uriPartWithParams.indexOf('?');
            if (queryIndex != -1) {
                uri = uriPartWithParams.substring(0, queryIndex);
                String query = uriPartWithParams.substring(queryIndex + 1);

                if (query.contains("&")) {
                    String[] params = query.split("&");
                    for (String param : params) {
                        if (param.contains("=")) {
                            String[] entry = param.split("=");
                            if (entry.length == 2) {
                                parameterMap.put(entry[0], entry[1]);
                            }
                        }
                    }
                }
            } else {
                uri = uriPartWithParams;
            }

            // Exception in thread "Thread-2" java.lang.RuntimeException: java.net.SocketException: Socket is closed
            // 关闭会导致 socket 关闭
            // bufferedReader.close();
            // inputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getParameter(String name) {
        if (parameterMap.containsKey(name)) {
            return parameterMap.get(name);
        }
        return "";
    }

    @Override
    public String toString() {
        return "MyHttpServletRequest{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", parameterMap=" + parameterMap +
                ", inputStream=" + inputStream +
                '}';
    }
}
