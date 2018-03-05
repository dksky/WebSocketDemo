package com.ldk.webSocket;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * websocket拦截器，用于过滤请求，添加相关变量参数等
 * @author Administrator
 *
 */
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, 
    		Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
        	String userId = ((ServletServerHttpRequest) request).getServletRequest().getParameter("userId");
        	System.err.println("beforeHandshake---request"+userId);
            attributes.put("userId",userId);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}