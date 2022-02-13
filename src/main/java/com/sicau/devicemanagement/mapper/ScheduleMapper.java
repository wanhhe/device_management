package com.sicau.devicemanagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sicau.devicemanagement.domain.Schedule;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public interface ScheduleMapper extends BaseMapper<Schedule>
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Schedule selectScheduleById(String id);

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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteScheduleById(String id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScheduleByIds(String[] ids);

    /**
     * 查询几天之间的所有id
     *
     * @param begin 开始 yyyy-MM-dd
     * @param end   结束
     * @return {@link List<String> }
     * @author sora
     * @date 2022/02/12
     */
    public List<String> queryIdBetweenDay(String begin, String end);
}
