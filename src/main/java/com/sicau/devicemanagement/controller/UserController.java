package com.sicau.devicemanagement.controller;

import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.model.LoginBody;
import com.sicau.devicemanagement.common.core.model.LoginUser;
import com.sicau.devicemanagement.common.utils.uuid.IdUtils;
import com.sicau.devicemanagement.common.utils.uuid.UUID;
import com.sicau.devicemanagement.domain.Teacher;
import com.sicau.devicemanagement.entity.User;
import com.sicau.devicemanagement.exception.CaptchaException;
import com.sicau.devicemanagement.mapper.TeacherMapper;
import com.sicau.devicemanagement.service.impl.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zx
 */
@RestController
@RequestMapping("/user")
//TODO 测试接口
public class UserController extends BaseController {

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }



    @PostMapping("/register")
    public AjaxResult register(@RequestBody Teacher teacher){

        String uuid = IdUtils.simpleUUID();
        teacher.setUid(uuid);
        teacher.setPassword(bCryptPasswordEncoder.encode(teacher.getPassword()));
        teacherMapper.insert(teacher);
        return success();
    }
}
