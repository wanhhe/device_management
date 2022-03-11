package com.sicau.devicemanagement.domain.sicau;

import lombok.Data;

@Data
public class UnGraduateClass extends Curriculum{

//    - 课程号 KCH `string`
//            - 开课学年度 KKXND `string`
//            - 周次 ZC `string`
//            - 教学地点 JXDD `string`
//            - 上课时间 SKSJ `string`
//            - 校区名称 XQMC `string`

//    {"ZHXS":"0","SKBJ":"水质工程学Ⅱ(实践教学)426201908100302","XQMC":"都江堰","ZC":"1-19","SKSJ":"","KKXND":"2021-2022-2",
//            "JXDD":"","KCMC":"水质工程学Ⅱ(实践教学)","DLSY":"否","XF":"1","KCH":"300348857","PKLB":"混教","ZXS":"0",
//            "KCXZ":"实践教学","JSXMA":"罗鸿兵","JSXM":"罗鸿兵","ZYYQ":"给排水(本)201901,给排水(本)201902","XKRS":"57"
//            ,"KCKSDW":"土木工程学院"}

    /* 排课类别 */
    private String PKLB;

    /* 单列实验 */
    private String DLSY;

    /* 上课班级 */
    private String SKBJ;

    /* 周学时 */
    private int ZHXS;

    /* 专业要求 */
    private String ZYYQ;

    /* 选课人数 */
    private int XKRS;

    /* 周次 */
    private String ZC;

    /* 课程开设单位 */
    private String KCKSDW;

    /* 教师姓名A */
    private String JSXMA;

    /* 课程性质 */
    private String KCXZ;

    /* 上课时间 */
    private String SKSJ;

    /* 校区名称 */
    private String XQMC;
}
