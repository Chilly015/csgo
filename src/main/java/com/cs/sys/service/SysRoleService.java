package com.cs.sys.service;

import com.cs.common.vo.CheckBox;
import com.cs.common.vo.PageObject;
import com.cs.common.vo.SysRoleMenuVo;
import com.cs.sys.entity.SysRole;

import java.util.List;

public interface SysRoleService {
    PageObject<SysRole> findPageObjects(String username, Integer pageCurrent);

    int deleteObject(Integer id);

    SysRoleMenuVo findObjectById(Integer id);

    int saveObject(SysRole entity,Integer[]menuIds);

    int updateObject(SysRole entity,Integer[] menuIds);

    List<CheckBox> findRoles();

}
