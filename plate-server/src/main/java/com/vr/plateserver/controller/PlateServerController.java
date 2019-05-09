package com.vr.plateserver.controller;

import com.vr.plateserver.service.PlateService;
import com.vr.vrfilterclient.selfAnnotation.AccessLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlateServerController {
    @Autowired
    private PlateService plateService;


    @PostMapping("/updatestatus/{pushNum}")
    @AccessLimit(needLogin = false)
    public boolean updateStatusAndCount(Integer count, @PathVariable("pushNum") String pushNum){
        boolean b=plateService.updateStatusAndCount(count,pushNum);
        return b;
    }
}
