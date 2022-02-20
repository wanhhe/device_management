package com.sicau.devicemanagement.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sicau.devicemanagement.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 schedule
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public class Schedule
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 实验室id */
    @Excel(name = "实验室id")
    private String labId;

    /** 学年，如2020-2021上 */
    @Excel(name = "学年，如2020-2021上")
    private String term;

    /** 第几周/星期几，如18/2 */
    @Excel(name = "第几周/星期几，如18/2")
    private String weekDay;

    /** 第几节课 */
    @Excel(name = "课程节数，如 1，2，3")
    private String lesson;

    /** 这节课开始的时间 */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    @Excel(name = "这节课开始的时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm")
    private Date beginTime;

    /** 这节课结束的时间 */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    @Excel(name = "这节课结束的时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm")
    private Date endTime;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setLabId(String labId) 
    {
        this.labId = labId;
    }

    public String getLabId() 
    {
        return labId;
    }
    public void setTerm(String term) 
    {
        this.term = term;
    }

    public String getTerm() 
    {
        return term;
    }
    public void setWeekDay(String weekDay) 
    {
        this.weekDay = weekDay;
    }

    public String getWeekDay() 
    {
        return weekDay;
    }
    public void setLesson(String lesson)
    {
        this.lesson = lesson;
    }

    public String getLesson()
    {
        return lesson;
    }
    public void setBeginTime(Date beginTime) 
    {
        this.beginTime = beginTime;
    }

    public Date getBeginTime() 
    {
        return beginTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("labId", getLabId())
            .append("term", getTerm())
            .append("weekDay", getWeekDay())
            .append("lesson", getLesson())
            .append("beginTime", getBeginTime())
            .append("endTime", getEndTime())
            .toString();
    }
}
