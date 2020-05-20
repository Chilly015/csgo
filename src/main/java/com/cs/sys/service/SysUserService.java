package com.cs.sys.service;

import com.cs.common.vo.PageObject;
import com.cs.common.vo.SysUserDeptVo;
import com.cs.sys.entity.SysUser;

import java.util.Map;

public interface SysUserService {
    PageObject<SysUserDeptVo> findPageObjects(
            String username,Integer pageCurrent);

    int validById(Integer id,Integer valid,String modifiedUser);

    int saveObject(SysUser entity,Integer[]roleIds);

    Map<String,Object> doFindObjectById(Integer id);

    int updateObject(SysUser entity,Integer...roleIds);

    int updatePassWord(String password,String newPassword,String cfgPassword);
}
