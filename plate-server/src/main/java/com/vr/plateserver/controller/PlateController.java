package com.vr.plateserver.controller;

import com.vr.commonutils.utils.Consts;
import com.vr.commonutils.utils.R;
import com.vr.ftpserver.ftp.FtpUtil;
import com.vr.plateserver.entity.DynamicsForm;
import com.vr.plateserver.service.PlateService;
import com.vr.redisserver.redis.RedisPoolUtil;
import com.vr.vrfilterclient.selfAnnotation.AccessLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PlateController {

    @Autowired
    private PlateService plateService;

    @Autowired
    private FtpUtil ftpUtil;

    @PostMapping("/upload")
    @AccessLimit(needLogin = false)
    public R startBroad(@RequestParam("file")MultipartFile file,@RequestParam("pushNum") String pushNum) throws Exception {
        String fileName=null;



        fileName= ftpUtil.upload(pushNum,file);




        return R.ok().put("imageurl",Consts.IMAGE_ADDR_PREFIX+fileName);
    }
    @GetMapping("/delete")
    @AccessLimit(needLogin = false)
    public R deleteFile(String filename) {
        try {
            String fileName = ftpUtil.deleteFile("10000001",filename);
            System.out.println(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.ok();
    }



    @GetMapping("/dynamics/{pushNum}")
    public R findDynamicsByPushNum(@PathVariable String pushNum) {
        R r = plateService.findDynamicsByPushNum(pushNum);
        return r;
    }

    @PostMapping("/startbroad")
    public R startBroad(@RequestBody DynamicsForm dynamicsForm) {
        R r = plateService.saveRoomInfo(dynamicsForm);
        return r;
    }

    @PostMapping("/stopbroad")
    public R stopBroad(@RequestBody DynamicsForm dynamicsForm) {
        String count = RedisPoolUtil.get(dynamicsForm.getRoomNum() + Consts.MAX_ONLINE_COUNT);
        if (count == null) {
            count = "0";
        }
        boolean b = plateService.updateStatusAndCount(Integer.parseInt(count), dynamicsForm.getRoomNum());
        if (!b) {
            return R.error("数据库异常");
        }
        return R.ok();
    }

    @PostMapping("/like/{dynimicId}")
    public R roomLike(@PathVariable Integer dynamicId) {
        R r = plateService.clickLike(dynamicId);
        return r;
    }

    @PostMapping("/nolike/{dynimicId}")
    public R roomnoLike(@PathVariable Integer dynamicId) {
        R r = plateService.clicknoLike(dynamicId);
        return r;
    }

    @GetMapping("/onlinecount/{pushNum}")
    public R getOnlineCount(@PathVariable String pushNum) {
        String count = RedisPoolUtil.get(pushNum + Consts.ONLINE);
        if (count == null) {
            return R.error("主播不在线");
        }
        return R.ok().put("count", count);
    }

    @GetMapping("/plateinfo")
    @AccessLimit(needLogin = false)
    public R findPlateInfo() {
        R r = plateService.findPlateInfo();
        return r;
    }

    @GetMapping("/plates")
    @AccessLimit(needLogin = false)
    public R findPlates() {
        R r = plateService.findPlates();
        return r;
    }

    @GetMapping("/platedetails")
    @AccessLimit(needLogin = false)
    public R findPlateDetails() {
        R r = plateService.findPlateDetails();
        return r;
    }

    @GetMapping("/roominfo/{plateId}")
    @AccessLimit(needLogin = false)
    public R findRoomInfo(@PathVariable Integer plateId) {
        R r = plateService.findRoomInfo(plateId);
        return r;
    }


}
