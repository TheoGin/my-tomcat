package com.example.tomcat.utils;

public class WebUtils {
    public static int parseInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            System.out.println("NumberFormatException: " + e.getMessage());
            return defaultValue;
        }
    }
}
