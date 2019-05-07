package com.vr.userserver.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class LoginForm implements Serializable {
    private String code;
    private String nickName;
    private String avatarUrl;
    private String sex;
    private String addr;
}
