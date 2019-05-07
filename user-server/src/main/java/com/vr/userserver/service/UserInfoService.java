package com.vr.userserver.service;

import com.vr.commonutils.utils.R;
import com.vr.userserver.entity.LoginForm;
import com.vr.userserver.entity.UserDetailInfo;
import com.vr.userserver.entity.UserInfo;
import com.vr.userserviceapi.entity.UserInfoDto;

import java.util.List;

public interface UserInfoService {
    List<UserInfo> findAll();

    UserInfoDto findUserInfoByOpenId(String openid);

    R login(LoginForm loginForm);

    int updateUserInfoByOpenid(String openid, UserDetailInfo userDetailInfo);
}
