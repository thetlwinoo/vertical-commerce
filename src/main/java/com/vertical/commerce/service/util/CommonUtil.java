package com.vertical.commerce.service.util;

public class CommonUtil {
    public static boolean isBlank(String param) {
        return param.isEmpty() || param.trim().equals("");
    }

    public static String handleize(String text) {
        String _handle =  text.toLowerCase()
            .replaceAll("\\s+", "-")
            .replaceAll("[^\\w\\-]+", "")
            .replaceAll("\\-\\-+", "-")
            .replaceAll("^-+", "")
            .replaceAll("-+$", "");

        return _handle;
    }

    public static boolean isNullOrEmptyString(String it){
        return it==null || it.isEmpty();
    }
}
