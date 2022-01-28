package com.sicau.devicemanagement.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
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
     * 修改设备状态
     *
     * @param id 该设备id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/18
     */
    @PutMapping("/{id}/{status}")
    public AjaxResult updateDeviceStatus(@PathVariable("id") String id, @PathVariable("status") String status) {
        if (status.equals(Constants.DEVICE_NATURAL) || status.equals(Constants.DEVICE_UNDERREPAIR)) {
            deviceService.updateDeviceStatus(id, status);
        } else if (status.equals(Constants.DEVICE_BROKEN)) {
            deviceService.deviceBroken(id);
        } else {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "请输入正确的参数");
        }
        return AjaxResult.success();
    }
}
