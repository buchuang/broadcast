package com.vr.plateserver.service.impl;

import com.vr.commonutils.exception.ThisException;
import com.vr.commonutils.utils.ErrorEnum;
import com.vr.commonutils.utils.R;
import com.vr.plateserver.dao.PlateDao;
import com.vr.plateserver.entity.*;
import com.vr.plateserver.service.PlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlateServiceImpl implements PlateService {

    @Autowired
    private PlateDao plateDao;

    @Override
    public R findDynamicsByPushNum(String pushNum) {
        List<Dynamics> dynamics = plateDao.findDynamicsByPushNum(pushNum);
        return R.ok().put("data", dynamics);
    }

    @Override
    public R saveRoomInfo(DynamicsForm dynamicsForm) {
        if (dynamicsForm == null) {
            ThisException.exception(ErrorEnum.REQUEST_PARAM_FAILED);
        }
        if (dynamicsForm.getRoomNum() == null || "".equals(dynamicsForm.getRoomNum())) {
            ThisException.exception(ErrorEnum.ROOM_NUM_NOTNULL);
        }
        if (dynamicsForm.getPlateId() == null) {
            ThisException.exception(ErrorEnum.PLATEID_NOTNULL);
        }
        if (dynamicsForm.getRoomTitle() == null || "".equals(dynamicsForm.getRoomTitle())) {
            ThisException.exception(ErrorEnum.ROOMTITLE_NOTNULL);
        }

        RoomDetail roominfo = plateDao.findRoomInfoByPushNum(dynamicsForm.getRoomNum());

        if (roominfo == null) {
            RoomDetail roomDetail = new RoomDetail();
            roomDetail.setRoomNum(dynamicsForm.getRoomNum());
            roomDetail.setRoomUrl(dynamicsForm.getRoomUrl());
            int b = plateDao.saveRoomInfo(roomDetail);
            if (b < 0) {
                ThisException.exception(ErrorEnum.DATABASE_ERROR);
            }
        }
        dynamicsForm.setRoomStatus(1);
        dynamicsForm.setStartTime(new Date());
        dynamicsForm.setRoomLike(0);
        dynamicsForm.setAudiNum(0);
        int b = plateDao.saveDynamic(dynamicsForm);
        if (b < 0) {
            ThisException.exception(ErrorEnum.DATABASE_ERROR);
        }
        return R.ok();
    }

    @Override
    public R clickLike(Integer dynamicId) {
        int b = plateDao.clickLike(dynamicId);
        if (b < 0) {
            ThisException.exception(ErrorEnum.DATABASE_ERROR);
        }
        return R.ok();
    }

    @Override
    public boolean updateStatusAndCount(Integer count, String pushNum) {
        int b = plateDao.updateStatusAndCount(count, pushNum);
        if (b < 0) {
            return false;
        }
        return true;
    }

    @Override
    public R clicknoLike(Integer dynamicId) {
        int b = plateDao.clickNoLike(dynamicId);
        if (b < 0) {
            ThisException.exception(ErrorEnum.DATABASE_ERROR);
        }
        return R.ok();
    }

    @Override
    public R findPlateInfo() {
        List<Plate> plates = plateDao.findPlateInfo();
        List<PlateDetail> plateDetails = plateDao.findPlateDetailInfo();

        for (Plate p : plates) {
            for (PlateDetail pd : plateDetails) {
                if (pd.getPlateId() == p.getPlateId()) {
                    p.getPlateDetail().add(pd);
                }
            }
        }
        return R.ok().put("data", plates);
    }

    @Override
    public R findPlates() {
        return R.ok().put("data", plateDao.findPlateInfo());
    }

    @Override
    public R findPlateDetails() {
        return R.ok().put("data", plateDao.findPlateDetailInfo());
    }

    @Override
    public R findRoomInfo(Integer plateId) {
        if (plateId == null) {
            ThisException.exception(ErrorEnum.REQUEST_PARAM_FAILED);
        }
        List<DynamicsForm> dynamics = plateDao.findRoomInfo(plateId);
        for (DynamicsForm df : dynamics) {
            RoomDetail roomdetail = plateDao.findRoomUrlByRoomNum(df.getRoomNum());
            df.setRoomUrl(roomdetail.getRoomUrl());
            df.setHeadImg(roomdetail.getHeadImg());
            df.setNickname(roomdetail.getNickname());
        }
        return R.ok().put("data", dynamics);
    }
}
