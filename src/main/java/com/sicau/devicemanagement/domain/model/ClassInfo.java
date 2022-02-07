package com.sicau.devicemanagement.domain.model;

import lombok.Data;

@Data
public class ClassInfo {

//    - 教室号 JSH `string`
//            - 教室描述 JSMS `string`
//            - 校区号 XQH `string`
//            - 所在楼层 SZLC `string`
//            - 数据状态0新增，1删除，2 更新 FLAG_ `string`
//            - 数据来源 DATA_FROM_ `string`
//            - 教学楼号 JXLH `string`
//            - 教室用途 JSYT `string`
//            - 教室类型码 JSLXM `string`

    private String JSH;

    private String XQH;

    private String SZLC;

    private String FLAG_;

    private String DATA_FROM_;

    private String JXLH;

    private String JSYT;

    private String JSLXM;
}
