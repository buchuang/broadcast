package com.vr.chatroom.myinterceptor;

import com.rabbitmq.client.Channel;
import com.vr.chatroom.config.ExchangeChannel;
import com.vr.commonutils.utils.Consts;
import com.vr.redisserver.redis.RedisPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import java.security.Principal;

@Component
public class AuthWebSocketHandlerDecoratorFactory implements WebSocketHandlerDecoratorFactory {
    private static final Logger log = LoggerFactory.getLogger(AuthWebSocketHandlerDecoratorFactory.class);
    private static long line=0;

    @Override
    public WebSocketHandler decorate(WebSocketHandler handler) {
        return new WebSocketHandlerDecorator(handler) {
            @Override
            public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
                // 客户端与服务器端建立连接后，此处记录谁上线了
                Principal principal = session.getPrincipal();
                if(principal != null){
                    String pushNum = principal.getName();
                    //主播上线
                    long count=0;

                    if(pushNum!=null && pushNum.endsWith(Consts.ANCHOR)){
                        System.out.println("主播上线");
                        Channel channel = ExchangeChannel.getChannel();
                        channel.exchangeDeclare(Consts.FANOUT+pushNum.substring(0, pushNum.indexOf(Consts.ANCHOR)),"fanout");
                        RedisPoolUtil.set(pushNum.substring(0,pushNum.indexOf(Consts.ANCHOR))+Consts.ONLINE,"1");
                        count=1;
                    }else if(pushNum!=null && pushNum.endsWith(Consts.VISITOR)){//游客上线
                        count=RedisPoolUtil.incr(pushNum.substring(0, pushNum.indexOf(Consts.VISITOR)) + Consts.ONLINE, 1);
                        RedisPoolUtil.set(pushNum.substring(0,pushNum.indexOf(Consts.VISITOR))+Consts.MAX_ONLINE_COUNT,String.valueOf(count));
                    }else {
                        //return;
                    }
                    System.out.println("在线人数"+count);
                }
                super.afterConnectionEstablished(session);
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                // 客户端与服务器端断开连接后，此处记录谁下线了
                Principal principal = session.getPrincipal();
                if(principal != null){
                    String pushNum = principal.getName();
                    long count=0;
                    System.out.println(pushNum);
                    //主播下线
                    if(pushNum!=null && pushNum.endsWith(Consts.ANCHOR)){
                        System.out.println("主播下线");
                        RedisPoolUtil.del(pushNum.substring(0,pushNum.indexOf(Consts.ANCHOR)));
                        RedisPoolUtil.del(pushNum.substring(0,pushNum.indexOf(Consts.ANCHOR))+Consts.ONLINE);

                    }else if(pushNum!=null && pushNum.endsWith(Consts.VISITOR)){
                        count=RedisPoolUtil.decr(pushNum.substring(0,pushNum.indexOf(Consts.VISITOR))+Consts.ONLINE,1);
                    }else {
                        //return;
                    }
                    System.out.println("在线人数"+count);
                }
                super.afterConnectionClosed(session, closeStatus);
            }
        };
    }
}