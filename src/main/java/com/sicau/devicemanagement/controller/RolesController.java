package com.sicau.devicemanagement.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.page.TableDataInfo;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.domain.Roles;
import com.sicau.devicemanagement.domain.model.LoginUser;
import com.sicau.devicemanagement.service.IRolesService;
import com.sicau.devicemanagement.service.impl.TokenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private TokenService tokenService;

    /**
     * 查询【角色】列表
     */
    @PreAuthorize("hasAnyRole('superAdmin')")
    @GetMapping("/list")
    public TableDataInfo list(Roles roles)
    {
        startPage();
        List<Roles> list = rolesService.selectRolesList(roles);
        return getDataTable(list);
    }

    /**
     * 获取【请填写功能名称】详细信息
     *
     *  // fixme 未使用
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(rolesService.selectRolesById(id));
    }

    /**
     * 列出所有管理员
     *
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/25
     */
    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    @GetMapping("/admin")
    public AjaxResult listAdmin() {
        return AjaxResult.success(rolesService.getAdmin());
    }

    /**
     * 添加管理员
     *
     * @param id 要添加为管理员的id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/25
     */
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @GetMapping("/admin/{id}")
    public AjaxResult addAdmin(@PathVariable("id") String id) {
        // 先判断是不是教师id
        boolean role = rolesService.checkRole(id, Constants.ROLE_TEACHER_ID);
        if (!role) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST,"参数错误");
        }
        // 新增为管理员
        return toAjax(rolesService.addAdmin(id));
    }

    /**
     * 更新超级管理员
     *
     * @param id id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/25
     */
    @PreAuthorize("hasAnyRole('superAdmin')")
    @GetMapping("/superadmin/{id}")
    public AjaxResult updateSuperAdmin(@PathVariable("id") String id, @RequestHeader("Authorization") String token) {
        // 先判断是不是教师id
        boolean role = rolesService.checkRole(id, Constants.ROLE_TEACHER_ID);
        if (!role) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST,"参数错误");
        }
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (loginUser != null) {
            return toAjax(rolesService.updateSuperAdmin(loginUser.getUserId(), id));
        }
        return AjaxResult.error(HttpStatus.FORBIDDEN, "您无权授予超级管理员!");
    }

    /**
     * 取消某个管理员的权限
     *
     * @param id id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/25
     */
    @PreAuthorize("hasAnyRole('superAdmin')")
    @PutMapping("/admin/{id}")
    public AjaxResult cancelAdmin(@PathVariable("id") String id) {
        // 先判断是不是教师id
        boolean role = rolesService.checkRole(id, Constants.ROLE_TEACHER_ID);
        if (!role) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST,"参数错误");
        }
        return toAjax(rolesService.cancelAdmin(id));
    }
}
