package com.vr.chatroom.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ExchangeChannel {
    private static Channel channel=null;
    private static Connection conn=null;
    static {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("47.94.87.74");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            //创建连接
            conn = factory.newConnection();
            //创建通道
            channel = conn.createChannel();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Channel getChannel(){
        return channel;
    }

    private ExchangeChannel(){}


}
