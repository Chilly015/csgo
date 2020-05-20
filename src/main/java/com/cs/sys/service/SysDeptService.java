package com.cs.sys.service;

import com.cs.common.vo.Node;
import com.cs.sys.entity.SysDept;

import java.util.List;
import java.util.Map;

public interface SysDeptService {
    List<Map<String,Object>> doFindObjects();

    int doDeleteObjectById(Integer id);

    int doSaveObject(SysDept entity);

    int doUpdateObject(SysDept entity);

    List<Node> findZtreeDeptNodes();
}
