package com.sicau.devicemanagement.service;

import com.sicau.devicemanagement.domain.DeviceImg;

import java.util.List;


/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public interface IDeviceImgService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public DeviceImg selectDeviceImgById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param deviceImg 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DeviceImg> selectDeviceImgList(DeviceImg deviceImg);

    /**
     * 新增【请填写功能名称】
     * 
     * @param deviceImg 【请填写功能名称】
     * @return 结果
     */
    public int insertDeviceImg(DeviceImg deviceImg);

    /**
     * 修改【请填写功能名称】
     * 
     * @param deviceImg 【请填写功能名称】
     * @return 结果
     */
    public int updateDeviceImg(DeviceImg deviceImg);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteDeviceImgByIds(String[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDeviceImgById(String id);

    /**
     * 删除类型为type的所有设备
     *
     * @param type 类型
     * @return int
     * @author sora
     * @date 2022/02/08
     */
    public int deleteDeviceImgByType(String type);
}
