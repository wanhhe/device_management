package com.sicau.devicemanagement.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.page.TableDataInfo;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.domain.Roles;
import com.sicau.devicemanagement.service.IRolesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sicau.devicemanagement.common.utils.PageUtils.startPage;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@RestController
@RequestMapping("/system/roles")
public class RolesController extends BaseController
{
    @Autowired
    private IRolesService rolesService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:roles:list')")
    @GetMapping("/list")
    public TableDataInfo list(Roles roles)
    {
        startPage();
        List<Roles> list = rolesService.selectRolesList(roles);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:roles:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Roles roles)
    {
        List<Roles> list = rolesService.selectRolesList(roles);
        ExcelUtil<Roles> util = new ExcelUtil<Roles>(Roles.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:roles:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(rolesService.selectRolesById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:roles:add')")
    @PostMapping
    public AjaxResult add(@RequestBody Roles roles)
    {
        return toAjax(rolesService.insertRoles(roles));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:roles:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody Roles roles)
    {
        return toAjax(rolesService.updateRoles(roles));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:roles:remove')")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(rolesService.deleteRolesByIds(ids));
    }
}
