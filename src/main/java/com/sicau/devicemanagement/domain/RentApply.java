package com.sicau.devicemanagement.domain;

import com.sicau.devicemanagement.common.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;


/**
 * 【请填写功能名称】对象 rent_apply
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@Data
public class RentApply
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 设备类型id */
    @Excel(name = "设备类型id")
    private String deviceTypeId;

    /** 申请者id */
    @Excel(name = "申请者id")
    private String applicantsId;

    /** 申请者类型 */
    @Excel(name = "申请者类型")
    private String applicantsType;

    /** 设备状态 */
    @Excel(name = "设备状态")
    private String deviceStatus;

    /** 课表主键 */
    @Excel(name = "课表主键")
    private String scheduleId;


    /** 申请时间 */
    // TODO mingming
    @Excel(name = "申请时间")
    private String creatTime;

    @Excel(name = "结束流程时间")
    private String finishTime;

    /** 设备id */
    @Excel(name = "设备id")
    private String deviceId;

    /** 拒绝申请原因 */
    @Excel(name = "拒绝申请原因")
    private String refuseReason;

    /** 拒绝申请的老师id */
    @Excel(name = "拒绝申请的老师id")
    private String refuserId;

    /** 指导老师是否通过，1为通过 */
    @Excel(name = "指导老师是否通过，1为通过")
    private Integer instructorPass;

    /** 管理老师是否通过，1为通过 */
    @Excel(name = "管理老师是否通过，1为通过")
    private Integer administratorPass;

    /** 拥有老师是否通过，1为通过 */
    @Excel(name = "拥有老师是否通过，1为通过")
    private Integer ownerPass;

    /** 审核到第几阶段 */
    @Excel(name = "审核到第几阶段")
    private Integer auditStatus;

    /** 是否逻辑删除，0为删除 */
    @Excel(name = "是否逻辑删除，0为删除")
    private Integer isDel;

    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deviceTypeId", getDeviceTypeId())
            .append("applicantsId", getApplicantsId())
            .append("applicantsType", getApplicantsType())
            .append("deviceStatus", getDeviceStatus())
            .append("scheduleId", getScheduleId())
            .append("deviceId", getDeviceId())
            .append("refuseReason", getRefuseReason())
            .append("refuserId", getRefuserId())
            .append("instructorPass", getInstructorPass())
            .append("administratorPass", getAdministratorPass())
            .append("ownerPass", getOwnerPass())
            .append("auditStatus", getAuditStatus())
            .append("isDel", getIsDel())
            .toString();
    }

    public void setCreateTime(Date nowDate) {
    }
}
