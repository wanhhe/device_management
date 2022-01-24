package com.sicau.devicemanagement.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 短信服务
 *
 * @author sora
 * @date 2022/01/18
 */
@Component
public class SmsService {

    @Value("${sms.signName}")
    private String signName;

    private void sendSms(String text) {

    }

    /**
     * 发送开始使用设备短信
     *
     * @param tel tel
     * @author sora
     * @date 2022/01/18
     */
    public void sendStartSms(String tel) {

    }

    /**
     * 发送短信提醒审核申请
     *
     * @param tel tel
     * @author sora
     * @date 2022/01/18
     */
    public void sendRemindAuditSms(String tel) {

    }

    /**
     * 向申请者发送拒绝借用申请的短信
     *
     * @param tel tel
     * @author sora
     * @date 2022/01/18
     */
    public void sendRejectApply(String tel, String reason) {

    }

    /**
     * 修改用户联系方式时进行校验
     *
     * @param tel tel
     * @author sora
     * @date 2022/01/18
     */
    public void sendCheckSms(String tel, String verifyCode) {

    }
}
