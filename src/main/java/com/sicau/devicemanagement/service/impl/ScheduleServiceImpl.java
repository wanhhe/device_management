package com.sicau.devicemanagement.service.impl;


import com.sicau.devicemanagement.domain.Schedule;
import com.sicau.devicemanagement.mapper.ScheduleMapper;
import com.sicau.devicemanagement.service.IScheduleService;
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
}
