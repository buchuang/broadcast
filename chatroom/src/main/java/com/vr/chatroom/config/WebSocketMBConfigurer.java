package com.vr.chatroom.config;

import com.vr.chatroom.myinterceptor.AuthHandshakeInterceptor;
import com.vr.chatroom.myinterceptor.AuthWebSocketHandlerDecoratorFactory;
import com.vr.chatroom.myinterceptor.MyPrincipalHandshakeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
class WebSocketMBConfigurer implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private AuthHandshakeInterceptor sessionAuthHandshakeInterceptor;
    @Autowired
    private AuthWebSocketHandlerDecoratorFactory myWebSocketHandlerDecoratorFactory;
    @Autowired
    private MyPrincipalHandshakeHandler myDefaultHandshakeHandler;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 这句话表示在topic和user这两个域上可以向客户端发消息。
        config.setApplicationDestinationPrefixes("/app") // 指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀
                // "STOMP broker relay"处理所有消息将消息发送到外部的消息代理
                .enableStompBrokerRelay("/exchange","/topic","/queue","/amq/queue")
                .setVirtualHost("/")
                //.setRelayHost("192.168.0.113")
                .setRelayHost("47.94.87.74")
                .setClientLogin("guest")
                .setClientPasscode("guest")
                .setSystemLogin("guest")
                .setSystemPasscode("guest")
                .setSystemHeartbeatSendInterval(5000)
                .setSystemHeartbeatReceiveInterval(4000);
    }

        /**
         * 将"/gs-guide-websocket"路径注册为STOMP端点，这个路径与发送和接收消息的目的路径有所不同，这是一个端点，客户端在订阅或发布消息到目的地址前，要连接该端点，
         * 即用户发送请求url="/gs-guide-websocket"与STOMP server进行连接。之后再转发到订阅url；
         * PS：端点的作用——客户端在订阅或发布消息到目的地址前，要连接该端点。
         */
        @Override
        public void registerStompEndpoints(StompEndpointRegistry registry) {
            /*
             * 在网页上可以通过"/gs-guide-websocket"来和服务器的WebSocket连接
             *这个和客户端创建连接时的url有关，其中setAllowedOrigins()方法表示允许连接的域名，withSockJS()方法表示支持以SockJS方式连接服务器。
             */
            registry.addEndpoint("/gs-guide-websocket")
                    .addInterceptors(sessionAuthHandshakeInterceptor)
                    .setHandshakeHandler(myDefaultHandshakeHandler)
                    .setAllowedOrigins("*");
        }
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(myWebSocketHandlerDecoratorFactory);
    }
}
