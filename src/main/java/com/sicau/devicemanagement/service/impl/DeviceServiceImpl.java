package com.sicau.devicemanagement.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.domain.Device;
import com.sicau.devicemanagement.domain.DeviceType;
import com.sicau.devicemanagement.domain.RentApply;
import com.sicau.devicemanagement.domain.model.DeviceUsingSituation;
import com.sicau.devicemanagement.mapper.DeviceMapper;
import com.sicau.devicemanagement.mapper.DeviceTypeMapper;
import com.sicau.devicemanagement.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@Service
public class DeviceServiceImpl implements IDeviceService
{
    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceTypeMapper deviceTypeMapper;

    @Autowired
    private SmsService smsService;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Device selectDeviceById(String id)
    {
        return deviceMapper.selectDeviceById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param device 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Device> selectDeviceList(Device device)
    {
        return deviceMapper.selectDeviceList(device);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param device 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDevice(Device device)
    {
        return deviceMapper.insertDevice(device);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param device 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDevice(Device device)
    {
        return deviceMapper.updateDevice(device);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDeviceByIds(String[] ids)
    {
        return deviceMapper.deleteDeviceByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDeviceById(String id)
    {
        return deviceMapper.deleteDeviceById(id);
    }

    @Override
    public DeviceUsingSituation queryDeviceTotalStatus() {
        int total = deviceMapper.countTotalDeviceNumber();
        int using = deviceMapper.countDeviceByStatus(DeviceUsingSituation.DevcieRentStatus.DEVICE_USING.status());
        int overtime = deviceMapper.countDeviceByStatus(DeviceUsingSituation.DevcieRentStatus.DEVICE_OVERTIME.status());
        DeviceUsingSituation deviceUsingSituation = new DeviceUsingSituation();
        deviceUsingSituation.setTotal(total);
        deviceUsingSituation.setUsing(using);
        deviceUsingSituation.setOvertime(overtime);
        return deviceUsingSituation;
    }

    /**
     * 是否是设备所有者
     *
     * @param uid uid
     * @param id  id
     * @return boolean
     * @author sora
     * @date 2022/01/19
     */
    @Override
    public boolean isDeviceOwner(String uid, String id) {
        String managerId = deviceMapper.selectDeviceById(id).getManagerId();
        return uid.equals(managerId);
    }

    @Override
    public void replaceDevice(String uid, String id) {

    }

    @Override
    public void updateDeviceStatus(String id, String status) {
        UpdateWrapper<Device> deviceUpdateWrapper = new UpdateWrapper<>();
        deviceUpdateWrapper.set("status", status).eq("id", id);
        deviceMapper.update(null, deviceUpdateWrapper);
    }

    @Override
    public void deviceBroken(String id) {
        Device device = deviceMapper.selectDeviceById(id);
        UpdateWrapper<Device> deviceUpdateWrapper = new UpdateWrapper<>();
        deviceUpdateWrapper.set("status", Constants.DEVICE_BROKEN)
                .set("is_del", 0).eq("id", id);
        deviceMapper.update(device, deviceUpdateWrapper);
        // 更改type状态
        DeviceType deviceType = deviceTypeMapper.selectDeviceTypeById(device.getTypeId());
        deviceType.setInventory(deviceType.getInventory()-1);
        deviceType.setTotal(deviceType.getTotal()-1);
        deviceTypeMapper.updateDeviceType(deviceType);
    }
}
