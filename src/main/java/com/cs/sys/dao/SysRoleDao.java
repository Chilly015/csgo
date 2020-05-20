package com.cs.sys.dao;

import com.cs.common.vo.CheckBox;
import com.cs.common.vo.SysRoleMenuVo;
import com.cs.sys.entity.SysRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysRoleDao {

    @Select("select count(*) from sys_roles")
    int getRowCount();

    List<SysRole> findObjects(@Param("startIndex") Integer startIndex,
                              @Param("pageSize") Integer pageSize,
                              @Param("username") String username);

    @Delete("delete from sys_roles where id=#{id}")
    int deleteObject(Integer id);

    SysRoleMenuVo findObjectById(Integer id);

    int insertObject(SysRole entity);

    int updateObject(SysRole entity);

    @Select("select id,name from sys_roles")
    List<CheckBox> findRoles();
}
