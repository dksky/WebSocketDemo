package com.ldk.webSocket;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 用户端webSocket通讯
 * @author Administrator
 *
 */
public class UserWebSocketHandler extends TextWebSocketHandler{
	@Autowired
	private ReceiveDataHandler receiveDataHandler;
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
    private final static Map<String, WebSocketSession> sessions = Collections.synchronizedMap(new HashMap<String, WebSocketSession>());
    
    //接收文本消息，并发送出去
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.err.println(message.getPayload());
        try {
			receiveDataHandler.handleMessageForUserApp(message.getPayload(), sessions);
		} catch (Exception e) {
			logger.error("用户端websocket接受信息处理错误！", e);
			session.sendMessage(new TextMessage("用户端websocket接受信息处理错误！请确认传入正确格式的信息"));
		}
    	//super.handleTextMessage(session, message);
    	//session.sendMessage(message);
    }
    
    //连接建立后处理
    @SuppressWarnings("unchecked")
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	System.err.println("connect to the websocket chat success......");
    	String userId = session.getAttributes().get("userId").toString();
        sessions.put(userId, session);
        //处理离线消息
    }
    
    //抛出异常时处理
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        System.err.println("websocket chat connection closed......error");
        sessions.remove(session);
    }
    
    //连接关闭后处理
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    	System.err.println("websocket chat connection closed......");
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    
    public static Map<String, WebSocketSession> getSessions() {
		return sessions;
	}
}