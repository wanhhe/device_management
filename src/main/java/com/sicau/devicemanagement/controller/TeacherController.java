package com.sicau.devicemanagement.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.page.TableDataInfo;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.common.utils.StringUtils;
import com.sicau.devicemanagement.common.utils.uuid.IdUtils;
import com.sicau.devicemanagement.domain.Teacher;

import com.sicau.devicemanagement.domain.model.LoginUser;

import com.sicau.devicemanagement.service.ITeacherService;
import com.sicau.devicemanagement.service.impl.TokenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@RestController
@RequestMapping("/system/teacher")
public class TeacherController extends BaseController
{
    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 查询【老师】列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Teacher teacher)
    {
        startPage();
        List<Teacher> list = teacherService.selectTeacherList(teacher);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Teacher teacher)
    {
        List<Teacher> list = teacherService.selectTeacherList(teacher);
        ExcelUtil<Teacher> util = new ExcelUtil<Teacher>(Teacher.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @GetMapping(value = "/{uid}")
    public AjaxResult getInfo(@PathVariable("uid") String uid)
    {
        return AjaxResult.success(teacherService.selectTeacherByUid(uid));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:add')")
    @PostMapping
    public AjaxResult add(@RequestBody Teacher teacher)
    {
        if (teacher.getTel().length() != 11) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "请输入正确的手机号");
        }
        if (StringUtils.isEmpty(teacher.getEmployeeId())) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "请输入正确的工号");
        }
        if (StringUtils.isEmpty(teacher.getMajor())) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "请输入正确的专业");
        }
        if (StringUtils.isEmpty(teacher.getName())) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "请输入正确的姓名");
        }
        teacher.setUid(IdUtils.simpleUUID());
        teacher.setIsDel(Constants.NATURAL);
        teacher.setPassword(bCryptPasswordEncoder.encode(teacher.getEmployeeId()));
        teacher.setRoleId("2");
        teacher.setCollege("水利水电学院");
        return toAjax(teacherService.insertTeacher(teacher));
    }

    /**
     * 老师修改自己账号的密码
     *
     * @param uid        uid
     * @param password   密码
     * @param verifycode 验证码
     * @param token      令牌
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/15
     */
    @PreAuthorize("hasAnyRole('teacher','admin','superAdmin')")
    @PutMapping("/password/{uid}/{password}/{verifycode}")
    public AjaxResult updatePassword(@PathVariable("uid") String uid,
                                     @PathVariable("password") String password,
                                     @PathVariable("verifycode") String verifycode,
                                     @RequestHeader("Authorization") String token) {
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (!loginUser.getUserId().equals(uid)) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "您没有权限进行该操作");
        }
        return teacherService.teacherUpdatePassword(uid, bCryptPasswordEncoder.encode(password), verifycode) == -1
                ? AjaxResult.error(HttpStatus.ACCEPTED, "验证码错误") : AjaxResult.success();
    }

    /**
     * 获得更改密码的验证码
     *
     * @param uid   uid
     * @param token 令牌
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/15
     */
    @PreAuthorize("hasAnyRole('teacher','admin','superAdmin')")
    @GetMapping("/verify/password/{uid}")
    public AjaxResult updatePasswordVerify(@PathVariable("uid") String uid, @RequestHeader("Authorization") String token) {
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (!loginUser.getUserId().equals(uid)) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "您没有权限进行该操作");
        }
        String code = teacherService.getPasswordVerify(uid);
        return code != null ? AjaxResult.success(code) : AjaxResult.error();
    }

    /**
     * 老师修改自己账号的绑定手机号
     *
     * @param uid        uid
     * @param tel        电话
     * @param verifycode 验证码
     * @param token      令牌
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/15
     */
    @PreAuthorize("hasAnyRole('teacher','admin','superAdmin')")
    @PutMapping("/tel/{uid}/{tel}/{verifycode}")
    public AjaxResult updateTel(@PathVariable("uid") String uid,
                                @PathVariable("tel") String tel,
                                @PathVariable("verifycode") String verifycode,
                                @RequestHeader("Authorization") String token) {
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (!loginUser.getUserId().equals(uid)) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "您没有权限进行该操作");
        }
        return teacherService.teacherUpdateTel(uid, tel, verifycode) == -1
                ? AjaxResult.error(HttpStatus.ACCEPTED, "验证码错误") : AjaxResult.success();
    }

    /**
     * 获得更改密码的验证码
     *
     * @param uid   uid
     * @param token 令牌
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/15
     */
    @PreAuthorize("hasAnyRole('teacher','admin','superAdmin')")
    @GetMapping("/verify/tel/{uid}")
    public AjaxResult updateTelVerify(@PathVariable("uid") String uid, @RequestHeader("Authorization") String token) {
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (!loginUser.getUserId().equals(uid)) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "您没有权限进行该操作");
        }
        String code = teacherService.getTelVerify(uid);
        return code != null ? AjaxResult.success(code) : AjaxResult.error();
    }
}
