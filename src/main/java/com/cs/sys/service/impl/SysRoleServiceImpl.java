package com.cs.sys.service.impl;

import com.cs.common.vo.CheckBox;
import com.cs.common.vo.PageObject;
import com.cs.common.vo.SysRoleMenuVo;
import com.cs.common.vo.exception.ServiceException;
import com.cs.sys.dao.SysRoleDao;
import com.cs.sys.dao.SysRoleMenuDao;
import com.cs.sys.dao.SysUserRoleDao;
import com.cs.sys.entity.SysRole;
import com.cs.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public PageObject<SysRole> findPageObjects(String username, Integer pageCurrent) {
        if(pageCurrent==null || pageCurrent<1){
            throw new ServiceException("当前页码不正确");
        }
        int pageSize = 3;
        int startIndex = (pageCurrent - 1) * pageSize;
        int rowCount = sysRoleDao.getRowCount();
        List<SysRole> list = sysRoleDao.findObjects(startIndex, pageSize,username);
        PageObject<SysRole> pe = new PageObject<>(pageCurrent, pageSize, rowCount,list);
        return pe;
    }

    @Override
    public int deleteObject(Integer id) {
        //1.验证数据的合法性
        if (id == null || id <= 0)
            throw new IllegalArgumentException("请先选择");
        //3.基于id删除关系数据
        sysRoleMenuDao.deleteObjectsByRoleId(id);
        sysUserRoleDao.deleteObjectsByRoleId(id);
        //4.删除角色自身
        int rows = sysRoleDao.deleteObject(id);
        if (rows == 0)
            throw new ServiceException("此记录可能已经不存在");
        //5.返回结果
        return rows;
    }

    @Override
    public SysRoleMenuVo findObjectById(Integer id) {
        //1.合法性验证
        if(id==null||id<=0)
            throw new ServiceException("id的值不合法");
        //2.执行查询
        SysRoleMenuVo result=sysRoleDao.findObjectById(id);
        //3.验证结果并返回
        if(result==null)
            throw new ServiceException("此记录已经不存在");
        return result;
    }

    @Override
    public int saveObject(SysRole entity, Integer[] menuIds) {
        //1.参数有效性校验
        if(entity==null)
            throw new IllegalArgumentException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getName()))
            throw new IllegalArgumentException("角色名不允许为空");
        if(menuIds==null||menuIds.length==0)
            throw new ServiceException("必须为角色分配权限");
        //2.保存角色自身信息
        int rows=sysRoleDao.insertObject(entity);
        //3.保存角色菜单关系数据
        sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
        //4.返回业务结果
        return rows;
    }

    @Override
    public int updateObject(SysRole entity, Integer[] menuIds) {
        //1.合法性验证
        if(entity==null)
            throw new ServiceException("更新的对象不能为空");
        if(entity.getId()==null)
            throw new ServiceException("id的值不能为空");

        if(StringUtils.isEmpty(entity.getName()))
            throw new ServiceException("角色名不能为空");
        if(menuIds==null||menuIds.length==0)
            throw new ServiceException("必须为角色指定一个权限");

        //2.更新数据
        int rows=sysRoleDao.updateObject(entity);
        if(rows==0)
            throw new ServiceException("对象可能已经不存在");
        sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
        sysRoleMenuDao.insertObjects(entity.getId(),menuIds);
        //3.返回结果
        return rows;
    }

    @Override
    public List<CheckBox> findRoles() {
        return sysRoleDao.findRoles();
    }
}
