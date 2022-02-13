package com.sicau.devicemanagement.domain.model;

import lombok.Data;

@Data
public class ClassInfo {

//    - 教室号 JSH `string`
//            - 教室描述 JSMS `string`
//            - 校区号 XQH `string`
//            - 所在楼层 SZLC `string`
//            - 数据状态0新增，1删除，2 更新 FLAG_ `string`
//            - 教学楼号 JXLH `string`
//            - 教室类型码 JSLXM `string`

//    {"JSGLBM":"","JSLXM":"实验室","ID_":"1843171","JSYT":"","JXLH":"","KSZWS":"30","JSMS":"","XQH":"2",
//            "FLAG_":"0","DATA_FROM_":"教务","ZWS":"30","YXZWS":"30","SZLC":"","JSH":"3-226"}

    private String JSH;

    private String XQH;

    private String SZLC;

    private String FLAG_;

    private String JXLH;

    private String JSLXM;

    private String ID_;
}
