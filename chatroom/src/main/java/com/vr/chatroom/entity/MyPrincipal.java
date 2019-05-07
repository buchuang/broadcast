package com.vr.chatroom.entity;

import java.security.Principal;

public class MyPrincipal implements Principal {
    private String pushNum;

    public MyPrincipal(String pushNum){
        this.pushNum = pushNum;
    }
    @Override
    public String getName() {
        return pushNum;
    }
}