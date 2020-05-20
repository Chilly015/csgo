package com.cs.sys.dao;

import com.cs.common.vo.Node;
import com.cs.sys.entity.SysMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SysMenuDao {
    List<Map<String,Object>> findObjects();

    @Select("select count(*) from sys_menus where parentId=#{id}")
    int findChildCount(Integer id);

    @Delete("delete from sys_menus where id=#{id}")
    int deleteObject(Integer id);

    @Select("select id,name,parentId from sys_menus")
    List<Node> findZtreeMenuNodes();

    int insertObject(SysMenu entity);

    int updateObject(SysMenu entity);
}
