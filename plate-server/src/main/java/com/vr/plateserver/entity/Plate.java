package com.vr.plateserver.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Plate implements Serializable {
    private Integer plateId;
    private String plateName;
    private List<PlateDetail> plateDetail=new ArrayList<>();
}
