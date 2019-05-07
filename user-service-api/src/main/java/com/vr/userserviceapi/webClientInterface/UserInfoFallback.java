package com.vr.userserviceapi.webClientInterface;

import com.vr.userserviceapi.entity.UserInfoDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class UserInfoFallback implements UserInfoClientService {
    @Override
    public List<UserInfoDto> findUsers() {
        List<UserInfoDto> list=new ArrayList<>();
        UserInfoDto userInfoDto=new UserInfoDto();
        userInfoDto.setAddr("1111");
        userInfoDto.setAvatarUrl("111");
        userInfoDto.setName("对不起，请稍后访问");
        userInfoDto.setEmail("1111");
        userInfoDto.setNickName("某某");
        userInfoDto.setOpenid("111");
        userInfoDto.setPushNum("111");
        userInfoDto.setSex("男");
        userInfoDto.setTel("121234");
        userInfoDto.setUserId(1);
        list.add(userInfoDto);
        return list;
    }

    @Override
    public UserInfoDto findUser(Integer userid) {
        return null;
    }
}
