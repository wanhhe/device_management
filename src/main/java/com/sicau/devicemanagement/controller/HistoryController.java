package com.sicau.devicemanagement.controller;

import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.common.utils.PageUtils;
import com.sicau.devicemanagement.common.utils.StringUtils;
import com.sicau.devicemanagement.common.utils.file.DateUtils;
import com.sicau.devicemanagement.domain.RentApply;
import com.sicau.devicemanagement.domain.model.LoginUser;
import com.sicau.devicemanagement.service.impl.HistoryService;
import com.sicau.devicemanagement.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("system/history")
public class HistoryController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private HistoryService historyService;

    /**
     * 查询自己的设备外借数据
     *
     * @param uid   uid
     * @param token 令牌
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/26
     */
    @PreAuthorize("hasAnyRole('teacher','admin','superAdmin')")
    @GetMapping("/own/{uid}/{type}")
    public AjaxResult getOwnDeviceRentedHistory(@PathVariable("uid") String uid,
                                                @PathVariable("type") String type,
                                                @RequestParam("size") int size,
                                                @RequestParam("page") int page,
                                                @RequestHeader("Authorization") String token) {
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (!uid.equals(loginUser.getUserId())) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "token校验失败");
        }
        if (!loginUser.getRole().equals(Constants.ROLE_TEACHER)) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "没有权限");
        }
        if (PageUtils.pageCuttingIllegal(size, page)) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "参数错误");
        }
        return AjaxResult.success(historyService.getOwnDeviceBorrowHistory(uid, type, size, page));
    }

    /**
     * 查询自己的借用历史数据
     *
     * @param uid   uid
     * @param token 令牌
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/26
     */
    @PreAuthorize("hasAnyRole('student','teacher','admin','superAdmin')")
    @GetMapping("/self/{uid}")
    public AjaxResult getSelfRentHistory(@PathVariable("uid") String uid,
                                         @RequestParam("size") int size,
                                         @RequestParam("page") int page,
                                         @RequestHeader("Authorization") String token) {
        // 判断身份
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (!uid.equals(loginUser.getUserId())) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "token校验失败");
        }
        if (PageUtils.pageCuttingIllegal(size, page)) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "参数错误");
        }
        return AjaxResult.success(historyService.getBorrowHistoryByUid(uid, size, page));
    }

    @PreAuthorize("hasAnyRole('student','teacher','admin','superAdmin')")
    @GetMapping("/self/{uid}/{size}/{page}/{purpose}")
    public AjaxResult getSelfUnreturnHistory(@PathVariable("uid") String uid,
                                             @PathVariable("size") int size,
                                             @PathVariable("page") int page,
                                             @PathVariable("purpose") int purpose,
                                             @RequestHeader("Authorization") String token) {
        // 判断身份
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (!uid.equals(loginUser.getUserId())) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "token校验失败");
        }
        if (PageUtils.pageCuttingIllegal(size, page)) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "参数错误");
        }
        if (purpose < 0 || purpose > 5) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "参数错误");
        }
        return AjaxResult.success(historyService.getBorrowHistoryByUid(uid, size, page, purpose));

    }

    /**
     * 通过名字查询下属学生的设备租借历史
     *
     * @param uid  uid
     * @param name 的名字
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/26
     */
    @PreAuthorize("hasAnyRole('teacher','admin','superAdmin')")
    @GetMapping("/sub/name/{uid}/{name}")
    public AjaxResult getSubordinateRentHistoryByName(@PathVariable("uid") String uid, @PathVariable("name") String name) {
        return AjaxResult.success(historyService.getSubBorrowHistoryByName(uid, name));
    }

    /**
     * 通过设备类型查询下属学生的设备借用历史
     *
     * @param uid  uid
     * @param type 类型
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/26
     */
    @PreAuthorize("hasAnyRole('teacher','admin','superAdmin')")
    @GetMapping("/sub/type/{uid}/{type}")
    public AjaxResult getSubordinateRentHistoryByDeviceType(@PathVariable("uid") String uid,
                                                            @PathVariable("type") String type,
                                                            @RequestParam("size") int size,
                                                            @RequestParam("page") int page) {
        if (PageUtils.pageCuttingIllegal(size, page)) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "参数错误");
        }
        return AjaxResult.success(historyService.getSubBorrowHistoryByDevice(uid, type, size, page));
    }

    /**
     * 按设备型号得到得到所有租赁设备的历史
     *
     * @param type 类型id
     * @param size 大小
     * @param page 页面
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/16
     */
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @GetMapping("/all/{type}")
    public AjaxResult getAllRentHistoryByDevice(@PathVariable("type") String type,
                                                @RequestParam("size") int size,
                                                @RequestParam("page") int page) {
        if (PageUtils.pageCuttingIllegal(size, page)) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "参数错误");
        }
        if (!StringUtils.isNotEmpty(type)) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "参数错误");
        }
        return AjaxResult.success(historyService.adminGetBorrowHistoryByDevice(type, size, page));
    }

    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @GetMapping("/all/{size}/{page}")
    public AjaxResult getAllRentHistory(@PathVariable("size") int size, @PathVariable("page") int page) {
        if (PageUtils.pageCuttingIllegal(size, page)) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "参数错误");
        }
        return AjaxResult.success(historyService.getAllBorrowHistory(size, page));
    }

    /**
     * 获得某台设备的租用历史
     *
     * @param id 该设备id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/18
     */
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @GetMapping("/{id}")
    public AjaxResult getRentHistory(@PathVariable("id") String id) {
        return AjaxResult.success(historyService.adminGetBorrowHistoryByDeviceId(id));
    }

    /**
     * 按照数量导出某个设备的借用历史
     *
     * @param id       id
     * @param filename 文件名
     * @param size     大小
     * @param page     页面
     * @param response 响应
     * @author sora
     * @date 2022/02/19
     */
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @GetMapping("/excel/device/{id}/{filename}")
    public void exportDeviceHistory(@PathVariable("id") String id, @PathVariable("filename") String filename,
                                    @RequestParam("size") int size,
                                    @RequestParam("page") int page,
                                    HttpServletResponse response) {
        if (PageUtils.pageCuttingIllegal(size, page)) {
            return;
        }
        if (!StringUtils.isNotEmpty(filename)) {
            return;
        }
        List<RentApply> rentApplies = historyService.selectDeviceByNum(id, size, page);
        ExcelUtil<RentApply> util = new ExcelUtil<RentApply>(RentApply.class);
        util.exportExcel(response, rentApplies, "导出一个时间段的设备使用情况");
    }

    /**
     * 按照时间段导出某个设备的借用历史
     *
     * @param id       id
     * @param begin    开始
     * @param end      结束
     * @param filename 文件名
     * @param response 响应
     * @author sora
     * @date 2022/02/19
     */
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @GetMapping("/excel/device/{id}/{begin}/{end}/{filename}")
    public void exportDeviceHistory(@PathVariable("id") String id,
                                    @PathVariable("begin") String begin,
                                    @PathVariable("end") String end,
                                    @PathVariable("filename") String filename,
                                          HttpServletResponse response) {
        if (PageUtils.timeIllegal(begin, end)) {
            return;
        }
        if (!StringUtils.isNotEmpty(filename)) {
            return;
        }
        List<RentApply> rentApplies = historyService.selectDeviceByDay(id, begin, end);
        ExcelUtil<RentApply> util = new ExcelUtil<RentApply>(RentApply.class);
        util.exportExcel(response, rentApplies, "导出一个时间段的设备使用情况");
    }

    /**
     * 导出某个设备类型的借用历史
     *
     * @param id       id
     * @param begin    开始日期 yyyy-MM-dd
     * @param end      结束日期
     * @param filename 文件名
     * @param response 响应
     * @author sora
     * @date 2022/02/19
     */
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @GetMapping("/excel/type/{id}/{begin}/{end}/{filename}")
    public void exportDeviceTypeHistory(@PathVariable("id") String id,
                                        @PathVariable("begin") String begin,
                                        @PathVariable("end") String end,
                                        @PathVariable("filename") String filename,
                                        HttpServletResponse response) {
        if (PageUtils.timeIllegal(begin, end)) {
            return;
        }
        if (!StringUtils.isNotEmpty(filename)) {
            return;
        }
        List<RentApply> rentApplies = historyService.selectDeviceTypeByTime(id, begin, end);
        ExcelUtil<RentApply> util = new ExcelUtil<RentApply>(RentApply.class);
        util.exportExcel(response, rentApplies, "导出一个时间段的类型设备使用情况");
    }

    /**
     * 导出一种设备类型的使用历史
     *
     * @param id       id
     * @param filename 文件名
     * @param size     大小
     * @param page     页面
     * @param response 响应
     * @author sora
     * @date 2022/02/19
     */
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @GetMapping("/excel/type/{id}/{filename}")
    public void exportDeviceTypeHistory(@PathVariable("id") String id,
                                              @PathVariable("filename") String filename,
                                              @RequestParam("size") int size,
                                              @RequestParam("page") int page,
                                              HttpServletResponse response) {
        if (PageUtils.pageCuttingIllegal(size, page)) {
            return;
        }
        if (!StringUtils.isNotEmpty(filename)) {
            return;
        }
        List<RentApply> rentApplies = historyService.selectDeviceTypeByNum(id, size, page);
        ExcelUtil<RentApply> util = new ExcelUtil<RentApply>(RentApply.class);
        util.exportExcel(response, rentApplies, "导出一定数量的类型设备使用情况");
    }

    /**
     * 导出一个时间段内的申请历史
     *
     * @param begin    开始日期 yyyy-MM-dd
     * @param end      结束
     * @param basis    依据
     * @param method   倒叙还是顺序
     * @param filename 文件名
     * @param response 响应
     * @author sora
     * @date 2022/02/19
     */
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @GetMapping("/excel/rent/{begin}/{end}/{basis}/{method}/{filename}")
    public void exportRentHistory(@PathVariable("begin") String begin,
                                  @PathVariable("end") String end,
                                  @PathVariable("basis") String basis,
                                  @PathVariable("method") String method,
                                  @PathVariable("filename") String filename,
                                  HttpServletResponse response) {
        if (PageUtils.timeIllegal(begin, end)) {
            return;
        }
        if (!StringUtils.isNotEmpty(filename)) {
            return;
        }
        List<RentApply> rentApplies = historyService.selectRentApply(begin, end, basis, method);
        if (rentApplies == null) {
            return;
        }
        ExcelUtil<RentApply> util = new ExcelUtil<RentApply>(RentApply.class);
        util.exportExcel(response, rentApplies, "导出一个时间段内的申请历史");
    }

    /**
     * 导出一个数量的申请历史
     *
     * @param filename 文件名
     * @param size     大小
     * @param page     页面
     * @param response 响应
     * @author sora
     * @date 2022/02/19
     */
    @PreAuthorize("hasAnyRole('admin','superAdmin')")
    @GetMapping("/excel/rent/{filename}")
    public void exportRentHistory(@PathVariable("filename") String filename,
                                  @RequestParam("size") int size,
                                  @RequestParam("page") int page,
                                  HttpServletResponse response) {
        if (PageUtils.pageCuttingIllegal(size, page)) {
            return;
        }
        if (!StringUtils.isNotEmpty(filename)) {
            return;
        }
        List<RentApply> rentApplies = historyService.selectRentApply(size, page);
        if (rentApplies == null) {
            return;
        }
        ExcelUtil<RentApply> util = new ExcelUtil<RentApply>(RentApply.class);
        util.exportExcel(response, rentApplies, "导出一个时间段内的申请历史");
    }
}
