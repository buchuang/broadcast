package com.vr.plateserverapi.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class DynamicsDto implements Serializable {
    private Integer dynamicsId;
    private Integer roomNum;
    private String roomTitle;
    private Integer roomLike;
    private Integer plateId;
    private String imageUrl;
    private String city;
    private Date startTime;
    private Integer audi_num;
}
