package com.cs.sys.service.impl;

import com.cs.common.vo.Node;
import com.cs.common.vo.exception.ServiceException;
import com.cs.sys.dao.SysMenuDao;
import com.cs.sys.dao.SysRoleMenuDao;
import com.cs.sys.entity.SysMenu;
import com.cs.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    @Override
    public List<Map<String, Object>> findObjects() {
        List<Map<String, Object>> list = sysMenuDao.findObjects();
        for (Map<String,Object> map:list
             ) {
            System.out.println(map);
        }
        if(list==null || list.size()==0){
            throw new ServiceException("没有查找到记录");
        }
        return list;
    }

    @Override
    public int deleteObjects(Integer id) {
        if(id==null || id<1){
            throw new ServiceException("请选择id");
        }
        int childCount = sysMenuDao.findChildCount(id);
        if(childCount>0){
            throw new ServiceException(("请删除子菜单"));
        }
        sysRoleMenuDao.deleteObjectsByMenuId(id);
        int row = sysMenuDao.deleteObject(id);
        if(row==0){
            throw new ServiceException("该记录已不存在");
        }
        return row;
    }

    @Override
    public List<Node> findZtreeMenuNodes() {
        return sysMenuDao.findZtreeMenuNodes();
    }

    @Override
    public int saveObject(SysMenu entity) {
        //1.合法验证
        if(entity==null)
            throw new ServiceException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getName()))
            throw new ServiceException("菜单名不能为空");
        int rows;
        //2.保存数据
        try{
            rows=sysMenuDao.insertObject(entity);
        }catch(Exception e){
            e.printStackTrace();
            throw new ServiceException("保存失败");
        }
        //3.返回数据
        return rows;
    }

    @Override
    public int updateObject(SysMenu entity) {
        //1.合法验证
        if(entity==null)
            throw new ServiceException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getName()))
            throw new ServiceException("菜单名不能为空");

        //2.更新数据
        int rows=sysMenuDao.updateObject(entity);
        if(rows==0)
            throw new ServiceException("记录可能已经不存在");
        //3.返回数据
        return rows;
    }
}
