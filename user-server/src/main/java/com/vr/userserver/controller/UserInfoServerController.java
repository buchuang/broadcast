package com.vr.userserver.controller;

import com.vr.commonutils.utils.JsonUtil;
import com.vr.redisserver.redis.RedisPoolUtil;
import com.vr.userserver.service.UserInfoService;
import com.vr.userserviceapi.entity.UserInfoDto;
import com.vr.vrfilterclient.selfAnnotation.AccessLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class UserInfoServerController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/test")
    @AccessLimit(needLogin = false)
    public void test() {
        UserInfoDto userInfo = userInfoService.findUserInfoByOpenId("IqwENsLpTX0/39kXz3qV1w==");
        RedisPoolUtil.set("11111", JsonUtil.toJson(userInfo));
    }
}
