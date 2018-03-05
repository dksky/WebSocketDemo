package com.ldk.webSocket;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * websocket接收数据处理类
 * @author Administrator
 *
 */
@Service
public class ReceiveDataHandler {

	public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	/**
	 * 处理用户端上报的消息
	 * @param payload
	 * @param sessions
	 */
	public void handleMessageForUserApp(String message, Map<String, WebSocketSession> sessions) throws Exception {
		Map msgMap = gson.fromJson(message, Map.class);
		if(msgMap.get("method") != null) {
			String method = msgMap.get("method").toString();
			switch(method) {
			case "chat":
				System.err.println("用户端：执行方法chat");
				chat(msgMap, sessions);
				break;
			case "saveUserLocation":
				System.err.println("用户端：执行方法saveUserLocation");
				break;
			case "queryNearCareWorkers":
				System.err.println("用户端：执行方法queryNearCareWorkers");
				break;
			case "queryCareWorkerByCareAppoId":
				System.err.println("用户端：执行方法queryCareWorkerByCareAppoId");
				break;
			}
		}
	}
	
	/**
	 * 聊天
	 * @param msgMap
	 * @param sessions
	 * @throws IOException 
	 */
	private void chat(Map msgMap, Map<String, WebSocketSession> sessions) throws IOException {
		String userId = msgMap.get("userId").toString();
		String type = msgMap.get("type").toString();
		Map data = (Map)msgMap.get("data");
		String toUserId = data.get("toUserId").toString();
		String message = data.get("message").toString();
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("fromUserId", userId);
		map.put("message", message);
		String resultMsg = gson.toJson(formatSendData("chat", map));
		//执行推送
		WebSocketSession session = sessions.get(toUserId);
		if(session != null && session.isOpen()) {
			session.sendMessage(new TextMessage(resultMsg));
		}
	}

	/**
	 * 格式化返回信息map
	 * @param method
	 * @param sendData
	 * @return
	 */
	public static Map<String, Object> formatSendData(String method, Object sendData) {
		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("method", method);
		resultMap.put("data", sendData);
		return resultMap;
	}
}
