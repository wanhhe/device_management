package com.sicau.devicemanagement.domain;

import com.sicau.devicemanagement.common.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【实验室】对象 lab
 *
 * @author Mr.xin
 * @date 2022-01-15
 */
@Data
public class Lab {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 校区名
     */
    @Excel(name = "校区名")
    private String campusId;

    /**
     * 管理老师id
     */
    @Excel(name = "管理老师id")
    private String managerId;

    /**
     * 教学楼编号
     */
    @Excel(name = "教学楼编号")
    private String buildNum;

    /**
     * 　门牌号
     */
    @Excel(name = "门牌号")
    private String number;

    /**
     * 是否拥有设备
     */
    @Excel(name = "是否拥有设备")
    private Boolean hasDevice;
}
