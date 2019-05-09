package com.vr.userserver.controller;

import com.vr.commonutils.exception.ThisException;
import com.vr.commonutils.utils.Consts;
import com.vr.commonutils.utils.ErrorEnum;
import com.vr.commonutils.utils.JsonUtil;
import com.vr.commonutils.utils.R;
import com.vr.redisserver.redis.RedisPoolUtil;
import com.vr.userserver.entity.LoginForm;
import com.vr.userserver.entity.UserDetailInfo;
import com.vr.userserver.service.UserInfoService;
import com.vr.userserviceapi.entity.UserInfoDto;
import com.vr.vrfilterclient.selfAnnotation.AccessLimit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "用户模块")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/finduser")
    public R login(UserInfoDto dto) {
        UserInfoDto r = userInfoService.findUserInfoByOpenId(dto.getOpenid());
        return R.ok().put("data",r);
    }


    @PostMapping("/login")
    @ApiOperation("登录")
    @AccessLimit(needLogin = false)
    public R login(@RequestBody LoginForm loginForm) {
        R r = userInfoService.login(loginForm);
        return r;
    }

    @PostMapping("/updateuser")
    @ApiOperation("更新用户信息")
    public R updateUserInfo(@RequestBody UserDetailInfo userDetailInfo, UserInfoDto userInfoDto,HttpServletRequest request) {
        String token = request.getHeader("token");
        token = token == null ? request.getParameter("token") : token;
        BeanUtils.copyProperties(userDetailInfo, userInfoDto);
        int b = userInfoService.updateUserInfoByOpenid(userInfoDto.getOpenid(), userDetailInfo);
        if (b < 0) {
            ThisException.exception(ErrorEnum.DATABASE_ERROR);
        }
        RedisPoolUtil.setEx(token, JsonUtil.toJson(userInfoDto), Consts.LOGIN_EXPIRE);
        return R.ok();
    }
}
