package com.sicau.devicemanagement.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sicau.devicemanagement.common.annotation.Excel;
import lombok.Data;

@Data
public class Purchase {

    private String id;

    private String tid;

    @Excel(name = "教师姓名")
    @TableField(exist = false)
    private String tname;

    @Excel(name = "名称")
    private String name;

    @Excel(name = "型号")
    private String type;

    @Excel(name = "生产商")
    private String manufactor;

    @Excel(name = "用途")
    private String purpose;

    @Excel(name = "价格")
    private float price;

    @Excel(name = "购买数量")
    private Integer count;

    @Excel(name = "期望购买时间")
    private String supposePurchaseTime;

    @Excel(name = "创建时间")
    private String createTime;
}
