package com.sicau.devicemanagement.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.page.TableDataInfo;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.domain.RentApply;
import com.sicau.devicemanagement.domain.model.LoginUser;
import com.sicau.devicemanagement.service.IDeviceService;
import com.sicau.devicemanagement.service.IRentApplyService;
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
@RequestMapping("/system/apply")
public class RentApplyController extends BaseController
{
    @Autowired
    private IRentApplyService rentApplyService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IDeviceService deviceService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(RentApply rentApply)
    {
        startPage();
        List<RentApply> list = rentApplyService.selectRentApplyList(rentApply);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:apply:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, RentApply rentApply)
    {
        List<RentApply> list = rentApplyService.selectRentApplyList(rentApply);
        ExcelUtil<RentApply> util = new ExcelUtil<RentApply>(RentApply.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:apply:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(rentApplyService.selectRentApplyById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:apply:add')")
    @PostMapping
    public AjaxResult add(@RequestBody RentApply rentApply)
    {
        return toAjax(rentApplyService.insertRentApply(rentApply));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:apply:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody RentApply rentApply)
    {
        return toAjax(rentApplyService.updateRentApply(rentApply));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:apply:remove')")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(rentApplyService.deleteRentApplyByIds(ids));
    }

    /**
     * 确认开始使用设备
     *
     * @param id 申请使用id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/18
     */
    @GetMapping("/start")
    public AjaxResult confirmStartUseDevice(@RequestParam("id") String id, @RequestHeader("Authorization") String token) {
        LoginUser loginUser = tokenService.getLoginUser(token);
        boolean access;
        String uid;
        if (loginUser.getRole().equals(Constants.TEACHER)) {
            uid = loginUser.getUserId();
            // 判断是否能够使用设备
            access = rentApplyService.isUserAccessDevice(uid,id);
            if (!access) {
                return AjaxResult.error(HttpStatus.FORBIDDEN, "该时间段您无权使用该设备!");
            }
            rentApplyService.teacherStartUsingDevice(id);
        } else if (loginUser.getRole().equals(Constants.STUDENT)) {
            uid = loginUser.getUserId();
            // 判断是否能够使用设备
            access = rentApplyService.isUserAccessDevice(uid, id);
            if (!access) {
                return AjaxResult.error(HttpStatus.FORBIDDEN, "该时间段您无权使用该设备!");
            }
            rentApplyService.studentStartUsingDevice(id);
        }
        return AjaxResult.success();
    }

    /**
     * 申请归还设备
     *
     * @param id 申请使用id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/18
     */
    @GetMapping("/return")
    public AjaxResult applyRetrunDevice(@RequestParam("id") String id, @RequestHeader("Authorization") String token) {
        String uid = tokenService.getLoginUser(token).getUserId();
        // 判断是否能够结束设备
        boolean access = rentApplyService.isUserFinishDevice("2", id);
        if (!access) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "该时间段您无权结束该设备!");
        }
        rentApplyService.finishUse(uid, id);
        return AjaxResult.success();
    }

    /**
     * 管理老师确认设备已归还
     *
     * @param id     申请使用id
     * @param token  令牌
     * @param broken
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/16
     */
    @GetMapping("/return/confirm")
    public AjaxResult confirmReturnDevice(@RequestParam("id") String id,
                                          @RequestHeader("Authorization") String token,
                                          @RequestParam("broken") boolean broken) {
        String uid = tokenService.getStudentUidFromToken(token);
        // 判断是否能够确认归还
        boolean flag = rentApplyService.isDeviceOwner(uid, id);
        if (!flag) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "您不是设备管理者!");
        }
        if (broken) { // 如果设备损坏，更新设备状态
            rentApplyService.deviceBroken(id);
        } else {
            rentApplyService.confirmReturn(id);
        }
        return AjaxResult.success();
    }

    /**
     * 在使用之前已经设备损坏
     *
     * @param id    申请使用id
     * @param token 令牌
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/19
     */
    @GetMapping("/damage/before")
    public AjaxResult hasDamagedBeforeUseDevice(@RequestParam("id") String id, @RequestHeader("Authorization") String token) {
        LoginUser loginUser = tokenService.getLoginUser(token);
        String uid;
        boolean access;
        if (loginUser.getRole().equals(Constants.TEACHER)) {
            uid = loginUser.getUserId();
            // 判断是否能够使用设备
            access = rentApplyService.isUserAccessDevice(uid,id);
            if (!access) {
                return AjaxResult.error(HttpStatus.FORBIDDEN, "该时间段您无权使用该设备!");
            }
            rentApplyService.finishUse(uid, id);
        } else if (loginUser.getRole().equals(Constants.STUDENT)) {
            uid = loginUser.getUserId();
            // 判断是否能够使用设备
            access = rentApplyService.isUserAccessDevice(uid, id);
            if (!access) {
                return AjaxResult.error(HttpStatus.FORBIDDEN, "该时间段您无权使用该设备!");
            }
            rentApplyService.finishUse(uid, id);
        }
        return AjaxResult.success();
    }

    /**
     * 设备损坏，申请替换设备
     *
     * @param id    申请使用id
     * @param token 令牌
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/21
     */
    @GetMapping("/damage/replacement")
    public AjaxResult replaceDeviceBecauseDamage(@RequestParam("id") String id, @RequestHeader("Authorization") String token) {
        LoginUser loginUser = tokenService.getLoginUser(token);
        String uid;
        boolean access;
        if (loginUser.getRole().equals(Constants.TEACHER)) {
            uid = loginUser.getUserId();
            // 判断是否能够使用设备
            access = rentApplyService.isUserAccessDevice(uid,id);
            if (!access) {
                return AjaxResult.error(HttpStatus.FORBIDDEN, "该时间段您无权使用该设备!");
            }
            deviceService.replaceDevice(uid, id);
        } else if (loginUser.getRole().equals(Constants.STUDENT)) {
            uid = loginUser.getUserId();
            // 判断是否能够使用设备
            access = rentApplyService.isUserAccessDevice(uid, id);
            if (!access) {
                return AjaxResult.error(HttpStatus.FORBIDDEN, "该时间段您无权使用该设备!");
            }
            deviceService.replaceDevice(uid, id);
            return AjaxResult.success();
        }
        return AjaxResult.success();
    }

    /**
     * 使用设备后设备损坏
     *
     * @param id    申请使用id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/19
     */
    @GetMapping("/damage/after")
    public AjaxResult hasDamageAfterUseDevice(@RequestParam("id") String id, @RequestHeader("Authorization") String token) {
        String uid = tokenService.getTeacherUidFromToken(token);
        boolean flag = deviceService.isDeviceOwner(uid, id);
        if (!flag) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "您不是该设备的拥有者，无权结束该设备使用!");
        }
        return null;
    }
}
