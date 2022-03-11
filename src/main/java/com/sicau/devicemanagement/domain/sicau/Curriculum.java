package com.sicau.devicemanagement.domain.sicau;

import lombok.Data;

@Data
public class Curriculum {

    /**
     * 课程号
     */
    private String KCH;

    /**
     * 课程名称
     */
    private String KCMC;

    /**
     * 开课学年度
     */
    private String KKXND;

    /**
     * 教学地点
     */
    private String JXDD;

    /**
     * 总学时
     */
    private int ZXS;

    /**
     * 学分
     */
    private int XF;

    /**
     * 教师姓名
     */
    private String JSXM;
}
