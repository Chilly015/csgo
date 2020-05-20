package com.cs.sys.service.impl;

import com.cs.common.annotation.RequiredLog;
import com.cs.common.vo.PageObject;
import com.cs.common.vo.SysUserDeptVo;
import com.cs.common.vo.exception.ServiceException;
import com.cs.sys.dao.SysUserDao;
import com.cs.sys.dao.SysUserRoleDao;
import com.cs.sys.entity.SysUser;
import com.cs.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @RequiredLog("查看用户")
    @Override
    public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
        //1.对参数进行校验
        if(pageCurrent==null||pageCurrent<1)
            throw new IllegalArgumentException("当前页码值无效");
        //2.查询总记录数并进行校验
        int rowCount=sysUserDao.getRowCount(username);
        if(rowCount==0)
            throw new ServiceException("没有找到对应记录");
        //3.查询当前页记录
        int pageSize=3;
        int startIndex=(pageCurrent-1)*pageSize;
        List<SysUserDeptVo> records=
                sysUserDao.findPageObjects(username,
                        startIndex, pageSize);
        System.out.println("find log:"+Thread.currentThread().getName());
        //4.对查询结果进行封装并返回
        return new PageObject<>(pageCurrent, pageSize, rowCount, records);
    }

    @RequiredLog("禁用启用")
    @Override
    public int validById(Integer id, Integer valid, String modifiedUser) {
        //1.合法性验证
        if(id==null||id<=0)
            throw new ServiceException("参数不合法,id="+id);
        if(valid!=1&&valid!=0)
            throw new ServiceException("参数不合法,valie="+valid);
        if(StringUtils.isEmpty(modifiedUser))
            throw new ServiceException("修改用户不能为空");
        //2.执行禁用或启用操作
        int rows=sysUserDao.validById(id, valid, modifiedUser);
        //3.判定结果,并返回
        if(rows==0)
            throw new ServiceException("此记录可能已经不存在");
        return rows;
    }

    @Override
    public int saveObject(SysUser entity, Integer[] roleIds) {
        long start=System.currentTimeMillis();
        //1.参数校验
        if(entity==null)
            throw new ServiceException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getUsername()))
            throw new ServiceException("用户名不能为空");
        if(StringUtils.isEmpty(entity.getPassword()))
            throw new ServiceException("密码不能为空");
        if(roleIds==null || roleIds.length==0)
            throw new ServiceException("至少要为用户分配角色");
        //2.保存用户自身信息
        //2.1对密码进行加密
        String source=entity.getPassword();
        String salt= UUID.randomUUID().toString();
        SimpleHash sh=new SimpleHash(//Shiro框架
                "MD5",//algorithmName 算法
                source,//原密码
                salt, //盐值
                1);//hashIterations表示加密次数
        entity.setSalt(salt);
        entity.setPassword(sh.toHex());
        int rows=sysUserDao.insertObject(entity);
        //3.保存用户角色关系数据
        sysUserRoleDao.insertObjects(entity.getId(), roleIds);
        long end=System.currentTimeMillis();
        System.out.println(("total time :"+(end-start)));
        //4.返回结果
        return rows;
    }

    @Override
    public Map<String,Object> doFindObjectById(Integer id) {
        if(id==null||id<1)
            throw new IllegalArgumentException("id不合法");
        SysUserDeptVo user = sysUserDao.doFindObjectById(id);
        if(user==null)
            throw new ServiceException("此用户已经不存在");
        List<Integer> roleIds = sysUserRoleDao.doFindRoleIdsById(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("user",user);
        map.put("roleIds",roleIds);
        for (Map.Entry<String,Object> entry:map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key+":"+value);
        }
        return map;

    }

    @Override
    public int updateObject(SysUser entity, Integer...roleIds) {
        if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		if(roleIds==null||roleIds.length==0)
			throw new IllegalArgumentException("必须为其指定角色");
        int rows = sysUserDao.updateUserObject(entity);
        sysUserRoleDao.deleteObjectsByRoleId(entity.getId());
        sysUserRoleDao.insertObjects(entity.getId(),roleIds);
        return rows;
    }

    @Override
    public int updatePassWord(String password, String newPassword, String cfgPassword) {
        if(StringUtils.isEmpty(password))throw new ServiceException("原密码不能为空");
        if(StringUtils.isEmpty(newPassword))throw new ServiceException("新密码不能为空");
        if(!newPassword.equals(cfgPassword))throw new ServiceException("两次密码不一致");
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        System.out.println(user.getUsername());
        SimpleHash sh = new SimpleHash("MD5",password,user.getSalt(),1);
        if(!sh.toHex().equals(user.getPassword()))throw new ServiceException("原密码不一致");
        String salt = UUID.randomUUID().toString();
        SimpleHash hs = new SimpleHash("MD5",newPassword,salt,1);
        int rows = sysUserDao.updatePassWord(hs.toHex(), salt, user.getId());
        return rows;
    }
}
