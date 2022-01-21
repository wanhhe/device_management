package com.sicau.devicemanagement.mapper;

import com.sicau.devicemanagement.common.core.model.DeviceUsingSituation;
import com.sicau.devicemanagement.domain.Device;

import java.util.List;


/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public interface DeviceMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Device selectDeviceById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param device 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Device> selectDeviceList(Device device);

    /**
     * 统计设备总数
     *
     * @return int
     * @author sora
     * @date 2022/01/18
     */
    public int countTotalDeviceNumber();

    /**
     * 根据设备状态统计数量
     *
     * @param status 状态
     * @return int
     * @author sora
     * @date 2022/01/18
     */
    public int countDeviceByStatus(String status);

    /**
     * 新增【请填写功能名称】
     * 
     * @param device 【请填写功能名称】
     * @return 结果
     */
    public int insertDevice(Device device);

    /**
     * 修改【请填写功能名称】
     * 
     * @param device 【请填写功能名称】
     * @return 结果
     */
    public int updateDevice(Device device);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDeviceById(String id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceByIds(String[] ids);
}
