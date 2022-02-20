package com.sicau.devicemanagement.domain.model;

import lombok.Data;

@Data
public class UnGraduateClass {

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

    private String KCH;

    private String KKXND;

    private String ZC;

    private String JXDD;

    private String SKSJ;

    private String XQMC;

    private String KCKSDW;
}
