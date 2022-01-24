package com.sicau.devicemanagement.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sicau.devicemanagement.common.core.model.DeviceUsingSituation;
import com.sicau.devicemanagement.common.utils.StringUtils;
import com.sicau.devicemanagement.common.utils.file.DateUtils;
import com.sicau.devicemanagement.domain.Device;
import com.sicau.devicemanagement.domain.DeviceType;
import com.sicau.devicemanagement.domain.RentApply;
import com.sicau.devicemanagement.mapper.DeviceMapper;
import com.sicau.devicemanagement.mapper.DeviceTypeMapper;
import com.sicau.devicemanagement.mapper.RentApplyMapper;
import com.sicau.devicemanagement.service.IRentApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@Service
public class RentApplyServiceImpl implements IRentApplyService
{
    @Autowired
    private RentApplyMapper rentApplyMapper;

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
    public RentApply selectRentApplyById(String id)
    {
        return rentApplyMapper.selectRentApplyById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param rentApply 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<RentApply> selectRentApplyList(RentApply rentApply)
    {
        return rentApplyMapper.selectRentApplyList(rentApply);
    }

    /**
     * 新增【借用申请】
     * 
     * @param rentApply 【借用申请】
     * @return 结果
     */
    @Override
    public int insertRentApply(RentApply rentApply)
    {
        rentApply.setCreateTime(DateUtils.getNowDate());
        return rentApplyMapper.insertRentApply(rentApply);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param rentApply 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateRentApply(RentApply rentApply)
    {
        return rentApplyMapper.updateRentApply(rentApply);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteRentApplyByIds(String[] ids)
    {
        return rentApplyMapper.deleteRentApplyByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteRentApplyById(String id)
    {
        return rentApplyMapper.deleteRentApplyById(id);
    }

    /**
     * 老师开始使用设备
     *
     * @param uid uid
     * @param id  申请使用id
     * @author sora
     * @date 2022/01/19
     */
    @Override
    public void teacherStartUsingDevice(String uid, String id) {
        // 发送短信提醒开始使用
        smsService.sendStartSms(uid);
        // 修改申请状态
        rentApplyMapper.updateStatus(id, "使用中");
    }

    /**
     * 学生开始使用设备
     *
     * @param uid uid
     * @param id  申请使用id
     * @author sora
     * @date 2022/01/19
     */
    @Override
    public void studentStartUsingDevice(String uid, String id) {
        smsService.sendStartSms(uid);
        rentApplyMapper.updateStatus(id, "使用中");
    }

    /**
     * 使用完成，申请归还
     *
     * @param uid uid
     * @param id  申请使用id
     * @author sora
     * @date 2022/01/21
     */
    @Override
    public void finishUse(String uid, String id) {
        // 判断该用户是不是该设备的管理者，如果是就不用管理老师确认
        String ownerId = rentApplyMapper.selectOwnerById(id);
        if (uid.equals(ownerId)) {
            rentApplyMapper.updateStatus(id, "已归还");
        } else {
            rentApplyMapper.updateStatus(id, "归还中");
        }
    }

    /**
     * 确认归还
     *
     * @param id id
     * @author sora
     * @date 2022/01/21
     */
    @Override
    public void confirmReturn(String id) {
        rentApplyMapper.updateStatus(id, "已归还");
    }

    /**
     * 设备损坏报修。查询距下一次有人借用该类设备时仓库是否有库存，如果没库存，发送短信通知借用者，进入退换流程
     *
     * @param id id
     * @author sora
     * @date 2022/01/21
     */
    @Override
    public void deviceBroken(String id) {
        /* 首先检查该种设备是不是只有一个，如果只有一个就不存在替换问题 */
        RentApply rentApply = rentApplyMapper.selectById(id);
        String deviceTypeId = rentApply.getDeviceTypeId();
        String deviceId = rentApply.getDeviceId();
        QueryWrapper<DeviceType> deviceTypeQueryWrapper = new QueryWrapper<>();
        deviceTypeQueryWrapper.select("total").eq("id", deviceTypeId);
        Integer count = deviceTypeMapper.selectCount(deviceTypeQueryWrapper);
        if (count == 1) {
            /* 确认归还 */
            confirmReturn(id);
            /* 修改设备表中的设备状态 */
            UpdateWrapper<Device> deviceUpdateWrapper = new UpdateWrapper<>();
            deviceUpdateWrapper.set("status", "维修中").eq("id", deviceId);
            /* 通知后来的使用者该设备已损坏，查找该类设备之后的所有申请借用者 */
            String reason = "您申请借用的设备已损坏";
            // 查找老师
            List<String> teacherList = rentApplyMapper.selectNowApplyTeacherTelByDeviceTypeId(deviceTypeId, DateUtils.getTime());
            // 查找学生
            List<String> studentList = rentApplyMapper.selectNowApplyStudentTelByDeviceTypeId(deviceTypeId, DateUtils.getTime());
            // 发送短信通知
            for (String applicant : teacherList) {
                smsService.sendRejectApply(applicant, reason);
            }
            for (String applicant : studentList) {
                smsService.sendRejectApply(applicant, reason);
            }
            /* 取消后面的申请 */
            // 将当前审核环节设置为不通过

            // 补充申请表的拒绝信息
        } else {
            /* 如果设备大于两个， */
        }
    }

    /**
     * 是否是借用设备的管理者
     *
     * @param uid uid
     * @param id  id
     * @return boolean
     * @author sora
     * @date 2022/01/21
     */
    @Override
    public boolean isDeviceOwner(String uid, String id) {
        String ownerId = rentApplyMapper.selectOwnerById(id);
        if (uid.equals(ownerId)) {
            return true;
        }
        return false;
    }

    /**
     * 判断该时间段用户是否能够使用该设备
     *
     * @param uid uid
     * @param id  申请使用id
     * @return boolean
     * @author sora
     * @date 2022/01/19
     */
    @Override
    public boolean isUserAccessDevice(String uid, String id) {
        String now = DateUtils.getTime();
        List<String> list = rentApplyMapper.selectUserAccessApply(uid, now);
        for (String access : list) {
            if (id.equals(access)) {
                return true;
            }
        }
        return false;
    }
}
