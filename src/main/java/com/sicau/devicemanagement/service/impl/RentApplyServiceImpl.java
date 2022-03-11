package com.sicau.devicemanagement.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sicau.devicemanagement.common.constant.Constants;

import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.redis.RedisCache;
import com.sicau.devicemanagement.common.utils.StringUtils;
import com.sicau.devicemanagement.common.utils.bean.BeanUtils;

import com.sicau.devicemanagement.common.utils.file.DateUtils;
import com.sicau.devicemanagement.common.utils.uuid.IdUtils;
import com.sicau.devicemanagement.domain.*;

import com.sicau.devicemanagement.domain.model.ApplyForm;
import com.sicau.devicemanagement.domain.model.LoginUser;

import com.sicau.devicemanagement.domain.model.DeviceUsingSituation;
import com.sicau.devicemanagement.mapper.*;
import com.sicau.devicemanagement.service.IRentApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2022-01-15
 */
@Service
public class RentApplyServiceImpl implements IRentApplyService {
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

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private RedisCache redisCache;


    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public RentApply selectRentApplyById(String id) {
        return rentApplyMapper.selectRentApplyById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param rentApply 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<RentApply> selectRentApplyList(RentApply rentApply) {
        return rentApplyMapper.selectRentApplyList(rentApply);
    }

    /**
     * 新增【借用申请】
     *
     * @param applyForm 【借用申请】
     * @return 结果
     */
    @Override
    public AjaxResult insertRentApply(ApplyForm applyForm, LoginUser loginUser) {
        if (StringUtils.equals(loginUser.getRole(), Constants.ROLE_ADMIN)) {
            return AjaxResult.error("您的身份不能申请，请联系管理员！");
        }
        applyForm = createApplyRecord(applyForm, loginUser);

        synchronized (this) {
            Schedule schedule = new Schedule();
            BeanUtils.copyProperties(applyForm, schedule);
            List<Schedule> schedules = scheduleMapper.selectScheduleList(schedule);
            if (schedules.size() > 0) {
                return AjaxResult.error("该时间段被占用");
            }
            rentApplyMapper.insertRentApply(applyForm);
            return AjaxResult.success("申请成功！管理员审核后即可使用");
        }
    }

    /**
     * 完善 申请表单
     *
     * @param applyForm
     * @param loginUser
     * @return
     */
    private ApplyForm createApplyRecord(ApplyForm applyForm, LoginUser loginUser) {
        //产生临时 scheduleId
        String tempScheduleId = IdUtils.simpleUUID();
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(applyForm, schedule);
        // 将schedule缓存进redis 审核流程完成后从缓存中存入数据库
        redisCache.setCacheObject(Constants.TEMP_SCHEDULE_ID + tempScheduleId, schedule);
        applyForm.setId(IdUtils.simpleUUID());
        applyForm.setScheduleId(tempScheduleId);
        applyForm.setId(IdUtils.simpleUUID());
        applyForm.setCreateTime(DateUtils.getTime());
        applyForm.setDeviceStatus("审核中");
        applyForm.setAuditStatus(0);
        applyForm.setApplicantsId(loginUser.getUserId());
        applyForm.setApplicantsType(loginUser.getRole());
        if (StringUtils.equals(loginUser.getRole(), Constants.ROLE_STUDENT)) {
            // 查询学生老师的id
            Student student =
                    studentMapper
                            .selectById(loginUser.getUserId());
            applyForm.setInstructorId(student.getTeacherId());
            // 查询设备拥有者的id
            Device device = deviceMapper.selectById(applyForm.getDeviceId());
            applyForm.setOwnerId(device.getManagerId());
            // 查询管理员id
            List<Teacher> superAdmins =
                    teacherMapper.selectList(new QueryWrapper<Teacher>().
                            eq("role_id", 4));
            applyForm.setAdministratorId(superAdmins.get(0).getUid());

        } else if (StringUtils.equals(loginUser.getRole(), Constants.ROLE_TEACHER)) {
            //会跳过老师审核的阶段
            applyForm.setAuditStatus(1);
            // 查询设备拥有者id
            Device device =
                    deviceMapper.selectById(applyForm.getDeviceId());
            applyForm.setOwnerId(device.getManagerId());
            // 判断是否是自己的设备
            if (StringUtils.equals(device.getManagerId(), loginUser.getUserId())) {
                applyForm.setOwnerPass(1);
                //是自己的设备 不需要自己审核
                applyForm.setAuditStatus(2);
            }
            //查询管理员id
            List<Teacher> superAdmins = teacherMapper.selectList(new QueryWrapper<Teacher>().
                    eq("role_id", 4));
            applyForm.setAdministratorId(superAdmins.get(0).getUid());
        } else if (StringUtils.equals(loginUser.getRole(), Constants.ROLE_SUPER_ADMIN)) {
            // 不需要老师 和 管理员审核
            applyForm.setAuditStatus(2);
            applyForm.setAdministratorPass(1);
            // 查询设备拥有者id
            Device device = deviceMapper.selectById(applyForm.getDeviceId());
            // 设置管理员id
            applyForm.setOwnerId(device.getManagerId());
        }
        return applyForm;
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param rentApply 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateRentApply(RentApply rentApply) {
        return rentApplyMapper.updateRentApply(rentApply);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteRentApplyByIds(String[] ids) {
        return rentApplyMapper.deleteRentApplyByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteRentApplyById(String id) {
        return rentApplyMapper.deleteRentApplyById(id);
    }

    /**
     * 老师开始使用设备
     *
     * @param id 申请使用id
     * @author sora
     * @date 2022/01/19
     */
    @Override
    public void teacherStartUsingDevice(String id) {
        // 发送短信提醒开始使用
        QueryWrapper<RentApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<RentApply> rentApplyList = rentApplyMapper.selectList(queryWrapper);
        if (rentApplyList == null || rentApplyList.isEmpty()) {
            return;
        }
        RentApply rentApply = rentApplyList.get(0);
        String deviceName = deviceMapper.selectDeviceById(rentApply.getDeviceId()).getName();
        String tel = teacherMapper.selectTeacherByUid(rentApply.getApplicantsId()).getTel();
        smsService.sendStartSms(deviceName, tel);
        // 修改申请状态
        rentApplyMapper.updateStatus(id, Constants.DEVICE_USING);
    }

    /**
     * 学生开始使用设备
     *
     * @param id 申请使用id
     * @author sora
     * @date 2022/01/19
     */
    @Override
    public void studentStartUsingDevice(String id) {
        // 发送短信提醒开始使用
        QueryWrapper<RentApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<RentApply> rentApplyList = rentApplyMapper.selectList(queryWrapper);
        if (rentApplyList == null || rentApplyList.isEmpty()) {
            return;
        }
        RentApply rentApply = rentApplyList.get(0);
        String deviceName = deviceMapper.selectDeviceById(rentApply.getDeviceId()).getName();
        String tel = studentMapper.selectStudentByUid(rentApply.getApplicantsId()).getTel();
        smsService.sendStartSms(deviceName, tel);
        rentApplyMapper.updateStatus(id, Constants.DEVICE_USING);
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
            rentApplyMapper.updateStatus(id, Constants.DEVICE_RETURNED);
        } else {
            rentApplyMapper.updateStatus(id, Constants.DEVICE_RETURNING);
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
        rentApplyMapper.updateStatus(id, Constants.DEVICE_RETURNED);
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
            String reason = "您申请借用的设备已损坏，暂无其它可用设备";
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
            /* 如果设备大于等于两个，将相同时间段借用该时间段设备的人按借用时间进行先后排序，拒绝排名在设备数之后的申请*/
            // 首先找到后面的所有申请，然后进行遍历
            List<RentApply> applyList = rentApplyMapper.selectAfterNow(id);
            HashMap<String, Integer> map = new HashMap<>();
            for (RentApply tmp : applyList) {
                int num = map.getOrDefault(tmp.getScheduleId(), 0);
                map.put(tmp.getScheduleId(), num + 1);
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
                        Date date = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, tmp.getCreateTime());
                        if (date.after(latestTime)) {
                            latest = tmp;
                            latestTime = date;
                        }
                    }
                    if (latest.getApplicantsType().equals(Constants.ROLE_STUDENT)) {
                        stu.add(latest.getApplicantsId());
                    } else if (latest.getApplicantsType().equals(Constants.ROLE_TEACHER)) {
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
                if (tmp.getApplicantsType().equals(Constants.ROLE_STUDENT)) {
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
        return uid.equals(ownerId);
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


    /**
     * 获取需要老师审核的申请
     *
     * @return
     */
    @Override
    public AjaxResult queryApplyCheckedByTeacher(LoginUser loginUser) {

        //查询学生的申请
        List<RentApply> rentApplies = rentApplyMapper.selectApplyByStudent(loginUser.getUserId());
        // 查询需要借用自己设备的申请
        List<RentApply> rentApplies1 = rentApplyMapper.selectApplyBorrowDevice(loginUser.getUserId());

        AjaxResult result = new AjaxResult();
        result.put("msg", "查询成功");
        result.put("xsdApply", rentApplies);
        result.put("jysbApply", rentApplies1);
        return result;
    }

    /**
     * 获取需要superAdmin 省核的申请
     *
     * @return
     */
    @Override
    public AjaxResult queryApplyCheckedBySuperAdmin(String userId) {
        // 查询已经被老师审核过的申请
        List<RentApply> applies = rentApplyMapper.selectNeedCheckedBySuperAdmin(userId);

        return AjaxResult.success("查询成功", applies);
    }

    /**
     * 判断用户是否可以结束使用设备
     *
     * @param uid uid
     * @param id  id
     * @return boolean
     * @author sora
     * @date 2022/02/16
     */
    @Override
    public boolean isUserFinishDevice(String uid, String id) {
        QueryWrapper<RentApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id).eq("applicants_id", uid).select("device_status");
        List<RentApply> rentApplyList = rentApplyMapper.selectList(queryWrapper);
        if (rentApplyList == null || rentApplyList.isEmpty()) {
            return false;
        }
        RentApply rentApply = rentApplyList.get(0);
        return rentApply.getDeviceStatus().equals(Constants.DEVICE_USING);
    }

    @Override
    public List<RentApply> getReturnApply(String uid, int size, int page) {
        QueryWrapper<RentApply> queryWrapper = new QueryWrapper<>();
        int offset = size * (page - 1);
        queryWrapper.last("limit "+offset+", " + size);
        queryWrapper.eq("owner_id", uid).eq("device_status", Constants.DEVICE_RETURNING);
        return rentApplyMapper.selectList(queryWrapper);
    }

    /**
     * 处理请求
     *
     * @param rid       请求记录的id
     * @param res       处理的结果 true or false
     * @param reason    拒绝的原因 可为空
     * @param loginUser 用户信息
     * @return
     */
    // TODO 接口校验问题
    @Override
    public AjaxResult handleApply(String rid, Integer res, String reason, LoginUser loginUser) {
        String role = loginUser.getRole();
        //先查出原来的数据
        RentApply updatedApply = rentApplyMapper.selectRentApplyById(rid);
        String instructorId = updatedApply.getInstructorId();
        String administratorId = updatedApply.getAdministratorId();
        String ownerId = updatedApply.getOwnerId();
        String userId = loginUser.getUserId();
        // 判断是否有资格审核
        if (!userId.equals(instructorId) &&
                !userId.equals(administratorId) &&
                !userId.equals(ownerId)){
            return AjaxResult.error("你没有资格审核该申请");
        }
        // 拒绝
        if (res == 0) {
                updatedApply.setRefuseReason(reason);
                updatedApply.setRefuserId(loginUser.getUserId());
                updatedApply.setFailed(true);
                updatedApply.setDeviceStatus(Constants.DEVICE_APPLY_FAIL);
                rentApplyMapper.updateRentApply(updatedApply);
                return AjaxResult.success();
            }

        // 审核者是老师
        if (StringUtils.equals(role, Constants.ROLE_TEACHER)) {
            // 申请者是学生 且没处理过请求
            if (StringUtils.equals(updatedApply.getApplicantsType(), Constants.ROLE_STUDENT)
                    && updatedApply.getInstructorPass() == null) {
                updatedApply.setInstructorPass(res);
                // 更新阶段
                Integer auditStatus = updatedApply.getAuditStatus();
                updatedApply.setAuditStatus(++auditStatus);
                // 通过时间
                updatedApply.setInstructorPassTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis()));

            }
            // 审核使用自己的设备
            if(ownerId.equals(userId)){
                updatedApply.setOwnerPass(res);
                Integer auditStatus = updatedApply.getAuditStatus();
                updatedApply.setAuditStatus(++auditStatus);
                updatedApply.setOwnerPassTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis()));
            }
            // 审核者是 超级管理员
        } else if (StringUtils.equals(role, Constants.ROLE_SUPER_ADMIN)) {
            updatedApply.setAdministratorPass(res);
            // 设置通过时间
            updatedApply.setAdministratorPassTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis()));
            Integer auditStatus = updatedApply.getAuditStatus();
            updatedApply.setAuditStatus(++auditStatus);
        }
        // 更改申请的状态
        if (updatedApply.getAuditStatus() >= 3) {
            updatedApply.setDeviceStatus(Constants.DEVICE_APPLY_SUCCESS);
            String scheduleId = updatedApply.getScheduleId();
            Schedule sc = (Schedule) redisCache.getCacheObject(Constants.TEMP_SCHEDULE_ID + scheduleId);
            // 通过后借用时间段保存
            sc.setId(scheduleId);
            scheduleMapper.insert(sc);
            redisCache.deleteObject(Constants.TEMP_SCHEDULE_ID + scheduleId);
        }
        rentApplyMapper.updateRentApply(updatedApply);
        return AjaxResult.success();
    }
}
