package com.vr.userserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vr.commonutils.exception.ThisException;
import com.vr.commonutils.utils.*;
import com.vr.redisserver.redis.RedisPoolUtil;
import com.vr.userserver.dao.UserInfoDao;
import com.vr.userserver.entity.LoginForm;
import com.vr.userserver.entity.UserDetailInfo;
import com.vr.userserver.entity.UserInfo;
import com.vr.userserver.service.UserInfoService;
import com.vr.userserviceapi.entity.UserInfoDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.secret}")
    private String secret;
    @Override
    public List<UserInfo> findAll() {
        return userInfoDao.findAll();
    }


    @Override
    public UserInfoDto findUserInfoByOpenId(String openid) {
        return toDto(userInfoDao.findByOpenid(openid));
    }

    @Override
    @Transactional
    public R login(LoginForm loginForm) {
        if (loginForm == null || StringUtils.isBlank(loginForm.getCode())) {
            ThisException.exception(ErrorEnum.REQUEST_PARAM_FAILED);
        }
        JSONObject sessionKeyOpenId = getSessionKeyOrOpenId(loginForm.getCode());
        if(sessionKeyOpenId.toString().contains(Consts.ERROR)){
            ThisException.exception(ErrorEnum.GET_USERINFO_FAIL);
        }
        String openid = sessionKeyOpenId.getString(Consts.OPENID);

        //String sessionKey = sessionKeyOpenId.getString(Consts.SESSION_KEY);

        UserInfo user = userInfoDao.findByOpenid(openid);
        if (user == null) {
            user = new UserInfo();
            user.setOpenid(openid);
            user.setAddr(loginForm.getAddr());
            user.setAvatarUrl(loginForm.getAvatarUrl());
            user.setNickName(loginForm.getNickName());
            user.setSex(loginForm.getSex());
            user.setFacus(0l);
            Boolean b = RedisPoolUtil.exist(Consts.REDIS_PUSH_NUM);
            if(!b){
                String maxPushNum=userInfoDao.findMaxPushNum();
                System.out.println(maxPushNum);
                RedisPoolUtil.set(Consts.REDIS_PUSH_NUM,maxPushNum);
            }
            Long push_num = RedisPoolUtil.incr(Consts.REDIS_PUSH_NUM, 1);
            user.setPushNum(String.valueOf(push_num));
            int b1 = userInfoDao.save(user);
            user.setEmail("");
            user.setIncome(0f);
            if (b1 < 0) {
                ThisException.exception(ErrorEnum.DATABASE_ERROR);
            }
        }
        String uuid = UUID.randomUUID().toString().replace("-","");

        RedisPoolUtil.setEx(uuid, JsonUtil.toJson(toDto(user)), Consts.LOGIN_EXPIRE);

        return R.ok().put("token", uuid).put("roomNum",user.getPushNum());
    }

    @Override
    public int updateUserInfoByOpenid(String openid, UserDetailInfo userDetailInfo) {
        return userInfoDao.updateUserInfoByOpenid(openid,userDetailInfo);
    }


    private JSONObject getSessionKeyOrOpenId(String code) {
        //微信端登录code
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        String requestUrlParam = "appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
        //发送post请求读取调用微信接口获取openid用户唯一标识
        JSONObject jsonObject = JSON.parseObject(HttpUtil.sendGet(requestUrl, requestUrlParam));
        return jsonObject;
    }

    private UserInfoDto toDto(UserInfo userInfo) {
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(userInfo, userInfoDto);
        return userInfoDto;
    }

    private String genToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
