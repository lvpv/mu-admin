package com.lvpb.mu.handler;

import com.alibaba.ttl.TransmittableThreadLocal;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/8 00:22
 * @description RequestContextHandler
 */
public class RequestContextHandler {


    private static final ThreadLocal<HttpServletRequest> REQUEST_HOLDER = new TransmittableThreadLocal<>();

    public static void setRequest(HttpServletRequest request) {
        REQUEST_HOLDER.set(request);
    }

    public static HttpServletRequest getRequest() {
        return REQUEST_HOLDER.get();
    }

    public static void clear() {
        REQUEST_HOLDER.remove();
    }
}
