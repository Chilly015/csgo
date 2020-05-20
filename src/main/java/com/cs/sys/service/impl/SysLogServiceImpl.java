package com.cs.sys.service.impl;

import com.cs.common.annotation.RequiredLog;
import com.cs.common.vo.PageObject;
import com.cs.common.vo.exception.ServiceException;
import com.cs.sys.dao.SysLogDao;
import com.cs.sys.entity.SysLog;
import com.cs.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogDao syslogdao;

    @Transactional(readOnly = true)
    @Override
    public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent) {
        if(pageCurrent==null||pageCurrent<1)
            throw new IllegalArgumentException("当前页码不正确");
        int rowCount = syslogdao.getRowCount(username);
        if(rowCount==0)
            throw new ServiceException("系统没有查到对应记录");
        int pageSize = 3;
        int startIndex = (pageCurrent-1)*pageSize;
        List<SysLog> records = syslogdao.findPageObjects(username,startIndex,pageSize);
        System.out.println(records);
        PageObject<SysLog> pageObject = new PageObject<>(pageCurrent, pageSize, rowCount, records);
        return pageObject;
    }

    @Override
    public int deleteObjects(Integer...ids) {
        if(ids==null || ids.length==0)
            throw new ServiceException("请选择一个");
        int rows;
        try {
            rows = syslogdao.deleteObjects(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("系统出现故障,正在维护中");
        }
        if(rows==0)
            throw new ServiceException("该记录已经不存在");
        return rows;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void saveLogObject(SysLog sysLog) {
        System.out.println("save log:"+Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int rows = syslogdao.insertLog(sysLog);
    }

}
