package com.sicau.devicemanagement.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.page.TableDataInfo;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.domain.Teacher;
import com.sicau.devicemanagement.domain.model.TeacherQuery;
import com.sicau.devicemanagement.service.ITeacherService;
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
}
