package com.sicau.devicemanagement.controller;

import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.domain.model.LoginBody;
import com.sicau.devicemanagement.common.utils.uuid.IdUtils;
import com.sicau.devicemanagement.domain.Teacher;
import com.sicau.devicemanagement.exception.CaptchaException;
import com.sicau.devicemanagement.mapper.TeacherMapper;
import com.sicau.devicemanagement.service.impl.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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



    @PostMapping("/register")
    public AjaxResult register(@RequestBody Teacher teacher){

        String uuid = IdUtils.simpleUUID();
        teacher.setUid(uuid);
        teacher.setPassword(bCryptPasswordEncoder.encode(teacher.getPassword()));
        teacherMapper.insert(teacher);
        return success();
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
