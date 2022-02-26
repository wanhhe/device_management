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
    @GetMapping("/list")
    public TableDataInfo list(DeviceImg deviceImg)
    {
        startPage();
        List<DeviceImg> list = deviceImgService.selectDeviceImgList(deviceImg);
        return getDataTable(list);
    }

    /**
     * 删除【请填写功能名称】
     */
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
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @DeleteMapping("/type/{type}")
    public AjaxResult delByType(@PathVariable String type) {
        return toAjax(deviceImgService.deleteDeviceImgByType(type));
    }
}
