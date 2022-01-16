package com.sicau.devicemanagement.domain;

import com.sicau.devicemanagement.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 【请填写功能名称】对象 device_img
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public class DeviceImg
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 设备类型id */
    @Excel(name = "设备类型id")
    private String deviceTypeId;

    /** 图片路径 */
    @Excel(name = "图片路径")
    private String url;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setDeviceTypeId(String deviceTypeId) 
    {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceTypeId() 
    {
        return deviceTypeId;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deviceTypeId", getDeviceTypeId())
            .append("url", getUrl())
            .toString();
    }
}
