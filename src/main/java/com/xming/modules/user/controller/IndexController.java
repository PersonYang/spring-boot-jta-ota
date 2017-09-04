package com.xming.modules.user.controller;

import com.xming.modules.user.service.TestDistributedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mc on 17/9/1.
 */

@RestController
public class IndexController {

    @Autowired
    TestDistributedService testDistributedService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(String isDistribute){
        testDistributedService.TestDistribute(isDistribute);
        return "success";
    }
}
