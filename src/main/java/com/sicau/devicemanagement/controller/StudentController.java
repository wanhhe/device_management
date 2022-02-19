package com.sicau.devicemanagement.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.page.TableDataInfo;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.common.utils.StringUtils;
import com.sicau.devicemanagement.common.utils.file.DateUtils;
import com.sicau.devicemanagement.common.utils.uuid.IdUtils;
import com.sicau.devicemanagement.domain.Student;
import com.sicau.devicemanagement.domain.Teacher;
import com.sicau.devicemanagement.domain.model.LoginUser;
import com.sicau.devicemanagement.service.IStudentService;
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
@RequestMapping("/system/student")
public class StudentController extends BaseController
{
    @Autowired
    private IStudentService studentService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:student:list')")
    @GetMapping("/list")
    public TableDataInfo list(Student student)
    {
        startPage();
        List<Student> list = studentService.selectStudentList(student);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:student:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Student student)
    {
        List<Student> list = studentService.selectStudentList(student);
        ExcelUtil<Student> util = new ExcelUtil<Student>(Student.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:student:query')")
    @GetMapping(value = "/{uid}")
    public AjaxResult getInfo(@PathVariable("uid") String uid)
    {
        return AjaxResult.success(studentService.selectStudentByUid(uid));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:student:add')")
    @PostMapping
    public AjaxResult add(@RequestBody Student student)
    {
        String expire = student.getExpirationDate();
        if (!StringUtils.isNotEmpty(expire)) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "请输入账号过期时间");
        }
        // 看expire参数有没有问题
        // 先转化时间
        long time = DateUtils.dateTime(expire, DateUtils.YYYY_MM_DD).getTime() - System.currentTimeMillis();
        if (time > (long) 2*365*24*60*60*1000 || time <= 0) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "账号有效期错误");
        }
        student.setUid(IdUtils.simpleUUID());
        student.setPassword(bCryptPasswordEncoder.encode(student.getStuNumber()));
        student.setRoleId("1");
        return toAjax(studentService.insertStudent(student));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:student:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody Student student)
    {
        if (!StringUtils.isNotEmpty(student.getPassword())) {
            student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
        }
        return toAjax(studentService.updateStudent(student));
    }

    /**
     * 学生修改自己账号的密码
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
        return studentService.studentUpdatePassword(uid, bCryptPasswordEncoder.encode(password), verifycode) == -1
                ? AjaxResult.error(HttpStatus.ACCEPTED, "验证码过期") : AjaxResult.success();
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
         return toAjax(studentService.getPasswordVerify(uid));
    }

    /**
     * 学生修改自己账号的绑定手机号
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
        return studentService.studentUpdateTel(uid, tel, verifycode) == -1
                ? AjaxResult.error(HttpStatus.ACCEPTED, "验证码过期") : AjaxResult.success();
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
        return toAjax(studentService.getTelVerify(uid));
    }

    /**
     * 老师延长下属账号使用时间
     *
     * @param sid   sid
     * @param token 令牌
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/28
     */
    @PutMapping("/teacher/{sid}/{week}")
    public AjaxResult teacherExtend(@PathVariable("sid") String sid,
                                    @PathVariable("week") int week,
                                    @RequestHeader("Authorization") String token) {
        if (week*7 > 365*2) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "可延长时间在两年内");
        }
        // 判断是不是下属学生或是不是管理员
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (loginUser.getRole().equals(Constants.TEACHER)) {
            // 如果是普通老师的话要判断该账号是不是其下属账号
            boolean flag = studentService.isStudentMaster(loginUser.getUserId(), sid);
            if (!flag) {
                return AjaxResult.error(HttpStatus.FORBIDDEN, "您没有权限延长该账号");
            }
        }
        // 判断该账号是否封禁中
        int status = studentService.queryStudentStatus(sid);
        if (status == 1) {
            return AjaxResult.error(HttpStatus.NOT_MODIFIED, "该账号在封禁中");
        }
        studentService.extendStudent(sid, week);
        return AjaxResult.success();
    }

    /**
     * 老师封禁下属学生账号
     *
     * @param sid   sid
     * @param del   1为封禁，0为直接删除账号
     * @param token 令牌
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/28
     */
    @DeleteMapping("/teacher/{sid}/{del}")
    public AjaxResult teacherBan(@PathVariable("sid") String sid,
                                 @PathVariable("del") int del,
                                 @RequestHeader("Authorization") String token) {
        // 判断是不是下属学生或是不是管理员
        LoginUser loginUser = tokenService.getLoginUser(token);
        // TODO: 2022/1/28 区分管理员和普通老师的常量
        if (loginUser.getRole().equals(Constants.TEACHER)) {
            // 如果是普通老师的话要判断该账号是不是其下属账号
            boolean flag = studentService.isStudentMaster(loginUser.getUserId(), sid);
            if (!flag && del == 1) {
                return AjaxResult.error(HttpStatus.FORBIDDEN, "您没有权限封禁该账号");
            } else if (!flag && del == 0) {
                return AjaxResult.error(HttpStatus.FORBIDDEN, "您没有权限删除该账号");
            }
        }
        if (del == 0) {
            studentService.deleteStudentByUid(sid);
        } else if (del == 1) {
            studentService.banStudentByUid(sid);
        } else {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "请输入正确的参数");
        }
        return AjaxResult.success();
    }

    /**
     * 老师批量解封下属学生
     *
     * @param ids   id
     * @param token 令牌
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/14
     */
    @PutMapping("/teacher")
    public AjaxResult teacherCancelBan(@RequestBody String[] ids, @RequestHeader("Authorization") String token) {
        // 判断是不是下属学生或是不是管理员
//        LoginUser loginUser = tokenService.getLoginUser(token);
//        // TODO: 2022/1/28 区分管理员和普通老师的常量
//        if (loginUser.getRole().equals(Constants.TEACHER)) {
//            // 如果是普通老师的话要判断该账号是不是其下属账号
//            boolean flag = studentService.isStudentMaster(loginUser.getUserId(), sid);
//            if (!flag) {
//                return AjaxResult.error(HttpStatus.FORBIDDEN, "您没有权限解封该账号");
//            }
//        }
        return AjaxResult.success(studentService.cancelBanStudentByUids(ids));
    }
}
