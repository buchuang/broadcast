package com.vr.plateserver.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class RoomDetail implements Serializable {
    private String roomNum;
    private String roomUrl;
    private String headImg;
    private String nickname;
}
