package com.sicau.devicemanagement.domain;

import com.sicau.devicemanagement.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 【请填写功能名称】对象 device_type
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public class DeviceType
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String name;

    /** 设备总数 */
    @Excel(name = "设备总数")
    private Integer total;

    /** 设备库存 */
    @Excel(name = "设备库存")
    private Integer inventory;

    /** 使用注意事项 */
    @Excel(name = "使用注意事项")
    private String precautions;

    /** 设备简介 */
    @Excel(name = "设备简介")
    private String introduction;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setTotal(Integer total) 
    {
        this.total = total;
    }

    public Integer getTotal() 
    {
        return total;
    }
    public void setInventory(Integer inventory) 
    {
        this.inventory = inventory;
    }

    public Integer getInventory() 
    {
        return inventory;
    }
    public void setPrecautions(String precautions) 
    {
        this.precautions = precautions;
    }

    public String getPrecautions() 
    {
        return precautions;
    }
    public void setIntroduction(String introduction) 
    {
        this.introduction = introduction;
    }

    public String getIntroduction() 
    {
        return introduction;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("total", getTotal())
            .append("inventory", getInventory())
            .append("precautions", getPrecautions())
            .append("introduction", getIntroduction())
            .toString();
    }
}
