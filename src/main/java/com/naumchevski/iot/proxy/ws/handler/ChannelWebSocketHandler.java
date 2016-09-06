package com.naumchevski.iot.proxy.ws.handler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.naumchevski.iot.proxy.service.HubService;

@Service
public class ChannelWebSocketHandler extends TextWebSocketHandler {
	
	@Autowired
	private HubService hubService;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		System.out.println("Opened new session in instance " + this);
		hubService.addConnection(session);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws Exception {
		String msg = message.getPayload();
		System.out.println(msg);
		hubService.SendMessage(session.getId(), msg);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception)
			throws Exception {
		session.close(CloseStatus.SERVER_ERROR);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {
		hubService.Close(session.getId());
		super.afterConnectionClosed(session, status);
	}	
}
