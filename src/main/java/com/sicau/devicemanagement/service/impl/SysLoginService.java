package com.sicau.devicemanagement.service.impl;


import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.domain.model.LoginUser;
import com.sicau.devicemanagement.common.core.redis.RedisCache;
import com.sicau.devicemanagement.exception.CaptchaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @param type     用户类型
     * @return 结果
     */
    public AjaxResult login(String username, String password, String code, String uuid, String type) throws CaptchaException {
        /* 验证码校验 */
        validateCaptcha(code, uuid);
        username = username + "::" + "ROLE_" + type;
        // 用户验证
        Authentication authentication = null;

        // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token

        String token = tokenService.createToken(loginUser);
        return AjaxResult.success("登录成功").put("role",loginUser.getRole()).put(Constants.TOKEN,token);
    }

    /**
     * 校验验证码
     *
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String code, String uuid) throws CaptchaException {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        // TODO 为了测试上线前改
//        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new CaptchaException("验证码过期");
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException("验证码错误");
        }
    }

}
