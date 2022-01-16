package com.sicau.devicemanagement.mapper;

import com.sicau.devicemanagement.domain.DeviceImg;

import java.util.List;


/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public interface DeviceImgMapper 
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDeviceImgById(String id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceImgByIds(String[] ids);
}
