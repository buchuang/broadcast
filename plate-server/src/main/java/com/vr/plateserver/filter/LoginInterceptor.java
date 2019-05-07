package com.vr.plateserver.filter;

import com.vr.userserviceapi.entity.UserInfoDto;
import com.vr.vrfilterclient.interceptor.UserAccessInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class LoginInterceptor extends UserAccessInterceptor {

    @Value("${user.service.name}")
    private String userServiceName;
    @Override
    public String getServiceNam() {
        return userServiceName;
    }

    @Override
    public void login(HttpServletRequest request, HttpServletResponse response, UserInfoDto userInfoDto) {
        request.setAttribute("user",userInfoDto);
    }
}
