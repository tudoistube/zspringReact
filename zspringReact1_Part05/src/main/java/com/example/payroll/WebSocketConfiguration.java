/*
 * ...added since Part 4.Event using WebSocket.
 *    https://spring.io/guides/tutorials/react-and-spring-data-rest/#react-and-spring-data-rest-part-4
 *    @EnableWebSocketMessageBroker, MESSAGE_PREFIX, registerStompEndpoints(),
 *    configureMessageBroker() 의 설정으로, Spring Data REST 이벤트 안으로 다가가서
 *    WebSocket 으로 Spring Data REST 이벤트를 게재할 수 있음.      
 *    
 */
package com.example.payroll;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


@Component
/*
 * ...@EnableWebSocketMessageBroker : turns on WebSocket upport.
 *    AbstractWebSocketMessageBrokerConfigurer : provides a convenient base class 
 *    to configure basic features. 
 *    
 */
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {


//...MESSAGE_PREFIX is the prefix you will prepend to every message’s route.
//   모든 메시지의 경로에 접두어로 붙는 접두사.	
	static final String MESSAGE_PREFIX = "/topic";

//...registerStompEndpoints() is used to configure the endpoint on the backend 
//	 for clients and server to link (/payroll).
//   ('/payroll) 경로의 연결에 대한 백엔드에 있는 서버와 클라이언트의 엔드포인트를 설정함.	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/payroll").withSockJS();
	}

//...configureMessageBroker() is used to configure the broker used to relay
//   messages between server and client.	
//   서버와 클라이언트 사이의 메시지를 연계하는 브로커를 설정함.	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker(MESSAGE_PREFIX);
		registry.setApplicationDestinationPrefixes("/app");
	}
}
