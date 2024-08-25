package com.car.adverts.utils;

import jakarta.servlet.http.HttpServletRequest;

public class HttpUtils {

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"};

    public static String getClientIpAddress(HttpServletRequest request) {
        if (request != null) {
            for (String header : IP_HEADER_CANDIDATES) {
                String ip = request.getHeader(header);
                if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                    return ip;
                }
            }

            return request.getRemoteAddr();
        }
        return null;
    }
}
