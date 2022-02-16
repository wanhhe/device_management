package com.sicau.devicemanagement.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.utils.StringUtils;
import com.sicau.devicemanagement.common.utils.file.DateUtils;
import com.sicau.devicemanagement.domain.*;
import com.sicau.devicemanagement.mapper.*;
import com.sicau.devicemanagement.service.IRentApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


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
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

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
    public void teacherStartUsingDevice(String id) {
        // 发送短信提醒开始使用
        QueryWrapper<RentApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        RentApply rentApply = rentApplyMapper.selectList(queryWrapper).get(0);
        String deviceName = deviceMapper.selectDeviceById(rentApply.getDeviceId()).getName();
        String tel = teacherMapper.selectTeacherByUid(rentApply.getApplicantsId()).getTel();
        smsService.sendStartSms(deviceName, tel);
        // 修改申请状态
        rentApplyMapper.updateStatus(id, "使用中");
    }

    /**
     * 学生开始使用设备
     *
     * @param id  申请使用id
     * @author sora
     * @date 2022/01/19
     */
    @Override
    public void studentStartUsingDevice(String id) {
        // 发送短信提醒开始使用
        QueryWrapper<RentApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        RentApply rentApply = rentApplyMapper.selectList(queryWrapper).get(0);
        String deviceName = deviceMapper.selectDeviceById(rentApply.getDeviceId()).getName();
        String tel = studentMapper.selectStudentByUid(rentApply.getApplicantsId()).getTel();
        smsService.sendStartSms(deviceName, tel);
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
        deviceTypeQueryWrapper.select("inventory").eq("id", deviceTypeId);
        Integer inventory = deviceTypeMapper.selectCount(deviceTypeQueryWrapper);
        if (inventory == 1) {
            /* 确认归还 */
            confirmReturn(id);
            /* 修改设备表中的设备状态 */
            Device device = deviceMapper.selectDeviceById(deviceId);
            device.setStatus("维修中");
            deviceMapper.updateDevice(device);
            /* 修改设备类型表中的设备状态 */
            // 库存减一
            DeviceType deviceType = deviceTypeMapper.selectDeviceTypeById(deviceTypeId);
            deviceType.setInventory(deviceType.getInventory() - 1);
            deviceTypeMapper.updateDeviceType(deviceType);
            /* 通知后来的使用者该设备已损坏，查找该类设备之后的所有申请借用者 */
            String reason = "您申请借用的设备已损坏，现暂无其它可用设备";
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
            // 首先找到后面的所有申请，然后进行遍历
            List<RentApply> applyList = rentApplyMapper.selectAfterNow(id);
            // 将当前审核环节设置为不通过
            refuseNowStage(applyList);
        } else {
            /* 确认归还 */
            confirmReturn(id);
            /* 修改设备表中的设备状态 */
            Device device = deviceMapper.selectDeviceById(deviceId);
            device.setStatus("维修中");
            deviceMapper.updateDevice(device);
            /* 修改设备类型表中的设备状态 */
            // 库存减一
            DeviceType deviceType = deviceTypeMapper.selectDeviceTypeById(deviceTypeId);
            deviceType.setInventory(deviceType.getInventory() - 1);
            deviceTypeMapper.updateDeviceType(deviceType);
            String reason = "您申请借用的设备已损坏，请在设备维修后再次申请";
            /* 如果设备大于等于两个，将相同时间段借用该时间段的人按借用时间进行先后排序，拒绝排名在设备数之后的申请*/
            // 首先找到后面的所有申请，然后进行遍历
            List<RentApply> applyList = rentApplyMapper.selectAfterNow(id);
            HashMap<String, Integer> map = new HashMap<>();
            for (RentApply tmp : applyList) {
                int num = map.getOrDefault(tmp.getScheduleId(), 0);
                map.put(tmp.getScheduleId(), num+1);
            }
            // 遍历整个map看是否有超出的
            for (String key : map.keySet()) {
                int i = map.get(key) - inventory;
                List<RentApply> res = new ArrayList<>();
                List<String> stu = new ArrayList<>();
                List<String> teacher = new ArrayList<>();
                while (i > 0) {
                    RentApply latest = new RentApply();
                    Date latestTime = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, "2000-01-01 00:00:00");
                    for (RentApply tmp : applyList) {
                        Date date = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, tmp.getCreatTime());
                        if (date.after(latestTime)) {
                            latest = tmp;
                            latestTime = date;
                        }
                    }
                    if (latest.getApplicantsType().equals(Constants.STUDENT)) {
                        stu.add(latest.getApplicantsId());
                    } else if (latest.getApplicantsType().equals(Constants.TEACHER)) {
                        teacher.add(latest.getApplicantsId());
                    }
                    // 找出了最小的
                    res.add(latest);
                    applyList.remove(latest);
                    i--;
                }
                QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
                QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
                if (!stu.isEmpty()) {
                    for (String str : stu) {
                        studentQueryWrapper.select("tel").eq("uid", str);
                        List<Student> students = studentMapper.selectList(studentQueryWrapper);
                        smsService.sendRejectApply(students.get(0).getTel(), reason);
                    }
                }
                if (!teacher.isEmpty()) {
                    for (String str : teacher) {
                        teacherQueryWrapper.select("tel").eq("uid", str);
                        List<Teacher> teachers = teacherMapper.selectList(teacherQueryWrapper);
                        smsService.sendRejectApply(teachers.get(0).getTel(), reason);
                    }
                }
                refuseNowStage(res);
            }
        }
    }

    private void refuseNowStage(List<RentApply> list) {
        for (RentApply tmp : list) {
            // 查看已审核到哪个阶段
            int status = tmp.getAuditStatus();
            if (status == 1) {
                tmp.setInstructorPass(0);
                tmp.setAdministratorPass(0);
                tmp.setOwnerPass(0);
                // 有三个阶段的必然是学生
                tmp.setRefuserId(studentMapper.selectTeacherId(tmp.getApplicantsId()));
            } else if (status == 2) {
                tmp.setAdministratorPass(0);
                tmp.setOwnerPass(0);
                // 判断是学生还是老师
                if (tmp.getApplicantsType().equals(Constants.STUDENT)) {
                    // TODO: 2022/1/25 审核管理员id
                }
            } else if (status == 3) {
                tmp.setOwnerPass(0);
                QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<>();
                deviceQueryWrapper.select("manager_id").eq("id", tmp.getDeviceId());
            }
            // 补充申请表的拒绝信息
            tmp.setRefuseReason("借用设备已损坏");
            // 落盘
            rentApplyMapper.updateRentApply(tmp);
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
