package com.sicau.devicemanagement.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.page.TableDataInfo;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.domain.Student;
import com.sicau.devicemanagement.domain.Teacher;
import com.sicau.devicemanagement.domain.model.LoginUser;
import com.sicau.devicemanagement.service.IStudentService;
import com.sicau.devicemanagement.service.impl.TokenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
        return toAjax(studentService.insertStudent(student));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:student:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody Student student)
    {
        return toAjax(studentService.updateStudent(student));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:student:remove')")
	@DeleteMapping("/{uids}")
    public AjaxResult remove(@PathVariable String[] uids)
    {
        return toAjax(studentService.deleteStudentByUids(uids));
    }

    /**
     * 教师添加下属学生账号
     *
     * @param student 学生
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/28
     */
    @PostMapping("/teacher")
    public AjaxResult teacherAdd(@RequestBody Student student) {
        // TODO: 2022/1/28 根据登录流程的短信验证是学生自行验证还是老师直接输入看写不写这个接口
        return AjaxResult.success();
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
                                    @PathVariable("time") int week,
                                    @RequestHeader("Authorization") String token) {
        if (week*7 > 365*2) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "可延长时间在两年内");
        }
        // 判断是不是下属学生或是不是管理员
        LoginUser loginUser = tokenService.getLoginUser(token);
        // TODO: 2022/1/28 区分管理员和普通老师的常量
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
     * 老师更新下属账号信息
     *
     * @param student 学生
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/28
     */
    @PutMapping("/teacher")
    public AjaxResult teacherUpdate(@RequestBody Student student) {
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
}
