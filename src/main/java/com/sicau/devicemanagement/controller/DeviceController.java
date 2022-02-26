package com.sicau.devicemanagement.controller;

import java.util.*;
import javax.servlet.http.HttpServletResponse;

import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.page.TableDataInfo;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.common.utils.uuid.IdUtils;
import com.sicau.devicemanagement.domain.Device;
import com.sicau.devicemanagement.service.IDeviceService;
import com.sicau.devicemanagement.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * 查询【通过金额查询】列表
     */
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
    @PostMapping("/export")
    public void export(HttpServletResponse response, Device device)
    {
        List<Device> list = deviceService.selectDeviceList(device);
        ExcelUtil<Device> util = new ExcelUtil<Device>(Device.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @GetMapping("/export/{size}/{page}")
    public void export(@PathVariable("size") int size, @PathVariable("page") int page,
                        HttpServletResponse response) {
        if (size < 1 || page < 1) {
            return;
        }
        List<Device> list = deviceService.exportSize(size, page);
        ExcelUtil<Device> util = new ExcelUtil<Device>(Device.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(deviceService.selectDeviceById(id));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody Device device)
    {
        return toAjax(deviceService.updateDevice(device));
    }

    /**
     * 删除【请填写功能名称】
     */
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
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
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
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @PutMapping("/{id}/{status}")
    public AjaxResult updateDeviceStatus(@PathVariable("id") String id, @PathVariable("status") String status) {
        if (status.equals(Constants.DEVICE_NATURAL) || status.equals(Constants.DEVICE_REPAIR)) {
            deviceService.updateDeviceStatus(id, status);
        } else if (status.equals(Constants.DEVICE_BROKEN)) {
            deviceService.deviceBroken(id);
        } else {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "请输入正确的参数");
        }
        return AjaxResult.success();
    }

    /**
     * 添加设备
     *
     * @param device        设备
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/08
     */
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @PostMapping("/device")
    public AjaxResult addDevice(@RequestBody Device device) {
        List<String> illegal = deviceService.deviceIllegal(device);
        if (illegal.size() != 0) {
            return AjaxResult.error("参数错误", illegal);
        }
        device.setId(IdUtils.simpleUUID());
        device.setStatus(Constants.DEVICE_NATURAL);
        device.setUseTimes((long)0);
        device.setIsDel(Constants.NATURAL);
        List<Device> list = new LinkedList<>();
        list.add(device);
        return addDevices(list);
    }

    /**
     * 批量添加设备
     *
     * @param devices        设备
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/08
     */
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @PostMapping("/devices")
    public AjaxResult addDevices(@RequestBody List<Device> devices) {
        List<String> illegal;
        Device tmp;
        for (int i = 0; i < devices.size(); i++) {
            tmp = devices.get(i);
            illegal = deviceService.deviceIllegal(tmp);
            if (illegal.size() > 0) {
                illegal.add(String.valueOf(i+1));
                return AjaxResult.error("参数错误", illegal);
            }
        }
        deviceService.addData(devices);
        int ints = deviceService.addDevice(devices);
        if (ints == 0) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "请上传相同类型的设备");
        } else if (ints == -1) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "不存在该类型设备");
        }
        return toAjax(ints);
    }
}
