package com.cs.sys.controller;

import com.cs.common.vo.JsonResult;
import com.cs.sys.entity.SysUser;
import com.cs.sys.service.SysUserService;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("doLogin")
    public JsonResult doLogin(String username,String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
        //分析:
        //1)token会传给shiro的SecurityManager
        //2)SecurityManager将token传递给认证管理器
        //3)认证管理器会将token传递给realm
        return new JsonResult("login ok");

    }

    /*@RequestMapping("doFindPageObjects")
    public JsonResult doFindPageObjects(String username,Integer pageCurrent) {
        return new JsonResult(sysUserService.findPageObjects(username, pageCurrent));
    }*/

    @GetMapping("user")
    public JsonResult user(String username,Integer pageCurrent){
        return new JsonResult(sysUserService.findPageObjects(username, pageCurrent));
    }

    @RequestMapping("doValidById")
    public JsonResult doValidById(Integer id,Integer valid){
        sysUserService.validById(
                id,valid,"admin");//"admin"用户将来是登陆用户
        return new JsonResult("update ok");
    }

    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(SysUser entity, Integer[] roleIds){
        sysUserService.saveObject(entity,roleIds);
        return new JsonResult("save ok");
    }


    @RequestMapping("doFindObjectById")
    public JsonResult doFindObjectById(Integer id){
        Map<String,Object> map=
                sysUserService.doFindObjectById(id);
        return new JsonResult(map);
    }

    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(SysUser entity,Integer...roleIds){
        sysUserService.updateObject(entity,roleIds);
        return new JsonResult("update ok");
    }

    @RequestMapping("doUpdatePassword")
    public JsonResult doUpdatePassword(String pwd,String newPwd,String cfgPwd){
        sysUserService.updatePassWord(pwd, newPwd, cfgPwd);
        return new JsonResult("修改成功!");
    }


}
