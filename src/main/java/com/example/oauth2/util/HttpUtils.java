package com.example.oauth2.util;

import com.example.exception.domain.BadRequestException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class HttpUtils {

    private static final String[] IP_HEADER_NAMES = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "Authorization",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR"
    };

    public static String getIp(HttpServletRequest request) {
        if (request == null)
            throw new BadRequestException("Request is null");
        return Arrays.stream(IP_HEADER_NAMES)
            .map(request::getHeader)
            .filter(h -> h != null && h.length() != 0 && !"unknown".equalsIgnoreCase(h))
            .map(h -> h.split(",")[0])
            .reduce("", (h1, h2) -> h1 + ":" + h2);
    }

    public static String getIp() {
        return getIp(getRequest());
    }

    public static String getJWT() {
        return getJWT(getRequest());
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static String getJWT(HttpServletRequest request) {
        if (request == null)
            throw new BadRequestException("Request is null");
        return request.getHeader("Authorization").split(" ")[1];
    }
}
