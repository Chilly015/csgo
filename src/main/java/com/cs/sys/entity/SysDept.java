package com.cs.sys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysDept implements Serializable {
    private static final long serialVersionUID = 4545054929073827637L;
    private Integer id;
    private String name;
    private Integer parentId;
    private Integer sort;
    private String note;
    private Date createdTime;
    private Date modifiedTime;
    private String createdUser;
    private String modifiedUser;
}
