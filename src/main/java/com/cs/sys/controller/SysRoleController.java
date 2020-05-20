package com.cs.sys.controller;

import com.cs.common.vo.JsonResult;
import com.cs.common.vo.PageObject;
import com.cs.sys.entity.SysRole;
import com.cs.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("doFindPageObjects")
    public JsonResult doFindPageObjects(Integer pageCurrent, String username) {
        PageObject<SysRole> pageObjects = sysRoleService.findPageObjects(username, pageCurrent);
        return new JsonResult(pageObjects);
    }

    @RequestMapping("doDeleteObject")
    public JsonResult doDeleteObject(Integer id) {
        sysRoleService.deleteObject(id);
        return new JsonResult("delete ok");
    }

    @RequestMapping("doFindObjectById")
    public JsonResult doFindObjectById(Integer id) {
        return new JsonResult(sysRoleService.findObjectById(id));
    }

    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(SysRole entity, Integer[] menuIds) {
        sysRoleService.saveObject(entity, menuIds);
        return new JsonResult("save ok");
    }

    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(SysRole entity, Integer... menuIds) {
        sysRoleService.updateObject(entity, menuIds);
        return new JsonResult("update ok");
    }

    @RequestMapping("doFindRoles")
    public JsonResult doFindRoles() {
        return new JsonResult(sysRoleService.findRoles());
    }
}