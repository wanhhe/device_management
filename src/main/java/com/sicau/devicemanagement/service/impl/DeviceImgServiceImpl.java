package com.sicau.devicemanagement.service.impl;

import java.util.List;

import com.sicau.devicemanagement.domain.DeviceImg;
import com.sicau.devicemanagement.mapper.DeviceImgMapper;
import com.sicau.devicemanagement.service.IDeviceImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@Service
public class DeviceImgServiceImpl implements IDeviceImgService
{
    @Autowired
    private DeviceImgMapper deviceImgMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public DeviceImg selectDeviceImgById(String id)
    {
        return deviceImgMapper.selectDeviceImgById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param deviceImg 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DeviceImg> selectDeviceImgList(DeviceImg deviceImg)
    {
        return deviceImgMapper.selectDeviceImgList(deviceImg);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param deviceImg 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDeviceImg(DeviceImg deviceImg)
    {
        return deviceImgMapper.insertDeviceImg(deviceImg);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param deviceImg 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDeviceImg(DeviceImg deviceImg)
    {
        return deviceImgMapper.updateDeviceImg(deviceImg);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDeviceImgByIds(String[] ids)
    {
        return deviceImgMapper.deleteDeviceImgByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDeviceImgById(String id)
    {
        return deviceImgMapper.deleteDeviceImgById(id);
    }
}
