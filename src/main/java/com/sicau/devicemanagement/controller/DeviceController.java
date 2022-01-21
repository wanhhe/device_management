package com.sicau.devicemanagement.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.model.LoginUser;
import com.sicau.devicemanagement.common.core.page.TableDataInfo;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.domain.Device;
import com.sicau.devicemanagement.service.IDeviceService;
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
@RequestMapping("/system/device")
public class DeviceController extends BaseController
{
    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:device:list')")
    @GetMapping("/list")
    public TableDataInfo list(Device device)
    {
        startPage();
        List<Device> list = deviceService.selectDeviceList(device);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:device:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Device device)
    {
        List<Device> list = deviceService.selectDeviceList(device);
        ExcelUtil<Device> util = new ExcelUtil<Device>(Device.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:device:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(deviceService.selectDeviceById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:device:add')")
    @PostMapping
    public AjaxResult add(@RequestBody Device device)
    {
        return toAjax(deviceService.insertDevice(device));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:device:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody Device device)
    {
        return toAjax(deviceService.updateDevice(device));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:device:remove')")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(deviceService.deleteDeviceByIds(ids));
    }


    /**
     * 返回总设备数量，正在使用的设备的数量，超时未归还的设备数量
     *
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/18
     */
    @GetMapping("/view")
    public AjaxResult getSituation() {
        return AjaxResult.success(deviceService.queryDeviceTotalStatus());
    }

    /**
     * 获得某台设备的租用历史
     *
     * @param id 该设备id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/18
     */
    @GetMapping("/history/{id}")
    public AjaxResult getRentHistory(@PathVariable("id") String id) {
        return null;
    }

    /**
     * 修改设备状态
     *
     * @param id 该设备id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/18
     */
    @GetMapping("/status")
    public AjaxResult updateDeviceStatus(@RequestParam("id") String id) {
        return null;
    }

    /**
     * 确认开始使用设备
     *
     * @param id 该设备id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/18
     */
    @GetMapping("/start")
    public AjaxResult confirmStartUseDevice(@RequestParam("id") String id, @RequestHeader("Authorization") String token) {
        String uid = tokenService.getStudentUidFromToken(token);
        boolean access = false;
        if (uid == null) {
            uid = tokenService.getTeacherUidFromToken(token);
            if (uid == null) {
                return AjaxResult.error(HttpStatus.UNAUTHORIZED, "token校验失败");
            }
            // 判断是否能够使用设备
            access = deviceService.isUserAccessDevice(uid,id);
            if (!access) {
                return AjaxResult.error(HttpStatus.FORBIDDEN, "该时间段您无权使用该设备!");
            }
            deviceService.teacherStartUsingDevice(uid, id);
            return AjaxResult.success();
        }
        // 判断是否能够使用设备
        access = deviceService.isUserAccessDevice(uid, id);
        if (!access) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "该时间段您无权使用该设备!");
        }
        deviceService.studentStartUsingDevice(uid, id);
        return AjaxResult.success();
    }

    /**
     * 确认设备使用完毕
     *
     * @param id 该设备id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/18
     */
    @GetMapping("/finish")
    public AjaxResult confirmFinishUseDevice(@RequestParam("id") String id, @RequestHeader("Authorization") String token) {
        String uid = tokenService.getStudentUidFromToken(token);
        boolean flag = deviceService.isUserAccessDevice(uid, id);
        // 判断是否能够结束设备
        flag = deviceService.isUserAccessDevice(uid, id);
        if (!flag) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "该时间段您无权结束该设备!");
        }
        // 判断是否是设备的拥有者
        flag = deviceService.isDeviceOwner(uid, id);
        if (!flag) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "您不是该设备的拥有者，无权结束该设备使用!");
        }
        deviceService.studentFinishUsingDevice(uid, id);
        return AjaxResult.success();
    }


    /**
     * 在使用之前已经设备损坏
     *
     * @param id    id
     * @param token 令牌
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/19
     */
    @GetMapping("/damage/before")
    public AjaxResult hasDamagedBeforeUseDevice(@RequestParam("id") String id, @RequestHeader("Authorization") String token) {
        String uid = tokenService.getStudentUidFromToken(token);
        boolean access;
        if (uid == null) {
            uid = tokenService.getTeacherUidFromToken(token);
            if (uid == null) {
                return AjaxResult.error(HttpStatus.UNAUTHORIZED, "token校验失败");
            }
            // 判断是否能够使用设备
            access = deviceService.isUserAccessDevice(uid,id);
            if (!access) {
                return AjaxResult.error(HttpStatus.FORBIDDEN, "该时间段您无权使用该设备!");
            }
            deviceService.teacherStartUsingDevice(uid, id);
            return AjaxResult.success();
        }
        // 判断是否能够使用设备
        access = deviceService.isUserAccessDevice(uid, id);
        if (!access) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "该时间段您无权使用该设备!");
        }
        deviceService.studentFinishUsingDevice(uid, id);
        return AjaxResult.success();
    }

    /**
     * 设备损坏，申请替换设备
     *
     * @param id    id
     * @param token 令牌
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/21
     */
    @GetMapping("/damage/replacement")
    public AjaxResult replaceDeviceBecauseDamage(@RequestParam("id") String id, @RequestHeader("Authorization") String token) {
        String uid = tokenService.getStudentUidFromToken(token);
        boolean access;
        if (uid == null) {
            uid = tokenService.getTeacherUidFromToken(token);
            if (uid == null) {
                return AjaxResult.error(HttpStatus.UNAUTHORIZED, "token校验失败");
            }
            // 判断是否能够使用设备
            access = deviceService.isUserAccessDevice(uid,id);
            if (!access) {
                return AjaxResult.error(HttpStatus.FORBIDDEN, "该时间段您无权使用该设备!");
            }
            deviceService.teacherStartUsingDevice(uid, id);
            return AjaxResult.success();
        }
        // 判断是否能够使用设备
        access = deviceService.isUserAccessDevice(uid, id);
        if (!access) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "该时间段您无权使用该设备!");
        }
        deviceService.replaceDevice(uid, id);
        return AjaxResult.success();
    }

    /**
     * 使用设备后设备损坏
     *
     * @param id    该设备id
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

    /**
     * 自己借用但还未归还的设备
     *
     * @param id 该用户id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/19
     */
    @GetMapping("/unreturn/{id}")
    public AjaxResult checkUnReturnDevice(@PathVariable("id") String id) {
        return null;
    }
}
