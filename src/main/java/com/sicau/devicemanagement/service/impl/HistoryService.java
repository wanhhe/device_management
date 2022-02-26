package com.sicau.devicemanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.domain.Device;
import com.sicau.devicemanagement.domain.DeviceType;
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

    private final static String CREATE_TIME = "create";

    private final static String FINISH_TIME = "finish";

    private final static String USING_TIME = "use";

    private final static String ASC = "asc";

    private final static String DESC = "desc";

    /**
     * 通过id查找借用历史
     *
     * @param uid uid
     * @return {@link List<BorrowHistory> }
     * @author sora
     * @date 2022/01/27
     */
    public List<BorrowHistory> getBorrowHistoryByUid(String uid, int size, int page) {
        QueryWrapper<RentApply> rentApplyQueryWrapper = new QueryWrapper<>();
        int offset = size * (page-1);
        rentApplyQueryWrapper.eq("applicants_id", uid).last("limit "+offset + ", "+size);
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
    public List<BorrowHistory> adminGetBorrowHistoryByDevice(String typeId, int size, int page) {
        QueryWrapper<RentApply> rentApplyQueryWrapper = new QueryWrapper<>();
        int offset = size * (page - 1);
        rentApplyQueryWrapper.eq("device_type_id", typeId).last("limit "+offset+", "+size);
        List<RentApply> rentApplies = rentApplyMapper.selectList(rentApplyQueryWrapper);
        List<BorrowHistory> borrowHistories = applyToBorrow(rentApplies);
        sortNameAsc(borrowHistories);
        advanceUnreturn(borrowHistories);
        return borrowHistories;
    }

    /**
     * 管理员通过设备id查找借用历史
     *
     * @param id id
     * @return {@link List<BorrowHistory> }
     * @author sora
     * @date 2022/02/16
     */
    public List<BorrowHistory> adminGetBorrowHistoryByDeviceId(String id) {
        QueryWrapper<RentApply> rentApplyQueryWrapper = new QueryWrapper<>();
        rentApplyQueryWrapper.eq("device_id", id);
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
     * @param size   大小
     * @param page   页面
     * @return {@link List<BorrowHistory> }
     * @author sora
     * @date 2022/02/16
     */
    public List<BorrowHistory> getSubBorrowHistoryByDevice(String uid, String typeId, int size, int page) {
        PageHelper.startPage(page, size);
        List<RentApply> rentApplies = rentApplyMapper.querySubStuApplyByDevice(uid, typeId);
        List<BorrowHistory> borrowHistories = applyToBorrow(rentApplies);
        // 对设备名进行排序
        sortDeviceAsc(borrowHistories);
        advanceUnreturn(borrowHistories);
        return borrowHistories;
    }

    /**
     * 获得所有设备的借用历史
     *
     * @param size 大小
     * @param page 页面
     * @return {@link List<BorrowHistory> }
     * @author sora
     * @date 2022/02/26
     */
    public List<BorrowHistory> getAllBorrowHistory(int size, int page) {
        QueryWrapper<RentApply> queryWrapper = new QueryWrapper<>();
        int offset = size * (page - 1);
        queryWrapper.last("limit "+offset+", "+size);
        List<RentApply> rentApplyList = rentApplyMapper.selectList(queryWrapper);
        List<BorrowHistory> borrowHistories = applyToBorrow(rentApplyList);
        // 按设备的先后使用顺序排序
        return borrowHistories;
    }

    /**
     * 老师查找自己外借设备的历史
     *
     * @param uid    uid
     * @param typeId 类型id
     * @param size 数据数
     * @param page 第几页
     * @return {@link List<BorrowHistory> }
     * @author sora
     * @date 2022/02/16
     */
    public List<BorrowHistory> getOwnDeviceBorrowHistory(String uid, String typeId, int size, int page) {
        PageHelper.startPage(page, size);
        List<RentApply> rentApplies = rentApplyMapper.queryOwnDeviceHistory(uid, typeId);
        List<BorrowHistory> borrowHistories = applyToBorrow(rentApplies);
        advanceUnreturn(borrowHistories);
        return borrowHistories;
    }

    /**
     * 根据数量导出该设备的使用情况
     *
     * @param id  id
     * @param size   大小
     * @param page   页面
     * @return {@link List<Device> }
     * @author sora
     * @date 2022/02/10
     */
    public List<RentApply> selectDeviceByNum(String id, int size, int page) {
        QueryWrapper<RentApply> rentApplyQueryWrapper = new QueryWrapper<>();
        rentApplyQueryWrapper.eq("device_id", id);
        int offset = size * (page - 1);
         rentApplyQueryWrapper.last("limit "+offset + ", " + size);
        return rentApplyMapper.selectList(rentApplyQueryWrapper);
    }

    /**
     * 根据时间导出该设备的使用情况
     *
     * @param id    id
     * @param begin 开始日期 yyyy-MM-dd
     * @param end   结束日期
     * @return {@link List<Device> }
     * @author sora
     * @date 2022/02/10
     */
    public List<RentApply> selectDeviceByDay(String id, String begin, String end) {
        QueryWrapper<RentApply> rentApplyQueryWrapper;
        List<String> scheduleIds = scheduleMapper.queryIdBetweenDay(begin, end);
        List<RentApply> res = new LinkedList<>();
        List<RentApply> list;
        RentApply tmp;
        for (String sid : scheduleIds) {
            rentApplyQueryWrapper = new QueryWrapper<>();
            rentApplyQueryWrapper.eq("device_id", id).eq("schedule_id", sid);
            list = rentApplyMapper.selectList(rentApplyQueryWrapper);
            if (list == null || list.isEmpty()) {
                continue;
            }
            tmp = list.get(0);
            if (tmp != null) {
                res.add(tmp);
            }
        }
        return res;
    }

    /**
     * 根据数量导出该设备类型的使用情况
     *
     * @param id   id
     * @param size 大小
     * @param page 页面
     * @return {@link List<DeviceType> }
     * @author sora
     * @date 2022/02/12
     */
    public List<RentApply> selectDeviceTypeByNum(String id, int size, int page) {
        QueryWrapper<RentApply> rentApplyQueryWrapper = new QueryWrapper<>();
        int offset = size * (page - 1);
        rentApplyQueryWrapper.eq("device_type_id", id).last("limit "+offset+", "+size);
        return rentApplyMapper.selectList(rentApplyQueryWrapper);
    }

    /**
     * 根据时间导出该设备类型的使用情况
     *
     * @param id    id
     * @param begin 开始日期 yyyy-MM-dd
     * @param end   结束日期
     * @return {@link List<DeviceType> }
     * @author sora
     * @date 2022/02/10
     */
    public List<RentApply> selectDeviceTypeByTime(String id, String begin, String end) {
        QueryWrapper<RentApply> rentApplyQueryWrapper;
        List<String> scheduleIds = scheduleMapper.queryIdBetweenDay(begin, end);
        List<RentApply> res = new LinkedList<>();
        List<RentApply> list;
        RentApply tmp;
        for (String sid : scheduleIds) {
            rentApplyQueryWrapper = new QueryWrapper<>();
            rentApplyQueryWrapper.eq("device_type_id", id).eq("schedule_id", sid);
            list = rentApplyMapper.selectList(rentApplyQueryWrapper);
            if (list == null || list.isEmpty()) {
                continue;
            }
            tmp = list.get(0);
            if (tmp != null) {
                res.add(tmp);
            }
        }
        return res;
    }

    /**
     * 根据数量导出使用申请
     *
     * @param size   大小
     * @param page   页面
     * @return {@link List<RentApply> }
     * @author sora
     * @date 2022/02/10
     */
    public List<RentApply> selectRentApply(int size, int page) {
        QueryWrapper<RentApply> rentApplyQueryWrapper = new QueryWrapper<>();
        int offset = size * (page - 1);
        rentApplyQueryWrapper.last("limit "+offset+", " + size);
        return rentApplyMapper.selectList(rentApplyQueryWrapper);
    }

    /**
     * 根据时间导出使用申请
     *
     * @param begin  开始日期 yyyy-MM-dd
     * @param end    结束日期
     * @param basis  依据
     * @param method 倒叙还是顺序
     * @return {@link List<RentApply> }
     * @author sora
     * @date 2022/02/12
     */
    public List<RentApply> selectRentApply(String begin, String end, String basis, String method) {
        QueryWrapper<RentApply> rentApplyQueryWrapper = new QueryWrapper<>();
        int query = query(rentApplyQueryWrapper, basis, method);
        if (query == -1) {
            return null;
        }
        if (CREATE_TIME.equals(basis)) {
            basis = "create_time";
            rentApplyQueryWrapper.between(basis, begin, end);
            return rentApplyMapper.selectList(rentApplyQueryWrapper);
        } else if (FINISH_TIME.equals(basis)) {
            basis = "finish_time";
            rentApplyQueryWrapper.between(basis, begin, end);
            return rentApplyMapper.selectList(rentApplyQueryWrapper);
        } else if (USING_TIME.equals(basis)) {
            List<String> scheduleIds = scheduleMapper.queryIdBetweenDay(begin, end);
            QueryWrapper<RentApply> queryWrapper;
            RentApply tmp;
            List<RentApply> res = new LinkedList<>();
            List<RentApply> list;
            for (String id : scheduleIds) {
                queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("schedule_id", id);
                list = rentApplyMapper.selectList(queryWrapper);
                if (list == null || list.isEmpty()) {
                    continue;
                }
                tmp = list.get(0);
                if (tmp != null) {
                    res.add(tmp);
                }
            }
            return res;
        }
        return null;
    }

//    /**
//     * 根据实际使用时间段导出使用申请
//     *
//     * @param begin  开始
//     * @param end    结束
//     * @return {@link List<RentApply> }
//     * @author sora
//     * @date 2022/02/12
//     */
//    public List<RentApply> selectRentApply(String begin, String end) {
//        List<String> scheduleIds = scheduleMapper.queryIdBetweenDay(begin, end);
//        List<RentApply> res = new LinkedList<>();
//        QueryWrapper<RentApply> rentApplyQueryWrapper;
//        RentApply tmp;
//        for (String id : scheduleIds) {
//            rentApplyQueryWrapper = new QueryWrapper<>();
//            rentApplyQueryWrapper.eq("schedule_id", id);
//            tmp = rentApplyMapper.selectList(rentApplyQueryWrapper).get(0);
//            if (tmp != null) {
//                res.add(tmp);
//            }
//        }
//        return res;
//    }

    private int query(QueryWrapper<RentApply> queryWrapper, String basis, String method) {
        if (CREATE_TIME.equals(basis)) {
            if (DESC.equals(method)) {
                queryWrapper.orderByDesc("create_time");
            } else if (ASC.equals(method)) {
                queryWrapper.orderByAsc("create_time");
            } else {
                return -1;
            }
        } else if (FINISH_TIME.equals(basis)) {
            if (DESC.equals(method)) {
                queryWrapper.orderByDesc("finish_time");
            } else if (ASC.equals(method)) {
                queryWrapper.orderByAsc("finish_time");
            } else {
                return -1;
            }
        }
        return 1;
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
            if (temp.getApplicantsType().equals(Constants.ROLE_TEACHER)) {
                borrowHistory.setTeacher(teacherMapper.selectTeacherByUid(temp.getApplicantsId()));
            } else if (temp.getApplicantsType().equals(Constants.ROLE_STUDENT)) {
                borrowHistory.setStudent(studentMapper.selectStudentByUid(temp.getApplicantsId()));
            }
            borrowHistory.setDeviceStatus(temp.getDeviceStatus());
            borrowHistory.setFinishTime(temp.getFinishTime());
            borrowHistory.setSchedule(scheduleMapper.selectScheduleById(temp.getScheduleId()));
            borrowHistory.setCreatTime(temp.getCreateTime());
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
            if (o2.getDeviceStatus().equals(Constants.DEVICE_USING)
                    && o1.getDeviceStatus().equals(Constants.DEVICE_RETURNED)) {
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
