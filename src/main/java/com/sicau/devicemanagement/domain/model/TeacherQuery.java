package com.sicau.devicemanagement.domain.model;

import com.sicau.devicemanagement.domain.Teacher;
import lombok.Data;

/**
 * @author Mr.xin
 * @date 2022/2/12 22:21
 * @description
 */
@Data
public class TeacherQuery extends Teacher {
    /* 是否有设备 */
    private Boolean isBelongDev  = true;
}
