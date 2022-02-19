package com.sicau.devicemanagement.service.impl;

import java.util.LinkedList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sicau.devicemanagement.common.constant.Constants;

import com.sicau.devicemanagement.common.utils.uuid.IdUtils;
import com.sicau.devicemanagement.domain.Device;
import com.sicau.devicemanagement.domain.DeviceType;

import com.sicau.devicemanagement.domain.model.DeviceUsingSituation;

import com.sicau.devicemanagement.domain.RentApply;
import com.sicau.devicemanagement.mapper.DeviceMapper;
import com.sicau.devicemanagement.mapper.DeviceTypeMapper;
import com.sicau.devicemanagement.mapper.RentApplyMapper;
import com.sicau.devicemanagement.service.IDeviceService;
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
public class DeviceServiceImpl implements IDeviceService
{
    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceTypeMapper deviceTypeMapper;

    @Autowired
    private RentApplyMapper rentApplyMapper;

    @Autowired
    private SmsService smsService;

    @Autowired
    private IDeviceTypeService deviceTypeService;

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
    public Device replaceDevice(String uid, String id) {
        // 看还有没有剩余的设备
        QueryWrapper<RentApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<RentApply> rentApplyList = rentApplyMapper.selectList(queryWrapper);
        if (rentApplyList == null || rentApplyList.isEmpty()) {
            return null;
        }
        RentApply rentApply = rentApplyList.get(0);
//        String deviceId = rentApply.getDeviceId();
        String type = rentApply.getDeviceTypeId();
        return deviceMapper.selectSameTypeSpareDevice(type, id);
//        if (deviceList.size() <= 1) {
//            return null;
//        } else {
//            // 选择除掉该设备的另一个设备
//            Device tmp = null;
//            for (int i = 0; i < deviceList.size(); i++) {
//                tmp = deviceList.get(0);
//                if (!tmp.getId().equals(deviceId)) {
//                    break;
//                }
//            }
//            // 更新rentapply
//            UpdateWrapper<RentApply> rentApplyUpdateWrapper = new UpdateWrapper<>();
//            rentApplyUpdateWrapper.set("device_id", tmp.getId());
//            rentApplyMapper.update(null, rentApplyUpdateWrapper);
//            return tmp;
//        }
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

    @Override
    public int addDevice(Device device) {
        List<Device> list = new LinkedList<>();
        list.add(device);
        return addDevice(list);
    }

    /**
     * 批量上传同类型设备
     *
     * @param list           列表
     * @return int
     * @author sora
     * @date 2022/02/08
     */
    @Override
    public int addDevice(List<Device> list) {
        // 首先判断上传的设备是不是同类型的，同时判断库中是否存在该类型，如果不存在新建类型
        String type = list.get(0).getTypeId();
        if (deviceTypeService.selectDeviceTypeById(type) == null) {
            return -1;
        }
        for (Device tmp : list) {
            if (!type.equals(tmp.getTypeId())) {
                return 0;
            }
        }
        int count = 0;
        for (Device tmp : list) {
            tmp.setId(IdUtils.simpleUUID());
            tmp.setStatus(Constants.DEVICE_NATURAL);
            if (deviceMapper.insertDevice(tmp) > 0) {
                count++;
            }
        }
        // 修改该类设备的库存
        DeviceType deviceType = deviceTypeMapper.selectDeviceTypeById(type);
        deviceType.setTotal(deviceType.getTotal() + list.size());
        deviceType.setInventory(deviceType.getInventory() + list.size());
        deviceTypeMapper.updateDeviceType(deviceType);
        return count;
    }
}
