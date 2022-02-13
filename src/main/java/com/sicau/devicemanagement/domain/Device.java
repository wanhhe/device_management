package com.sicau.devicemanagement.domain;

import com.sicau.devicemanagement.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 【请填写功能名称】对象 device
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public class Device
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 设备型号id */
    @Excel(name = "设备型号id")
    private String typeId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String name;

    /** 设备管理者id */
    @Excel(name = "设备管理者id")
    private String managerId;

    /** 设备价格 */
    @Excel(name = "设备价格")
    private Integer price;

    /* ==== 不对应数据库字段 ==== */
    // 上限
    private Integer priceU;
    // 下限
    private Integer priceD;
    /* ==== 不对应数据库字段 ==== */

    /** 设备状态 */
    @Excel(name = "设备状态")
    private String status;

    /** 使用次数 */
    @Excel(name = "使用次数")
    private Long useTimes;

    /** 所属专业 */
    @Excel(name = "所属专业")
    private String belongMajor;

    /** 所属实验室 */
    @Excel(name = "所属实验室")
    private String belongLabId;

    /** 是否逻辑删除，0为删除 */
    @Excel(name = "是否逻辑删除，0为删除")
    private Integer isDel = 1;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setTypeId(String typeId) 
    {
        this.typeId = typeId;
    }

    public String getTypeId() 
    {
        return typeId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setManagerId(String managerId) 
    {
        this.managerId = managerId;
    }

    public String getManagerId() 
    {
        return managerId;
    }
    public void setPrice(Integer price) 
    {
        this.price = price;
    }

    public Integer getPrice() 
    {
        return price;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setUseTimes(Long useTimes) 
    {
        this.useTimes = useTimes;
    }

    public Long getUseTimes() 
    {
        return useTimes;
    }
    public void setBelongMajor(String belongMajor) 
    {
        this.belongMajor = belongMajor;
    }

    public String getBelongMajor() 
    {
        return belongMajor;
    }
    public void setBelongLabId(String belongLabId) 
    {
        this.belongLabId = belongLabId;
    }

    public String getBelongLabId() 
    {
        return belongLabId;
    }
    public void setIsDel(Integer isDel) 
    {
        this.isDel = isDel;
    }

    public Integer getIsDel() 
    {
        return isDel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("typeId", getTypeId())
            .append("name", getName())
            .append("managerId", getManagerId())
            .append("price", getPrice())
            .append("status", getStatus())
            .append("useTimes", getUseTimes())
            .append("belongMajor", getBelongMajor())
            .append("belongLabId", getBelongLabId())
            .append("isDel", getIsDel())
            .toString();
    }
}
