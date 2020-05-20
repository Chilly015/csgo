package com.cs.sys.service;

import com.cs.common.vo.PageObject;
import com.cs.sys.entity.SysLog;

public interface SysLogService {
    PageObject<SysLog> findPageObjects(String username, Integer pageCurrent);

    int deleteObjects(Integer...ids);

    void saveLogObject(SysLog sysLog);

}
