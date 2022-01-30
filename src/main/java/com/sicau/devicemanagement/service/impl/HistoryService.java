package com.sicau.devicemanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.domain.RentApply;
import com.sicau.devicemanagement.domain.model.BorrowHistory;
import com.sicau.devicemanagement.domain.model.DeviceUsingSituation;
import com.sicau.devicemanagement.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Collator;
import java.util.*;

@Component
public class HistoryService {

    @Autowired
    RentApplyMapper rentApplyMapper;

    @Autowired
    DeviceMapper deviceMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    ScheduleMapper scheduleMapper;

    /**
     * 通过id查找借用历史
     *
     * @param uid uid
     * @return {@link List<BorrowHistory> }
     * @author sora
     * @date 2022/01/27
     */
    public List<BorrowHistory> getBorrowHistoryByUid(String uid) {
        QueryWrapper<RentApply> rentApplyQueryWrapper = new QueryWrapper<>();
        rentApplyQueryWrapper.eq("applicants_id", uid);
        List<RentApply> rentApplies = rentApplyMapper.selectList(rentApplyQueryWrapper);
        List<BorrowHistory> borrowHistories = applyToBorrow(rentApplies);
        advanceUnreturn(borrowHistories);
        return borrowHistories;
    }

//    /**
//     * 管理员通过角色查找借用历史
//     *
//     * @param role 角色
//     * @return {@link List<BorrowHistory> }
//     * @author sora
//     * @date 2022/01/27
//     */
//    public List<BorrowHistory> adminGetBorrowHistoryByRole(String role) {
//
//    }

    /**
     * 管理员通过设备类型id查找借用历史
     *
     * @param typeId 类型
     * @return {@link List<BorrowHistory> }
     * @author sora
     * @date 2022/01/27
     */
    public List<BorrowHistory> adminGetBorrowHistoryByDevice(String typeId) {
        QueryWrapper<RentApply> rentApplyQueryWrapper = new QueryWrapper<>();
        rentApplyQueryWrapper.eq("device_type_id", typeId);
        List<RentApply> rentApplies = rentApplyMapper.selectList(rentApplyQueryWrapper);
        List<BorrowHistory> borrowHistories = applyToBorrow(rentApplies);
        sortNameAsc(borrowHistories);
        advanceUnreturn(borrowHistories);
        return borrowHistories;
    }

    public List<BorrowHistory> adminGetBorrowHistoryByDeviceId(String id) {
        QueryWrapper<RentApply> rentApplyQueryWrapper = new QueryWrapper<>();
        rentApplyQueryWrapper.eq("device_id", id).eq("status", DeviceUsingSituation.DevcieRentStatus.DEVICE_RETURN);
        List<RentApply> rentApplies = rentApplyMapper.selectList(rentApplyQueryWrapper);
        List<BorrowHistory> borrowHistories = applyToBorrow(rentApplies);
        // 按借用时间从新到旧排序
        sortTimeDesc(borrowHistories);
        return borrowHistories;
    }

    /**
     * 老师通过学生姓名查找所属学生借用历史
     *
     * @param uid  uid
     * @param name 的名字
     * @return {@link List<BorrowHistory> }
     * @author sora
     * @date 2022/01/27
     */
    public List<BorrowHistory> getSubBorrowHistoryByName(String uid, String name) {
        List<RentApply> rentApplies = rentApplyMapper.querySubStuApplyByName(uid, name);
        List<BorrowHistory> borrowHistories = applyToBorrow(rentApplies);
        advanceUnreturn(borrowHistories);
        return borrowHistories;
    }

    /**
     * 老师通过设备类型查找所属学生借用历史
     *
     * @param uid    uid
     * @param typeId 类型id
     * @return {@link List<BorrowHistory> }
     * @author sora
     * @date 2022/01/27
     */
    public List<BorrowHistory> getSubBorrowHistoryByDevice(String uid, String typeId) {
        List<RentApply> rentApplies = rentApplyMapper.querySubStuApplyByDevice(uid, typeId);
        List<BorrowHistory> borrowHistories = applyToBorrow(rentApplies);
        // 对设备名进行排序
        sortDeviceAsc(borrowHistories);
        advanceUnreturn(borrowHistories);
        return borrowHistories;
    }

