package com.vr.userserviceapi.webClientInterface;

import com.vr.userserviceapi.entity.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value="USER-SERVER",path = "/server",fallback = UserInfoFallback.class)
public interface UserInfoClientService {

    @GetMapping("/findusers")
    public List<UserInfoDto> findUsers();

    @GetMapping("/finduser")
    public UserInfoDto findUser(@RequestParam("userid") Integer userid);

}
