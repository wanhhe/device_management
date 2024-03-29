package com.sicau.devicemanagement.common.constant;

/**
 * 通用常量信息
 * 
 * @author ruoyi
 */
public class Constants
{

    /**
     * 学生
     */
    public static final  String ROLE_STUDENT = "ROLE_student";

    public static final String ROLE_STUDENT_ID = "1";

    public static final String ROLE_PREFIX = "ROLE_";

    /**
     * 老师
     */
    public static final  String ROLE_TEACHER = "ROLE_teacher";

    public static final String ROLE_TEACHER_ID = "2";

    /**
     * 管理员
     */
    public static final String ROLE_ADMIN = "ROLE_admin";

    public static final String ROLE_ADMIN_ID = "3";

    /**
     * 拥有审核权限的管理员
     */
    public static final String ROLE_SUPER_ADMIN = "ROLE_superAdmin";

    public static final String ROLE_SUPER_ADMIN_ID = "4";


    /* 下面三个用于数据库中的逻辑删除 */
    /**
     * 正常使用
     */
    public static final int NATURAL = 0;

    /**
     * 该记录被逻辑删除
     */
    public static final int DELETE = 1;

    /**
     * 账号过期
     */
    public static final int OVERTIME = 2;

    /* 下面三条是设备的状态 */
    /**
     * 维修中
     */
    public static final String DEVICE_REPAIR = "repair";

    /**
     * 正常使用
     */
    public static final String DEVICE_NATURAL = "natural";

    /**
     * 损坏
     */
    public static final String DEVICE_BROKEN = "broken";

    public static final String DEVICE_APPLY_SUCCESS = "审核通过";
    public static final String DEVICE_APPLY_FAIL = "拒绝使用";
    public static final String DEVICE_APPLY_AUDITING = "审核中";


    /* 下面四条是申请使用中的设备状态 */
    /**
     * 待使用
     */
    public static final String DEVICE_PREUSING = "待使用";

    /**
     * 使用中
     */
    public static final String DEVICE_USING = "使用中";

    /**
     * 归还中
     */
    public static final String DEVICE_RETURNING = "归还中";

    /**
     * 已归还
     */
    public static final String DEVICE_RETURNED = "已归还";

    /**
     * linux服务器文件上传地址
     */
    public static final String LINUX_UPLOAD = "/upload";

    /**
     * windows服务器文件上传地址
     */
    public static final String WINDOWS_UPLOAD = "D:/upload";
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 申请设备使用的临时 ScheduleId
     * */
    public static final String TEMP_SCHEDULE_ID = "temporary_ScheduleIds:";



    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = "username";

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi:";

    /**
     * LDAP 远程方法调用
     */
    public static final String LOOKUP_LDAP = "ldap:";

    /**
     * LDAPS 远程方法调用
     */
    public static final String LOOKUP_LDAPS = "ldaps:";


    public static String getDefaultUploadAddr(){
        String os  = System.getProperty("os.name").toLowerCase();
        if(os.contains("window"))
        {
            return WINDOWS_UPLOAD;
        }
        else
        {
            return LINUX_UPLOAD;
        }
    }
}
