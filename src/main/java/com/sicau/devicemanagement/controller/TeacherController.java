package com.sicau.devicemanagement.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.page.TableDataInfo;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.common.utils.uuid.IdUtils;
import com.sicau.devicemanagement.domain.Teacher;
import com.sicau.devicemanagement.domain.model.LoginUser;
import com.sicau.devicemanagement.domain.model.TeacherQuery;
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
    public TableDataInfo list(TeacherQuery teacher)
    {
        startPage();
        List<Teacher> list = teacherService.selectTeacherList(teacher);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, TeacherQuery teacher)
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
        teacher.setUid(IdUtils.simpleUUID());
        teacher.setIsDel(0);
        teacher.setPassword(bCryptPasswordEncoder.encode(teacher.getPassword()));
        teacher.setRoleId("2");
        return toAjax(teacherService.insertTeacher(teacher));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody Teacher teacher)
    {
        return toAjax(teacherService.updateTeacher(teacher));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:remove')")
	@DeleteMapping("/{uids}")
    public AjaxResult remove(@PathVariable String[] uids)
    {
        return toAjax(teacherService.deleteTeacherByUids(uids));
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
    @PutMapping("/password/{uid}/{password}/{verifycode}")
    public AjaxResult updatePassword(@PathVariable("uid") String uid,
                                     @PathVariable("password") String password,
                                     @PathVariable("verifycode") String verifycode,
                                     @RequestHeader("Authorization") String token) {
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (!loginUser.getUserId().equals(uid)) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "您没有权限进行该操作");
        }
        return toAjax(teacherService.teacherUpdatePassword(uid, bCryptPasswordEncoder.encode(password), verifycode));
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
    @GetMapping("/verify/password/{uid}")
    public AjaxResult updatePasswordVerify(@PathVariable("uid") String uid, @RequestHeader("Authorization") String token) {
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (!loginUser.getUserId().equals(uid)) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "您没有权限进行该操作");
        }
        return toAjax(teacherService.getPasswordVerify(uid));
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
    @PutMapping("/tel/{uid}/{tel}/{verifycode}")
    public AjaxResult updateTel(@PathVariable("uid") String uid,
                                @PathVariable("tel") String tel,
                                @PathVariable("verifycode") String verifycode,
                                @RequestHeader("Authorization") String token) {
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (!loginUser.getUserId().equals(uid)) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "您没有权限进行该操作");
        }
        return toAjax(teacherService.teacherUpdateTel(uid, tel, verifycode));
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
    @GetMapping("/verify/tel/{uid}")
    public AjaxResult updateTelVerify(@PathVariable("uid") String uid, @RequestHeader("Authorization") String token) {
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (!loginUser.getUserId().equals(uid)) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "您没有权限进行该操作");
        }
        return toAjax(teacherService.getTelVerify(uid));
    }
}
