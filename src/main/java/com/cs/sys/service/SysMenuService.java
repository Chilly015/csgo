package com.cs.sys.service;

import com.cs.common.vo.Node;
import com.cs.sys.entity.SysMenu;

import java.util.List;
import java.util.Map;

public interface SysMenuService {

    List<Map<String,Object>> findObjects();


    int deleteObjects(Integer id);

    List<Node> findZtreeMenuNodes();

    int saveObject(SysMenu entity);

    int updateObject(SysMenu entity);
}
