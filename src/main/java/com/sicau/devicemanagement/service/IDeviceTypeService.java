package com.sicau.devicemanagement.service;


import com.sicau.devicemanagement.domain.DeviceType;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public interface IDeviceTypeService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public DeviceType selectDeviceTypeById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param deviceType 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DeviceType> selectDeviceTypeList(DeviceType deviceType);

    /**
     * 新增【请填写功能名称】
     * 
     * @param deviceType 【请填写功能名称】
     * @return 结果
     */
    public int insertDeviceType(DeviceType deviceType);

    /**
     * 修改【请填写功能名称】
     * 
     * @param deviceType 【请填写功能名称】
     * @return 结果
     */
    public int updateDeviceType(DeviceType deviceType);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteDeviceTypeByIds(String[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDeviceTypeById(String id);
}
