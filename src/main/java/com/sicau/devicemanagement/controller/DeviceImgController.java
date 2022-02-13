package com.sicau.devicemanagement.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.core.page.TableDataInfo;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.domain.DeviceImg;
import com.sicau.devicemanagement.service.IDeviceImgService;
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

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@RestController
@RequestMapping("/system/img")
public class DeviceImgController extends BaseController
{
    @Autowired
    private IDeviceImgService deviceImgService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:img:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceImg deviceImg)
    {
        startPage();
        List<DeviceImg> list = deviceImgService.selectDeviceImgList(deviceImg);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:img:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceImg deviceImg)
    {
        List<DeviceImg> list = deviceImgService.selectDeviceImgList(deviceImg);
        ExcelUtil<DeviceImg> util = new ExcelUtil<DeviceImg>(DeviceImg.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:img:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(deviceImgService.selectDeviceImgById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:img:add')")
    @PostMapping
    public AjaxResult add(@RequestBody DeviceImg deviceImg)
    {
        return toAjax(deviceImgService.insertDeviceImg(deviceImg));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:img:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceImg deviceImg)
    {
        return toAjax(deviceImgService.updateDeviceImg(deviceImg));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:img:remove')")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(deviceImgService.deleteDeviceImgByIds(ids));
    }

    /**
     * 按类型删除设备
     *
     * @param type 类型
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/08
     */
    @DeleteMapping("/type/{type}")
    public AjaxResult delByType(@PathVariable String type) {
        return toAjax(deviceImgService.deleteDeviceImgByType(type));
    }
}
