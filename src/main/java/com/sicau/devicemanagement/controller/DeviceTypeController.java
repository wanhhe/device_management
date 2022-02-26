package com.sicau.devicemanagement.controller;

import java.util.List;

import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.page.TableDataInfo;
import com.sicau.devicemanagement.domain.DeviceType;
import com.sicau.devicemanagement.service.IDeviceTypeService;
import com.sicau.devicemanagement.service.impl.TokenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@RestController
@RequestMapping("/system/type")
public class DeviceTypeController extends BaseController
{
    @Autowired
    private IDeviceTypeService deviceTypeService;

    /**
     * 查询【请填写功能名称】列表
     */
    @GetMapping("/list")
    public TableDataInfo list(DeviceType deviceType)
    {
        startPage();
        List<DeviceType> list = deviceTypeService.selectDeviceTypeList(deviceType);
        return getDataTable(list);
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(deviceTypeService.selectDeviceTypeById(id));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody DeviceType deviceType)
    {
        return toAjax(deviceTypeService.updateDeviceType(deviceType));
    }

    /**
     * 删除【请填写功能名称】
     */
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(deviceTypeService.deleteDeviceTypeByIds(ids));
    }

    /**
     * 添加设备类型
     *
     * @param deviceType 设备类型
     * @param files      文件
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/09
     */
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @PostMapping("/type")
    public AjaxResult addDeviceType(DeviceType deviceType, MultipartFile[] files) {
        int ints = deviceTypeService.addDeviceType(deviceType, files);
        if (ints == -1) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "已有重名设备");
        }
        return AjaxResult.success(ints);
    }
}