    /**
     * 老师查找自己外借设备的历史
     *
     * @param uid    uid
     * @param typeId 类型id
     * @return {@link List<BorrowHistory> }
     * @author sora
     * @date 2022/01/27
     */
    public List<BorrowHistory> getOwnDeviceBorrowHistory(String uid, String typeId) {
        List<RentApply> rentApplies = rentApplyMapper.queryOwnDeviceHistory(uid, typeId);
        List<BorrowHistory> borrowHistories = applyToBorrow(rentApplies);
        advanceUnreturn(borrowHistories);
        return borrowHistories;
    }

    /**
     * 把 Rent_Apply类转化为 BorrowHistory类
     *
     * @param list 列表
     * @return {@link List<BorrowHistory> }
     * @author sora
     * @date 2022/01/27
     */
    private List<BorrowHistory> applyToBorrow(List<RentApply> list) {
        List<BorrowHistory> res = new LinkedList<>();
        for (RentApply temp : list) {
            BorrowHistory borrowHistory = new BorrowHistory();
            borrowHistory.setId(temp.getId());
            borrowHistory.setDevice(deviceMapper.selectDeviceById(temp.getDeviceId()));
            if (temp.getApplicantsType().equals(Constants.TEACHER)) {
                borrowHistory.setTeacher(teacherMapper.selectTeacherByUid(temp.getApplicantsId()));
            } else if (temp.getApplicantsType().equals(Constants.STUDENT)) {
                borrowHistory.setStudent(studentMapper.selectStudentByUid(temp.getApplicantsId()));
            }
            borrowHistory.setDeviceStatus(temp.getDeviceStatus());
            borrowHistory.setSchedule(scheduleMapper.selectScheduleById(temp.getScheduleId()));
            borrowHistory.setCreatTime(temp.getCreatTime());
            borrowHistory.setRefuseReason(temp.getRefuseReason());
            borrowHistory.setRefuser(teacherMapper.selectTeacherByUid(temp.getRefuserId()));
            borrowHistory.setInstructorPass(temp.getInstructorPass());
            borrowHistory.setAdministratorPass(temp.getAdministratorPass());
            borrowHistory.setOwnerPass(temp.getOwnerPass());
            res.add(borrowHistory);
        }
        return res;
    }

    /**
     * 将未归还的历史提前，保证相对顺序不改变
     *
     * @param list  列表
     * @author sora
     * @date 2022/01/27
     */
    private void advanceUnreturn(List<BorrowHistory> list) {
        list.sort((o1, o2) -> {
            if (o2.getDeviceStatus().equals(DeviceUsingSituation.DevcieRentStatus.DEVICE_OVERTIME.status())
                    && o1.getDeviceStatus().equals(DeviceUsingSituation.DevcieRentStatus.DEVICE_RETURN.status())) {
                return -1;
            }
            return 0;
        });
    }

    /**
     * 按姓名首字母升序排序
     *
     * @param list 列表
     * @author sora
     * @date 2022/01/27
     */
    private void sortNameAsc(List<BorrowHistory> list) {
        Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
        list.sort(com);
    }

    /**
     * 按设备名升序排列
     *
     * @param list 列表
     * @author sora
     * @date 2022/01/27
     */
    private void sortDeviceAsc(List<BorrowHistory> list) {
        list.sort((o1, o2) -> o2.getDevice().getName().charAt(0) - o1.getDevice().getName().charAt(0));
    }

    /**
     * 按借用时间从新到旧排序
     *
     * @param list 列表
     * @author sora
     * @date 2022/01/27
     */
    private void sortTimeDesc(List<BorrowHistory> list) {
        list.sort((o1, o2) -> {
            if (o1.getSchedule().getBeginTime().after(o2.getSchedule().getBeginTime())) {
                return 1;
            }
            return 0;
        });
    }
}
