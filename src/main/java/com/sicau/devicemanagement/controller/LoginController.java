package com.sicau.devicemanagement.controller;

import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.utils.bean.BeanUtils;
import com.sicau.devicemanagement.domain.Student;
import com.sicau.devicemanagement.domain.Teacher;
import com.sicau.devicemanagement.domain.model.LoginBody;
import com.sicau.devicemanagement.exception.CaptchaException;
import com.sicau.devicemanagement.service.impl.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController  extends BaseController {


    @Autowired
    SysLoginService loginService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) throws CaptchaException {
        if(!loginBody.getType().equals(Constants.TEACHER)&&!loginBody.getType().equals(Constants.STUDENT)){
            error("用户类型错误");
        }
        // 生成令牌
        String token = loginService.login(loginBody.getName(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid(),loginBody.getType());
        AjaxResult ajax = success();
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

}