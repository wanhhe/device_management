package com.sicau.devicemanagement.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.page.TableDataInfo;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.domain.Teacher;
import com.sicau.devicemanagement.service.ITeacherService;
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
@RequestMapping("/system/teacher")
public class TeacherController extends BaseController
{
    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:list')")
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
    @PreAuthorize("@ss.hasPermi('system:teacher:export')")
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
    @PreAuthorize("@ss.hasPermi('system:teacher:query')")
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
     * 下属学生未归还的设备
     *
     * @param id 老师id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/19
     */
    @GetMapping("/unreturn/student/{id}")
    public AjaxResult belongStudentUnreturnDevice(@PathVariable("id") String id, @RequestHeader("Authorization") String token) {
        if (!tokenService.getTeacherUidFromToken(token).equals(id)) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "token校验失败");
        }
        return null;
    }

    /**
     * 外借的还未归还该教师的设备
     *
     * @param id 该教师的id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/19
     */
    @GetMapping("/unreturn/owner/{id}")
    public AjaxResult lentDeviceUnreturn(@PathVariable("id") String id, @RequestHeader("Authorization") String token) {
        // TODO: 2022/1/21 判断是不是管理员
        if (!tokenService.getTeacherUidFromToken(token).equals(id)) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "token校验失败");
        }
        return null;
    }

    /**
     * 延长下属学生时间
     *
     * @param stuId 要延长的学生账户的id
     * @param month 月
     * @param token 令牌
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/19
     */
    @PutMapping("/sub/{id}")
    public AjaxResult prolongSubordinateStudentAccount(@PathVariable("id") String stuId,
                                                       @RequestParam("month") int month,
                                                       @RequestHeader("Authorization") String token) {
        // 判断是否是下属学生或是否是管理员
        String tid = tokenService.getTeacherUidFromToken(token);
        boolean flag = teacherService.isStudentMasterOrAdmin(tid, stuId);
        if (!flag) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "您无权进行该操作");
        }
        return null;
    }

    /**
     * 管理员或指导老师封禁下属学生账户
     *
     * @param stuId 要封禁的学生账户id
     * @param token 令牌
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/19
     */
    @DeleteMapping("/sub/{id}")
    public AjaxResult banSubordinateStudentAccount(@PathVariable("id") String stuId, @RequestHeader("Authorization") String token) {
        String tid = tokenService.getTeacherUidFromToken(token);
        // 判断是否有权限封禁
        boolean flag = teacherService.isStudentMasterOrAdmin(tid, stuId);
        if (!flag) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "您无权封禁该账户");
        }
        teacherService.banStudent(stuId);
        return AjaxResult.success();
    }
}
