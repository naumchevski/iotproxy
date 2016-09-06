package com.naumchevski.iot.proxy.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.naumchevski.iot.proxy.ws.client.HubWebSocketClient;

@Service
public class HubService {
	
	private static final Map<String, HubWebSocketClient> connections = 
			Collections.synchronizedMap(new HashMap<String, HubWebSocketClient>());
	
	@Value("${hub.host}")
	private String host;
	
	public String getHost() {
		return host;
	}
	
	public void addConnection(WebSocketSession clientSocketSession) {
		connections.put(clientSocketSession.getId(), 
				new HubWebSocketClient(host, clientSocketSession.getUri().toString(), clientSocketSession));
	}
	
	public void SendMessage(String sessionId, String message) {
		HubWebSocketClient client = connections.get(sessionId);
		if (client != null) {
			client.Send(message);
		}
	}
	
	public void Close(String sessionId) {
		HubWebSocketClient client = connections.get(sessionId);
		if (client != null) {
			connections.remove(sessionId);
		}
	}
	
}
