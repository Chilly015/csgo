package com.cs.sys.dao;

import com.cs.common.vo.Node;
import com.cs.sys.entity.SysDept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SysDeptDao {

    List<Map<String,Object>> doFindObjects();

    @Delete("delete from sys_depts where id=#{id}")
    int doDeleteObjectById(Integer id);

    int doSaveObject(SysDept entity);

    int doUpdateObject(SysDept entity);

    @Select("select id,name,parentId from sys_depts")
    List<Node> findZtreeDeptNodes();
}
