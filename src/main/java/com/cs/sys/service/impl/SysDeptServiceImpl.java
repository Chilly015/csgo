package com.cs.sys.service.impl;

import com.cs.common.vo.Node;
import com.cs.common.vo.PageObject;
import com.cs.common.vo.SysUserDeptVo;
import com.cs.common.vo.exception.ServiceException;
import com.cs.sys.dao.SysDeptDao;
import com.cs.sys.dao.SysUserDao;
import com.cs.sys.dao.SysUserRoleDao;
import com.cs.sys.entity.SysDept;
import com.cs.sys.entity.SysUser;
import com.cs.sys.service.SysDeptService;
import com.cs.sys.service.SysUserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SysDeptServiceImpl implements SysDeptService {
    @Autowired
    private SysDeptDao sysDeptDao;

    @Override
    public List<Map<String, Object>> doFindObjects() {
        List<Map<String, Object>> list = sysDeptDao.doFindObjects();
        if(list==null || list.size()==0){
            throw new ServiceException("没有查到该记录");
        }
        return list;
    }

    @Override
    public int doDeleteObjectById(Integer id) {
        int rows = sysDeptDao.doDeleteObjectById(id);
        if(rows==0)throw new ServiceException("该记录已不存在");
        return rows;
    }

    @Override
    public int doSaveObject(SysDept entity) {
        if(entity==null)throw new ServiceException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getName()))throw new ServiceException("菜单名不能为空");
        int rows = sysDeptDao.doSaveObject(entity);
        return rows;
    }

    @Override
    public int doUpdateObject(SysDept entity) {
        if(entity==null)throw new ServiceException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getName()))throw new ServiceException("菜单名不能为空");
        int rows = sysDeptDao.doUpdateObject(entity);
        return rows;
    }


    @Override
    public List<Node> findZtreeDeptNodes() {
        return sysDeptDao.findZtreeDeptNodes();
    }
}
