package com.sicau.devicemanagement.domain.model;

import lombok.Data;

/**
 * 用户登录对象
 * 
 * @author ruoyi
 */
@Data
public class LoginBody
{
    /**
     * 用户名
     */
    private String name;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid = "";

    /**
     * 用户类型
     */
    private String type;

}
