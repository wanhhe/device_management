package com.sicau.devicemanagement.domain.model;

import java.sql.Timestamp;

public class SmsBody {

    private final String LoginName = "四川农业大学智慧校园";

    private final String Pwd = "UYi@45wa";

    private final String FeeType = "2";

    private String Mobile;

    private String Content;

    private final String SignName = "【川农智慧校园】";

//    private String TimingDate;
//
//    private String ExtCode;

    public SmsBody() {
    }

    public SmsBody(String content, String mobile) {
        Mobile = mobile;
        Content = content;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

//    public String getTimingDate() {
//        return TimingDate;
//    }
//
//    public void setTimingDate(String timingDate) {
//        TimingDate = timingDate;
//    }
//
//    public String getExtCode() {
//        return ExtCode;
//    }
//
//    public void setExtCode(String extCode) {
//        ExtCode = extCode;
//    }
}
