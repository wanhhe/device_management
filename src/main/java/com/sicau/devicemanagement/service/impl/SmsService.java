package com.sicau.devicemanagement.service.impl;

import com.sicau.devicemanagement.common.utils.http.HttpUtils;
import com.sicau.devicemanagement.domain.model.SmsBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 短信服务
 *
 * @author sora
 * @date 2022/01/18
 */
@Component
public class SmsService {

    @Resource
    private RestTemplate restTemplate;

    private final static String URL = "https://sdkulink.028lk.com:8082/Api/SendSms?LoginName=YACN001&Pwd=UYi@45wa&FeeType=2";


    /**
     * 发送短信
     *
     * @param content 内容
     * @param tel     电话
     * @return {@link String }
     * @author sora
     * @date 2022/02/15
     */
    private String sendSms(String content, String tel) {
        String url = URL + "&Mobile=" + tel + "&SignName=" + "&Content=" + content + "&TimingDate=&ExtCode=";
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * 发送定时短信
     *
     * @param content    内容
     * @param tel        电话
     * @param timingDate 时间日期
     * @return {@link String }
     * @author sora
     * @date 2022/02/15
     */
    public String sendSms(String content, String tel, String timingDate) {
        String url = URL + "&Mobile=" + tel + "&SignName=" + "&Content=" + content +
                "&TimingDate=" + timingDate + "&ExtCode=";
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * 发送验证代码
     *
     * @param content    内容
     * @param tel        电话
     * @param verifyCode 验证代码
     * @return {@link String }
     * @author sora
     * @date 2022/02/15
     */
    private String sendVerifyCode(String content, String tel, String verifyCode) {
        /* 用% 做占位符 */
//        StringBuilder builder = new StringBuilder(content);
//        int index = builder.indexOf("%");
//        builder.replace(index, index, verifyCode);
//        return sendSms(builder.toString(), tel);
        return sendSms(content.replaceAll("%", verifyCode), tel);
    }

    /**
     * 发送开始使用设备短信
     *
     * @param device 设备名
     * @param tel    tel
     * @return {@link String }
     * @author sora
     * @date 2022/02/15
     */
    public String sendStartSms(String device, String tel) {
        String start = "您正在使用设备：" + device + "，请及时归还！";
        return sendSms(start, tel);
    }

    /**
     * 发送短信提醒审核申请
     *
     * @param tel tel
     * @return {@link String }
     * @author sora
     * @date 2022/02/15
     */
    public String sendRemindAuditSms(String tel) {
        String aduit = "您有一条新的设备使用申请等待审核，详情请登录小程序查看！";
        return sendSms(aduit, tel);
    }

    /**
     * 向申请者发送拒绝借用申请的短信
     *
     * @param tel    tel
     * @param reason 原因
     * @return {@link String }
     * @author sora
     * @date 2022/02/15
     */
    public String sendRejectApply(String tel, String reason) {
        String content = "您好，你的一条借用申请已被拒绝，拒绝原因为：" + reason + "。详情请登录小程序查看！";
        return sendSms(content, tel);
    }

    /**
     * 发送申请通过申请
     *
     * @param device 设备
     * @param tel    电话
     * @return {@link String }
     * @author sora
     * @date 2022/02/15
     */
    public String sendPassNotify(String device, String tel) {
        String content = "您好，你申请借用的" + device + "已通过审核";
        return sendSms(content, tel);
    }

    /**
     * 发送设备缺货通知
     *
     * @param device 设备
     * @param tel    电话
     * @return {@link String }
     * @author sora
     * @date 2022/02/15
     */
    public String sendDeviceLackNotify(String device, String tel) {
        String content = "您好，你申请借用的设备" + device + "已无可用设备，请重新选择其它时间段申请使用！";
        return sendSms(content, tel);
    }

    /**
     * 修改用户联系方式时进行校验
     *
     * @param tel        tel
     * @param verifyCode 验证代码
     * @return {@link String }
     * @author sora
     * @date 2022/02/15
     */
    public String sendUpdateTelVerifyCode(String tel, String verifyCode) {
        String updateTel = "您好，你正在修改绑定手机号，验证码为%，验证码5分钟内有效，请勿将请勿将该验证码告诉除您之外的任何人！";
        return sendVerifyCode(updateTel, tel, verifyCode);
    }

    /**
     * 修改密码的短信
     *
     * @param tel        电话
     * @param verifyCode 验证代码
     * @return {@link String }
     * @author sora
     * @date 2022/02/15
     */
    public String sendUpdatePasswordVerifyCode(String tel, String verifyCode) {
        String findPassword = "您好，你正在修改账号密码，验证码为%，验证码5分钟内有效，如非本人操作，请尽快修改密码！";
        return sendVerifyCode(findPassword, tel, verifyCode);
    }

    /**
     * 注册时的短信
     *
     * @param tel        电话
     * @param verifyCode 验证代码
     * @return {@link String }
     * @author sora
     * @date 2022/02/15
     */
    public String sendRegisterSms(String tel, String verifyCode) {
        String register = "验证码：%，您正在进行注册操作，验证码5分钟内有效，请勿向他人泄露。";
        return sendVerifyCode(register, tel, verifyCode);
    }

    /**
     * 给学生发送账号创建成功短信
     *
     * @param tel
     * @param name 学生名字
     * @return {@link String }
     * @author sora
     * @date 2022/02/20
     */
    public String sendStudentCreatedSms(String tel, String name) {
        String register = name + "同学您好！您的账号已注册成功，登录账号和初始密码为您的学号，登录成功后请尽快修改密码！";
        return sendSms(register, tel);
    }

    /**
     * 给老师发送账号创建成功短信
     *
     * @param tel  电话
     * @param name 老师名字
     * @return {@link String }
     * @author sora
     * @date 2022/02/20
     */
    public String sendTeacherCreatedSms(String tel, String name) {
        String register = name + "老师您好！您的账号已注册成功，登录账号和初始密码为您的工号，登录成功后请尽快修改密码！";
        return sendSms(register, tel);
    }
}
