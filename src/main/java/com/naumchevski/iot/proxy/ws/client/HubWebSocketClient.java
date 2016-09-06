package com.naumchevski.iot.proxy.ws.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class HubWebSocketClient {

	private String query;
	private String host;
	
	private WebSocketSession clientSocketSession;
	private WebsocketClientEndpoint clientEndPoint;
	
	public HubWebSocketClient(String host, String query, WebSocketSession clientSocketSession) {
		this.host = host;
		this.query = query;
		this.clientSocketSession = clientSocketSession;
		
		connect();
	}
	
	private void connect() {
		
		try {
            clientEndPoint = new WebsocketClientEndpoint(new URI(getWSUri()));

            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                	try {
						if (clientSocketSession.isOpen()) {
							if (message.length() > 3) {
								System.out.println(message);
								clientSocketSession.sendMessage(new TextMessage(message.getBytes()));
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            });
            

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
		
	}
	
	public void Send(String message) {
		clientEndPoint.sendMessage(message);
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	private String getWSUri() {
		return String.format("%s%s", this.host, this.query);
//		return this.host;
	}
	
	public WebSocketSession getClientSocketSession() {
		return clientSocketSession;
	}
	
	public void setClientSocketSession(WebSocketSession clientSocketSession) {
		this.clientSocketSession = clientSocketSession;
	}
	
}
