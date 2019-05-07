package com.vr.plateserverapi.serviceapi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(serviceId = "PLATE-SERVER",path = "/server")
public interface PlateServerApi {

    @PostMapping("/updatestatus/{pushNum}")
    boolean updateStatusAndCount(Integer count, @PathVariable("pushNum") String pushNum);
}
