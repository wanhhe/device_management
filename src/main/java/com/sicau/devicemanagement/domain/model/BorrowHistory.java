package com.sicau.devicemanagement.domain.model;

import com.sicau.devicemanagement.common.annotation.Excel;
import com.sicau.devicemanagement.domain.Device;
import com.sicau.devicemanagement.domain.Schedule;
import com.sicau.devicemanagement.domain.Student;
import com.sicau.devicemanagement.domain.Teacher;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Data
public class BorrowHistory {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 申请设备 */
    @Excel(name = "设备")
    private Device device;

    /** 申请者类型 */
    @Excel(name = "申请者类型")
    private String applicantsType;

    /** 申请者(学生) */
    private Student student;

    /** 申请者(老师) */
    private Teacher teacher;

    /** 设备状态 */
    @Excel(name = "设备状态")
    private String deviceStatus;

    /** 课表主键 */
    @Excel(name = "课表")
    private Schedule schedule;

    /** 申请时间 */
    // TODO mingming
    @Excel(name = "申请时间")
    private String creatTime;

    @Excel(name = "拒绝时间")
    private String finishTime;

    /** 拒绝申请原因 */
    @Excel(name = "拒绝申请原因")
    private String refuseReason;

    /** 拒绝申请的老师 */
    private Teacher refuser;

    /** 指导老师是否通过，1为通过 */
    @Excel(name = "指导老师是否通过，1为通过")
    private Integer instructorPass;

    @Excel(name = "指导老师审核时间")
    private String instructorPassTime;

    /** 管理老师是否通过，1为通过 */
    @Excel(name = "管理老师是否通过，1为通过")
    private Integer administratorPass;

    @Excel(name = "管理老师审核时间")
    private String administratorPassTime;

    /** 拥有老师是否通过，1为通过 */
    @Excel(name = "拥有老师是否通过，1为通过")
    private Integer ownerPass;

    @Excel(name = "拥有老师审核时间")
    private String ownerPassTime;

    public void setCreateTime(Date nowDate) {
    }
}
