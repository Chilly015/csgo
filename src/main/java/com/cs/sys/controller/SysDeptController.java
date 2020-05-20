package com.cs.sys.controller;

import com.cs.common.vo.JsonResult;
import com.cs.sys.entity.SysDept;
import com.cs.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @RequestMapping("doFindObjects")
    public JsonResult doFindObjects(){
        return new JsonResult(sysDeptService.doFindObjects());
    }

    @RequestMapping("doDeleteObject")
    public JsonResult doDeleteObject(Integer id){
        sysDeptService.doDeleteObjectById(id);
        return new JsonResult("delete ok");
    }

    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(SysDept entity){
        sysDeptService.doSaveObject(entity);
        return new JsonResult("save ok");
    }

    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(SysDept entity){
        sysDeptService.doUpdateObject(entity);
        return new JsonResult("update ok");
    }

    @RequestMapping("doFindZTreeDeptNodes")
    public JsonResult doFindZTreeDeptNodes(){
        return new JsonResult(sysDeptService.findZtreeDeptNodes());
    }
}
