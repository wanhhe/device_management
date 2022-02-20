package com.sicau.devicemanagement.domain.model;

import lombok.Data;

@Data
public class ClassRent {

//            - 教室占用情况码 JSZYQKM  `string`
//            - 数据来源 DATA_FROM_
//            - 教室号 JSH `string`
//            - 数据状态0新增，1删除，2 更新 FLAG_ `string`
//            - 使用日期 SYRQ `string`
//            - 使用节次 SYJC `string`

    private String JSZYQKM;

    private String DATA_FROM_;

    private String JSH;

    private String FLAG_;

    private String SYRQ;

    private String SYJC;
}
