package com.whoknows.Connectify.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register the /chat endpoint, enabling SockJS fallback options
        registry.addEndpoint("/chat").withSockJS();
    }

    @Override
    public void configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry config) {
        // Configure message broker
        config.enableSimpleBroker("/topic"); // Prefix for messages being sent to clients
        config.setApplicationDestinationPrefixes("/app"); // Prefix for messages sent from clients
    }
}