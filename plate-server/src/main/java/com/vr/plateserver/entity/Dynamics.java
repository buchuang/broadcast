package com.vr.plateserver.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Dynamics implements Serializable {
    private Integer dynamicsId;
    private String roomNum;
    private Integer videoUrl;
    private String roomTitle;
    private Integer roomLike;
    private Integer plateId;
    private Integer RoomStatus;
    private String imageUrl;
    private String city;
    private Date startTime;
    private Integer audiNum;
}
