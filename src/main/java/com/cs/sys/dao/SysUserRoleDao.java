package com.cs.sys.dao;

import com.cs.sys.entity.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

@Mapper
@Repository
public interface SysUserRoleDao {
    @Delete("delete from sys_user_roles where role_id=#{id}")
    int deleteObjectsByRoleId(Integer roleId);

    int insertObjects(@Param("userId")Integer userId,
                      @Param("roleIds")Integer[] roleIds);

    List<Integer> doFindRoleIdsById(Integer id);

    int deleteRoleIdByUserId(Integer userId);
}
