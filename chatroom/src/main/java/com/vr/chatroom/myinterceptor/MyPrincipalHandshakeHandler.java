package com.vr.chatroom.myinterceptor;

import com.vr.chatroom.entity.MyPrincipal;
import com.vr.commonutils.utils.Consts;
import com.vr.commonutils.utils.JsonUtil;
import com.vr.redisserver.redis.RedisPoolUtil;
import com.vr.userserviceapi.entity.UserInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Component
public class MyPrincipalHandshakeHandler extends DefaultHandshakeHandler {
    private static final Logger log = LoggerFactory.getLogger(MyPrincipalHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        System.out.println("设置连接信息");
        ServletServerHttpRequest servletRequest = getServletRequest(request);
        HttpHeaders headers = servletRequest.getHeaders();
        List<String> push_nums = headers.get(Consts.HEAD_PUSH_NUM);
        List<String> tokens = headers.get(Consts.TOKEN);

        String realnum=push_nums.get(0);
        String token=tokens.get(0);
        if(realnum==null || token==null){
            return null;
        }

        if(RedisPoolUtil.exist(realnum)){
            return new MyPrincipal(realnum+Consts.VISITOR);
        }else{
            String s = RedisPoolUtil.get(token);
            UserInfoDto userInfoDto = (UserInfoDto)JsonUtil.fromJson(s,UserInfoDto.class);
            if(userInfoDto!=null&&userInfoDto.getPushNum().equals(realnum)){
                RedisPoolUtil.set(realnum, JsonUtil.toJson(userInfoDto));
                return new MyPrincipal(realnum+Consts.ANCHOR);
            }
            return null;
        }
    }

    private ServletServerHttpRequest getServletRequest(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest;
        }
        return null;
    }

}
