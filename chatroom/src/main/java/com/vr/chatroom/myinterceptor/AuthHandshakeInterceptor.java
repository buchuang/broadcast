package com.vr.chatroom.myinterceptor;

import com.vr.commonutils.utils.Consts;
import com.vr.commonutils.utils.JsonUtil;
import com.vr.redisserver.redis.RedisPoolUtil;
import com.vr.userserviceapi.entity.UserInfoDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AuthHandshakeInterceptor.class);
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("连接之前");
        ServletServerHttpRequest servletRequest = getServletRequest(request);
        HttpHeaders headers = servletRequest.getHeaders();



        List<String> tokens = headers.get(Consts.TOKEN);
        List<String> push_nums = headers.get(Consts.HEAD_PUSH_NUM);

        if(push_nums==null || StringUtils.isBlank(push_nums.get(0))){
            return false;
        }
        String realnum=push_nums.get(0);

        String info = RedisPoolUtil.get(realnum);
        UserInfoDto userInfoDto= (UserInfoDto) JsonUtil.fromJson(info,UserInfoDto.class);

        if(userInfoDto==null){
            if(tokens==null || StringUtils.isBlank(tokens.get(0))){
                return false;
            }
            String realtoken=tokens.get(0);
            String loginuser = RedisPoolUtil.get(realtoken);
            UserInfoDto realUser= (UserInfoDto) JsonUtil.fromJson(loginuser,UserInfoDto.class);
            if(realUser==null){
                return false;
            }
            if(realnum.equals(realUser.getPushNum())){
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//        ServletServerHttpRequest servletRequest = getServletRequest(request);
//        HttpHeaders headers = servletRequest.getHeaders();
//
//        List<String> push_nums = headers.get(Consts.PUSH_NUM);
//        String realnum=push_nums.get(0);
//        redisClient.incr(realnum,1);
        System.out.println("连接之后");
    }

    // 参考 HttpSessionHandshakeInterceptor
    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(false);
        }
        return null;
    }
    private ServletServerHttpRequest getServletRequest(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest;
        }
        return null;
    }
}
