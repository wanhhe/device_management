package com.sicau.devicemanagement.domain.sicau;

import lombok.Data;

@Data
public class ClassRent {

//            - 教室占用情况码 JSZYQKM  `string`
//            - 数据来源 DATA_FROM_
//            - 教室号 JSH `string`
//            - 数据状态0新增，1删除，2 更新 FLAG_ `string`
//            - 使用日期 SYRQ `string`
//            - 使用节次 SYJC `string`

    /* 使用周次,其中0表示对应周数没课，1表示对应周数有课。左起第一位是第一周 */
    private String SYZC;

    /* 默认主键  */
    private String ID_;

    /* 教室号 */
    private String JSH;

    /* 使用人身份 */
    private String SYRSF;

    /* 数据来源 */
    private String DATA_FROM_;

    /* 学年学期 */
    private String XNXQ;

    /* 使用开始节次 */
    private String SYKSJC;

    /* 使用星期 */
    private String SYXQ;

    /* 数据删除时间 */
    private String DEL_TIME_;

    /* 使用结束节次 */
    private String SYJSJC;

    /* 数据状态0新增，1删除，2更新 */
    private String FLAG_;

    /* 课程名称 */
    private String KCMC;

    /* 教室占用情况码 */
    private String JSZYQKM;

    /* 使用人号 */
    private String SYRH;

    /* 校区 */
    private String XQ;
}
