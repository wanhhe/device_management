package com.sicau.devicemanagement.domain;

import com.sicau.devicemanagement.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 lab
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public class Lab
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 校区名 */
    @Excel(name = "校区名")
    private String campusId;

    /** 管理老师id */
    @Excel(name = "管理老师id")
    private String managerId;

    private String num;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setCampusId(String campusId) 
    {
        this.campusId = campusId;
    }

    public String getCampusId() 
    {
        return campusId;
    }
    public void setManagerId(String managerId) 
    {
        this.managerId = managerId;
    }

    public String getManagerId() 
    {
        return managerId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("campusId", getCampusId())
            .append("managerId", getManagerId())
            .append("num", getNum())
            .toString();
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
