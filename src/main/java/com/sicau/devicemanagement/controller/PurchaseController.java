package com.sicau.devicemanagement.controller;

import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.BaseController;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.common.utils.PageUtils;
import com.sicau.devicemanagement.common.utils.StringUtils;
import com.sicau.devicemanagement.common.utils.file.DateUtils;
import com.sicau.devicemanagement.domain.Purchase;
import com.sicau.devicemanagement.service.impl.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/system/purchase")
public class PurchaseController extends BaseController {

    @Autowired
    private PurchaseService purchaseService;

    /**
     * 展示出想要购买的设备列表
     *
     * @param size 大小
     * @param page 页面
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/26
     */
    @PreAuthorize("hasAnyRole('teacher','admin','superAdmin')")
    @GetMapping("/list/{size}/{page}")
    public AjaxResult listWant(@PathVariable("size") int size, @PathVariable("page") int page) {
        if (PageUtils.pageCuttingIllegal(size, page)) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "参数错误");
        }
        return AjaxResult.success(purchaseService.select(size, page));
    }

    /**
     * 创建想要购买的设备
     *
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/26
     */
    @PreAuthorize("hasAnyRole('teacher','admin','superAdmin')")
    @PostMapping
    public AjaxResult create(@RequestBody Purchase purchase) {
        if (StringUtils.isEmpty(purchase.getName())) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "参数错误");
        }
        if (purchase.getCount() <= 0 || purchase.getPrice() <= 0) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "参数错误");
        }
        if (DateUtils.dateStrIsValid(purchase.getSupposePurchaseTime())) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "参数错误");
        }
        return toAjax(purchaseService.create(purchase));
    }

    /**
     * 增加一个数量
     *
     * @param id id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/26
     */
    @PreAuthorize("hasAnyRole('teacher','admin','superAdmin')")
    @GetMapping("/num/{id}/{num}")
    public AjaxResult add(@PathVariable("id") String id, @PathVariable("num") int num) {
        if (num <= 0 || num >= 15) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "参数错误");
        }
        return toAjax(purchaseService.add(id, num));
    }

    /**
     * 减少一个数量
     *
     * @param id id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/26
     */
    @PreAuthorize("hasAnyRole('teacher','admin','superAdmin')")
    @PutMapping("/num/{id}")
    public AjaxResult reduce(@PathVariable("id") String id) {
        return toAjax(purchaseService.reduce(id));
    }

    /**
     * 给我也整一个
     *
     * @param id id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/26
     */
    @PreAuthorize("hasAnyRole('teacher','admin','superAdmin')")
    @GetMapping("/{id}")
    public AjaxResult want(@PathVariable("id") String id) {
        return toAjax(purchaseService.want(id));
    }

    /**
     * 取消想要
     *
     * @param id id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/26
     */
    @PreAuthorize("hasAnyRole('teacher','admin','superAdmin')")
    @PutMapping("/{id}")
    public AjaxResult diswant(@PathVariable("id") String id) {
        return toAjax(purchaseService.diswant(id));
    }

    /**
     * 已购设备进行删除
     *
     * @param id id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/26
     */
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @DeleteMapping("/{id}")
    public AjaxResult buyed(@PathVariable("id") String id) {
        return toAjax(purchaseService.del(id));
    }

    /**
     * 导数据
     *
     * @param name     的名字
     * @param response 响应
     * @author sora
     * @date 2022/02/26
     */
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @GetMapping("/export/{name}")
    public void export(@PathVariable("name") String name, HttpServletResponse response) {
        List<Purchase> purchaseList = purchaseService.selectAll();
        ExcelUtil<Purchase> util = new ExcelUtil<Purchase>(Purchase.class);
        util.exportExcel(response, purchaseList, "待购买设备");
    }
}
