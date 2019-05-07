package com.vr.plateserver.controller;

import com.vr.vrfilterclient.selfAnnotation.AccessLimit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {
    @GetMapping("/myindex")
    @AccessLimit(needLogin = false)
    public String index(){
        return "upload";
    }
}
