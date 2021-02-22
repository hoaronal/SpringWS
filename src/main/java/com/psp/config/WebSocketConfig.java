package com.psp.config;

import com.psp.WebSocketMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private static final String WS_URL_TAIL = "/ws";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(wsHandler(), WS_URL_TAIL)
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler wsHandler() {

        return new WebSocketMessageHandler();
    }

}
