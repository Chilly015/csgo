package com.cs.sys.dao;

import com.cs.common.vo.SysRoleMenuVo;
import com.cs.common.vo.SysUserDeptVo;
import com.cs.sys.entity.SysRole;
import com.cs.sys.entity.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysUserDao {
    int getRowCount(@Param("username") String username);

    List<SysUserDeptVo> findPageObjects(
            @Param("username")String  username,
            @Param("startIndex")Integer startIndex,
            @Param("pageSize")Integer pageSize);

    int validById(
            @Param("id")Integer id,
            @Param("valid")Integer valid,
            @Param("modifiedUser")String modifiedUser);

    int insertObject(SysUser entity);

    SysUserDeptVo doFindObjectById(Integer id);

    int updateUserObject(SysUser entity);

    int updatePassWord(@Param("password") String password,
                       @Param("salt")String salt,
                       @Param("id")Integer id);

    @Select("select * from sys_users where username = #{username}")
    SysUser findUserByUserName(String username);

}
