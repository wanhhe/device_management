package com.sicau.devicemanagement.domain.sicau;

import lombok.Data;

@Data
public class GraduateClass extends Curriculum{

//    - 周次 QSZ `string`
//            - ==开课学年度== KKXND `string`
//            - 教学班号 JXBH `string`
//            - 开课学期码 KKXQM `string`
//            - ==上课节次== SKSJ `string`
//            - 教学地点 JXDD `string`
//            - 课程号 KCH `string`

//    {"KRL":"30","SKSJ":"111111110000","KKXND":"2018","JXDD":"4-C612","KCMC":"农村土地规划与利用",
//            "XF":"2","XH":"2018408026","KKXQM":"第二学期","KCH":"20951251","RKJSGH":"72038",
//            "ZXS":"32","XM":"程密","JXBH":"20951251001","QSZ":"00000000001100000000","KCKSDWH":"","JSXM":"黄凤"}

    /**
     * 周次
     */
    private String QSZ;

    /**
     * 姓名
     */
    private String XM;

    /**
     * 教学班号
     */
    private String JXBH;

    /**
     * 学号
     */
    private String XH;

    /**
     * 开课学期码
     */
    private String KKXQM;

    /**
     * 上课节次
     */
    private String SKSJ;

    /**
     * 课程开设单位
     */
    private String KCKSDWH;

    /**
     * 课容量
     */
    private int KRL;

    /**
     * 任课教师工号
     */
    private String RKJSGH;
}
