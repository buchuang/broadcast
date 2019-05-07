package com.vr.userserviceapi.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoDto implements Serializable {
    private Integer userId;
    private String openid;
    private String pushNum;
    private String avatarUrl;
    private String nickName;
    private String addr;
    private String sex;
    private String email;
    private String tel;
    private Float income;
    private Long facus;
    private String name;
    private int status;
}
