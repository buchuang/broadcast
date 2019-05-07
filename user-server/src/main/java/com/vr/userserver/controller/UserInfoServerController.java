package com.vr.userserver.controller;

import com.vr.commonutils.utils.Consts;
import com.vr.userserver.redis.RedisClient;
import com.vr.userserver.service.UserInfoService;
import com.vr.userserviceapi.entity.UserInfoDto;
import com.vr.vrfilterclient.selfAnnotation.AccessLimit;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/server")
public class UserInfoServerController {

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/authentication")
    @AccessLimit(needLogin = false)
    public UserInfoDto authentication(@RequestHeader("token") String token) {
        return (UserInfoDto) redisClient.get(token);
    }

    @PostMapping("/flushLoginTime")
    @AccessLimit(needLogin = false)
    public void flushLoginTime(@RequestHeader("token") String token) {
        redisClient.expire(token, Consts.LOGIN_EXPIRE);
    }

    @GetMapping("/test")
    @AccessLimit(needLogin = false)
    public void test() {
//        redisTemplate.opsForValue().set("increase","10000000");
//        Long increase = redisTemplate.opsForValue().increment("increase", 5l);
//        String increase1 = (String) redisClient.get("increase");
//        redisClient.incr("increase",1);
//        System.out.println(increase);
//        System.out.println(increase1);
 //       redisClient.set(Consts.REDIS_PUSH_NUM,"10000001");
        redisClient.incr(Consts.REDIS_PUSH_NUM,1);
//        redisTemplate.opsForValue().set(Consts.REDIS_PUSH_NUM,"10000001");
    }
}
