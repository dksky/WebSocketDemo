package com.ldk.webSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * websocket配置类，配置拦截器，路径映射及允许域等
 * @author Administrator
 *
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    
	@Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //允许连接的域,只能以http或https开头
        //String[] allowsOrigins = {"http://www.xxx.com"};
		String[] allowsOrigins = {"*"};
        
        //WebSocket通道
		//用户端
        registry.addHandler(userWebSocketHandler(),"/f/interface/websocket/userApp").setAllowedOrigins(allowsOrigins).addInterceptors(myInterceptor());
        //护工端
        //registry.addHandler(careWorkerWebSocketHandler(),"/f/interface/websocket/careWorkerApp").setAllowedOrigins(allowsOrigins).addInterceptors(myInterceptor());
        System.out.println("开启");
    }
	
    @Bean
    public UserWebSocketHandler userWebSocketHandler() {
        return new UserWebSocketHandler();
    }
    
    /*@Bean
    public CareWorkerWebSocketHandler careWorkerWebSocketHandler() {
    	return new CareWorkerWebSocketHandler();
    }*/
    
    @Bean
    public WebSocketHandshakeInterceptor myInterceptor(){
        return new WebSocketHandshakeInterceptor();
    }
}