package com.vr.plateserver.service;

import com.vr.commonutils.utils.R;
import com.vr.plateserver.entity.DynamicsForm;

public interface PlateService {
    R findDynamicsByPushNum(String pushNum);

    R saveRoomInfo(DynamicsForm dynamicsForm);

    R clickLike(Integer dynamicId);

    boolean updateStatusAndCount(Integer count, String pushNum);

    R clicknoLike(Integer dynamicId);

    R findPlateInfo();

    R findPlates();

    R findPlateDetails();

    R findRoomInfo(Integer plateId);
}
