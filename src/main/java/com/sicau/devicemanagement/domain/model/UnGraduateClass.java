package com.sicau.devicemanagement.domain.model;

import lombok.Data;

@Data
public class UnGraduateClass {

//    - 课程号 KCH `string`
//            - 排课类别 PKLB `string`
//            - 单列实验 DLSY `string`
//            - 开课学年度 KKXND `string`
//            - 周学时 ZHXS `int`
//            - 专业要求 ZYYQ `string`
//            - 课程名称 KCMC `string`
//            - 周次 ZC `string`
//            - 课程性质 KCXZ `string`
//            - 教学地点 JXDD `string`
//            - 上课时间 SKSJ `string`
//            - 校区名称 XQMC `string`

    private String KCH;

    private String PKLB;

    private String DLSY;

    private String KKXND;

    private int ZHXS;

    private String ZYYQ;

    private String KCMC;

    private String ZC;

    private String KCXZ;

    private String JXDD;

    private String SKSJ;

    private String XQMC;
}
