package com.cs.sys.controller;

import com.cs.common.vo.JsonResult;
import com.cs.common.vo.PageObject;
import com.cs.sys.entity.SysLog;
import com.cs.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log/")
public class SysLogController {
    @Autowired
    private SysLogService syslogservice;

    @RequestMapping("/doFindPageObjects")
    public JsonResult doFindPageObjects(String username,Integer pageCurrent){
        PageObject<SysLog> pageObject = syslogservice.findPageObjects(username, pageCurrent);
        return new JsonResult(pageObject);
    }

    @RequestMapping("/doDeleteObjects")
    public JsonResult doDeleteObjects(Integer[] ids){
        syslogservice.deleteObjects(ids);
        return new JsonResult("delete ok");
    }

}
