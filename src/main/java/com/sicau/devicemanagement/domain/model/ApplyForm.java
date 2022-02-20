package com.sicau.devicemanagement.domain.model;

import com.sicau.devicemanagement.domain.RentApply;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Mr.xin
 * @date 2022/2/19 15:51
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplyForm extends RentApply {
    /** 学年 */
    private  String term ;
    /** 实验室id */
    private String labId;
    /** 周/星期几 */
    private String weekDay;
    /** 第几节课 */
    private Integer lesson;
}
