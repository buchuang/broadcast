package com.vr.vrfilterclient.resolver;

import com.vr.userserviceapi.entity.UserInfoDto;

public class UserContext {
	
	private static ThreadLocal<UserInfoDto> userHolder = new ThreadLocal<UserInfoDto>();
	
	public static void setUser(UserInfoDto user) {
		userHolder.set(user);
	}
	
	public static UserInfoDto getUser() {
		return userHolder.get();
	}

}
