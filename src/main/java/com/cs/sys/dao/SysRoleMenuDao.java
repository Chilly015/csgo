package com.cs.sys.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysRoleMenuDao {

    @Delete("delete from sys_role_menus where menu_id=#{id}")
    int deleteObjectsByMenuId(Integer id);

    @Delete("delete from sys_role_menus where role_id=#{id}")
    int deleteObjectsByRoleId(Integer roleId);


    int insertObjects(
            @Param("roleId")Integer roleId,
            @Param("menuIds")Integer[] menuIds);

}
