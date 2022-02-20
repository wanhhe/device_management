package com.sicau.devicemanagement.service;


import com.sicau.devicemanagement.domain.Schedule;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public interface IScheduleService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Schedule selectScheduleById(String id);

    /**
     * 查询【设备的使用情况】
     * @param schedule
     * @return 【使用情况集合】
     */
    public List<Schedule> selectDeviceSchedule(Schedule schedule);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param schedule 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Schedule> selectScheduleList(Schedule schedule);

    /**
     * 新增【请填写功能名称】
     * 
     * @param schedule 【请填写功能名称】
     * @return 结果
     */
    public int insertSchedule(Schedule schedule);

    /**
     * 修改【请填写功能名称】
     * 
     * @param schedule 【请填写功能名称】
     * @return 结果
     */
    public int updateSchedule(Schedule schedule);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteScheduleByIds(String[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteScheduleById(String id);

    /**
     * 判断教室是否被占用
     *
     * @param id schedule主键
     * @return boolean
     * @author sora
     * @date 2022/02/06
     */
    public boolean isClassRoomUse(String id);

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
    public boolean isClassRoomUse(String labId, String term, String weekDay, int lesson);

    /**
     * 判断教室是否被占用
     *
     * @param labId 实验室id
     * @param time  yyyy-MM-dd HH:mm:ss
     * @return boolean
     * @author sora
     * @date 2022/02/06
     */
    public boolean isClassRoomUse(String labId, String time);
}
