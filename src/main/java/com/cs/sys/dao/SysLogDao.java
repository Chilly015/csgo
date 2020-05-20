package com.cs.sys.dao;


import com.cs.sys.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysLogDao {

    int insertLog(SysLog sysLog);

    int getRowCount(@Param("username") String username);

    List<SysLog> findPageObjects(@Param("username") String username,
                                 @Param("startIndex") Integer startIndex,
                                 @Param("pageSize") Integer pageSize);

    int deleteObjects(@Param("ids")Integer...ids);

}
