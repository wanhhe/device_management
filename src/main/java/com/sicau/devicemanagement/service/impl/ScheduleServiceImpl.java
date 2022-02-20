package com.sicau.devicemanagement.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sicau.devicemanagement.common.utils.file.DateUtils;
import com.sicau.devicemanagement.common.utils.http.HttpUtils;
import com.sicau.devicemanagement.domain.Schedule;
import com.sicau.devicemanagement.mapper.ScheduleMapper;
import com.sicau.devicemanagement.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@Service
public class ScheduleServiceImpl implements IScheduleService
{
    @Autowired
    private ScheduleMapper scheduleMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Schedule selectScheduleById(String id)
    {
        return scheduleMapper.selectScheduleById(id);
    }

    /**
     *
     * @param schedule
     * @return  【使用情况集合】
     */
    @Override
    public List<Schedule> selectDeviceSchedule(Schedule schedule) {
        return scheduleMapper.selectScheduleList(schedule);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param schedule 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Schedule> selectScheduleList(Schedule schedule)
    {
        return scheduleMapper.selectScheduleList(schedule);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param schedule 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSchedule(Schedule schedule)
    {
        return scheduleMapper.insertSchedule(schedule);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param schedule 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSchedule(Schedule schedule)
    {
        return scheduleMapper.updateSchedule(schedule);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteScheduleByIds(String[] ids)
    {
        return scheduleMapper.deleteScheduleByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteScheduleById(String id)
    {
        return scheduleMapper.deleteScheduleById(id);
    }

    /**
     * 判断教室是否被占用
     *
     * @param id schedule主键
     * @return boolean
     * @author sora
     * @date 2022/02/06
     */
    @Override
    public boolean isClassRoomUse(String id) {
        Schedule schedule = scheduleMapper.selectScheduleById(id);
        if (schedule == null) {
            return true;
        }
        return false;
    }

    /**
     * 判断教室是否被占用
     *
     * @param labId    实验室id
     * @param term     学期
     * @param weekDay 第几周_第几天
     * @param lesson   第几节课
     * @return boolean
     * @author sora
     * @date 2022/02/06
     */
    @Override
    public boolean isClassRoomUse(String labId, String term, String weekDay, int lesson) {
        QueryWrapper<Schedule> scheduleQueryWrapper = new QueryWrapper<>();
        scheduleQueryWrapper.select("id").eq("lab_id", labId)
                .eq("term", term)
                .eq("week_day", weekDay)
                .eq("lesson", lesson);
        List<Schedule> schedules = scheduleMapper.selectList(scheduleQueryWrapper);
        return !schedules.isEmpty();
    }

    /**
     * 判断教室是否被占用
     *
     * @param labId 实验室id
     * @param time  yyyy-MM-dd HH:mm:ss
     * @return boolean
     * @author sora
     * @date 2022/02/06
     */
    @Override
    public boolean isClassRoomUse(String labId, String time) {
        // 换算
        Calendar calendar = DateUtils.toCalendar(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, time));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // 小时
        int t = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        return false;
    }
}
