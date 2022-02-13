package com.sicau.devicemanagement.domain;

import com.sicau.devicemanagement.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 lab
 *
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

    /** 教学楼编号，如十教 */
    @Excel(name = "教学楼编号，如十教")
    private Integer buildNum;

    /** 门牌号，如A312 */
    @Excel(name = "门牌号，如A312")
    private String number;

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
    public void setBuildNum(Integer buildNum) 
    {
        this.buildNum = buildNum;
    }

    public Integer getBuildNum() 
    {
        return buildNum;
    }
    public void setNumber(String number) 
    {
        this.number = number;
    }

    public String getNumber() 
    {
        return number;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("campusId", getCampusId())
            .append("managerId", getManagerId())
            .append("buildNum", getBuildNum())
            .append("number", getNumber())
            .toString();
    }
}
