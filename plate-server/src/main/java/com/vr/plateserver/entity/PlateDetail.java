package com.vr.plateserver.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class PlateDetail implements Serializable {
    private Integer id;
    private String plateName;
    private Integer plateId;
}
