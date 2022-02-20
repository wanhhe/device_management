package com.sicau.devicemanagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sicau.devicemanagement.domain.DeviceType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@Mapper
public interface DeviceTypeMapper extends BaseMapper<DeviceType>
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDeviceTypeById(String id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceTypeByIds(String[] ids);
}
