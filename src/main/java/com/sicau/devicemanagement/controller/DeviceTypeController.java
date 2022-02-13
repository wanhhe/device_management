package com.sicau.devicemanagement.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.page.TableDataInfo;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.domain.DeviceType;
import com.sicau.devicemanagement.domain.model.LoginUser;
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

    @Autowired
    private TokenService tokenService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:type:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceType deviceType)
    {
        startPage();
        List<DeviceType> list = deviceTypeService.selectDeviceTypeList(deviceType);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:type:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceType deviceType)
    {
        List<DeviceType> list = deviceTypeService.selectDeviceTypeList(deviceType);
        ExcelUtil<DeviceType> util = new ExcelUtil<DeviceType>(DeviceType.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:type:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(deviceTypeService.selectDeviceTypeById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:type:add')")
    @PostMapping
    public AjaxResult add(@RequestBody DeviceType deviceType)
    {
        return toAjax(deviceTypeService.insertDeviceType(deviceType));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:type:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceType deviceType)
    {
        return toAjax(deviceTypeService.updateDeviceType(deviceType));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:type:remove')")
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
    @PostMapping("/type")
    public AjaxResult addDeviceType(@RequestBody DeviceType deviceType, MultipartFile[] files) {
        int[] ints = deviceTypeService.addDeviceType(deviceType, files);
        if (ints == null) {
            return AjaxResult.error(HttpStatus.ERROR, "添加失败");
        }
        return AjaxResult.success(ints);
    }
}
