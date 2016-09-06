package com.naumchevski.iot.proxy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.naumchevski.iot.proxy.ws.handler.ChannelWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig  extends SpringBootServletInitializer implements WebSocketConfigurer {
	
	@Autowired
	private ChannelWebSocketHandler channelWebSocketHandler;


	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(channelWebSocketHandler, "/hub/open");
	}	
	
}