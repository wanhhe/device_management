package com.sicau.devicemanagement.service.impl;

import java.util.List;

import com.sicau.devicemanagement.domain.DeviceType;
import com.sicau.devicemanagement.mapper.DeviceTypeMapper;
import com.sicau.devicemanagement.service.IDeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@Service
public class DeviceTypeServiceImpl implements IDeviceTypeService
{
    @Autowired
    private DeviceTypeMapper deviceTypeMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public DeviceType selectDeviceTypeById(String id)
    {
        return deviceTypeMapper.selectDeviceTypeById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param deviceType 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DeviceType> selectDeviceTypeList(DeviceType deviceType)
    {
        return deviceTypeMapper.selectDeviceTypeList(deviceType);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param deviceType 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDeviceType(DeviceType deviceType)
    {
        return deviceTypeMapper.insertDeviceType(deviceType);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param deviceType 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDeviceType(DeviceType deviceType)
    {
        return deviceTypeMapper.updateDeviceType(deviceType);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDeviceTypeByIds(String[] ids)
    {
        return deviceTypeMapper.deleteDeviceTypeByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDeviceTypeById(String id)
    {
        return deviceTypeMapper.deleteDeviceTypeById(id);
    }
}
