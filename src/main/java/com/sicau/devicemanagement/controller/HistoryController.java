package com.sicau.devicemanagement.controller;

import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.utils.ExcelUtil;
import com.sicau.devicemanagement.common.utils.StringUtils;
import com.sicau.devicemanagement.common.utils.file.DateUtils;
import com.sicau.devicemanagement.domain.RentApply;
import com.sicau.devicemanagement.domain.model.LoginUser;
import com.sicau.devicemanagement.service.impl.HistoryService;
import com.sicau.devicemanagement.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/own/{uid}/{type}")
    public AjaxResult getOwnDeviceRentedHistory(@PathVariable("uid") String uid,
                                                @PathVariable("type") String type,
                                                @RequestHeader("Authorization") String token) {
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (!uid.equals(loginUser.getUserId())) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "token校验失败");
        }
        if (!loginUser.getRole().equals(Constants.TEACHER)) {
            return AjaxResult.error(HttpStatus.FORBIDDEN, "没有权限");
        }
        return AjaxResult.success(historyService.getOwnDeviceBorrowHistory(uid, type));
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
    @GetMapping("/self/{uid}")
    public AjaxResult getSelfRentHistory(@PathVariable("uid") String uid, @RequestHeader("Authorization") String token) {
        // 判断身份
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (!uid.equals(loginUser.getUserId())) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "token校验失败");
        }
        return AjaxResult.success(historyService.getBorrowHistoryByUid(uid));
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
    @GetMapping("/sub/{uid}/{name}")
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
    @GetMapping("/sub/{uid}/{type}")
    public AjaxResult getSubordinateRentHistoryByDeviceType(@PathVariable("uid") String uid, @PathVariable("type") String type) {
        return AjaxResult.success(historyService.getSubBorrowHistoryByDevice(uid, type));
    }

//    /**
//     * 通过角色查询借用历史
//     *
//     * @param role 角色
//     * @param tid  tid
//     * @return {@link AjaxResult }
//     * @author sora
//     * @date 2022/01/26
//     */
//    @GetMapping("/all/{role}/{tid}")
//    public AjaxResult getAllRentHistoryByRole(@PathVariable("role") String role,
//                                              @PathVariable(value = "tid", required = false) String tid) {
//        if (!role.equals(Constants.TEACHER) && !role.equals(Constants.STUDENT)) {
//            return AjaxResult.error(HttpStatus.BAD_REQUEST, "请选择正确的角色");
//        }
//        return AjaxResult.success(historyService.adminGetBorrowHistoryByRole(role));
//    }

    /**
     * 按设备型号得到得到所有租赁设备的历史
     *
     * @param type 类型id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/26
     */
    @GetMapping("/all/{type}")
    public AjaxResult getAllRentHistoryByDevice(@PathVariable("type") String type) {
        return AjaxResult.success(historyService.adminGetBorrowHistoryByDevice(type));
    }

    /**
     * 获得某台设备的租用历史
     *
     * @param id 该设备id
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/18
     */
    @GetMapping("/{id}")
    public AjaxResult getRentHistory(@PathVariable("id") String id) {
        return AjaxResult.success(historyService.adminGetBorrowHistoryByDeviceId(id));
    }

    /**
     * 按照数量导出某个设备的借用历史
     *
     * @param id       id
     * @param size     大小
     * @param page     页面
     * @param response 响应
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/12
     */
    @GetMapping("/excel/device/{id}")
    public AjaxResult exportDeviceHistory(@PathVariable("id") String id,
                                    @RequestParam("size") int size,
                                    @RequestParam("page") int page,
                                    HttpServletResponse response) {
        if (pageCuttingIllegal(size, page)) {
            return AjaxResult.error(HttpStatus.BAD_METHOD, "参数错误");
        }
        List<RentApply> rentApplies = historyService.selectDeviceByNum(id, size, page);
        ExcelUtil<RentApply> util = new ExcelUtil<RentApply>(RentApply.class);
        util.exportExcel(response, rentApplies, "导出一个时间段的设备使用情况");
        return AjaxResult.success();

    }

    /**
     * 按照时间段导出某个设备的借用历史
     *
     * @param id       id
     * @param begin    开始
     * @param end      结束
     * @param response 响应
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/12
     */
    @GetMapping("/excel/device/{id}/{begin}/{end}")
    public AjaxResult exportDeviceHistory(@PathVariable("id") String id,
                                          @PathVariable("begin") String begin,
                                          @PathVariable("end") String end,
                                          HttpServletResponse response) {
        if (timeIllegal(begin, end)) {
            return AjaxResult.error(HttpStatus.BAD_METHOD, "参数错误");
        }
        List<RentApply> rentApplies = historyService.selectDeviceByDay(id, begin, end);
        ExcelUtil<RentApply> util = new ExcelUtil<RentApply>(RentApply.class);
        util.exportExcel(response, rentApplies, "导出一个时间段的设备使用情况");
        return AjaxResult.success();
    }

    /**
     * 导出某个设备类型的借用历史
     *
     * @param id       id
     * @param begin    开始日期 yyyy-MM-dd
     * @param end      结束日期
     * @param response 响应
     * @author sora
     * @date 2022/02/10
     */
    @GetMapping("/excel/type/{id}/{begin}/{end}")
    public AjaxResult exportDeviceTypeHistory(@PathVariable("id") String id,
                                        @PathVariable("begin") String begin,
                                        @PathVariable("end") String end,
                                        HttpServletResponse response) {
        if (timeIllegal(begin, end)) {
            return AjaxResult.error(HttpStatus.BAD_METHOD, "参数错误");
        }
        List<RentApply> rentApplies = historyService.selectDeviceTypeByTime(id, begin, end);
        ExcelUtil<RentApply> util = new ExcelUtil<RentApply>(RentApply.class);
        util.exportExcel(response, rentApplies, "导出一个时间段的类型设备使用情况");
        return AjaxResult.success();
    }

    /**
     * 导出一种设备类型的使用历史
     *
     * @param id       id
     * @param size     大小
     * @param page     页面
     * @param response 响应
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/12
     */
    @GetMapping("/excel/type/{id}")
    public AjaxResult exportDeviceTypeHistory(@PathVariable("id") String id,
                                              @RequestParam("size") int size,
                                              @RequestParam("page") int page,
                                              HttpServletResponse response) {
        if (pageCuttingIllegal(size, page)) {
            return AjaxResult.error(HttpStatus.BAD_METHOD, "参数错误");
        }
        List<RentApply> rentApplies = historyService.selectDeviceTypeByNum(id, size, page);
        ExcelUtil<RentApply> util = new ExcelUtil<RentApply>(RentApply.class);
        util.exportExcel(response, rentApplies, "导出一定数量的类型设备使用情况");
        return AjaxResult.success();
    }

    /**
     * 导出一个时间段内的申请历史
     *
     * @param begin    开始日期 yyyy-MM-dd
     * @param end      结束
     * @param response 响应
     * @author sora
     * @date 2022/02/10
     */
    @PostMapping("/excel/rent/{begin}/{end}/{basis}/{method}")
    public AjaxResult exportRentHistory(@PathVariable("begin") String begin,
                                  @PathVariable("end") String end,
                                  @PathVariable("basis") String basis,
                                  @PathVariable("method") String method,
                                  HttpServletResponse response) {
        if (timeIllegal(begin, end)) {
            return AjaxResult.error(HttpStatus.BAD_METHOD, "参数错误");
        }
        List<RentApply> rentApplies = historyService.selectRentApply(begin, end, basis, method);
        if (rentApplies == null) {
            return AjaxResult.error(HttpStatus.BAD_METHOD, "参数错误");
        }
        ExcelUtil<RentApply> util = new ExcelUtil<RentApply>(RentApply.class);
        util.exportExcel(response, rentApplies, "导出一个时间段内的申请历史");
        return AjaxResult.success();
    }

    /**
     * 导出一个数量的申请历史
     *
     * @param size     大小
     * @param page     页面
     * @param response 响应
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/02/10
     */
    @PostMapping("/excel/rent")
    public AjaxResult exportRentHistory(@RequestParam("size") int size,
                                        @RequestParam("page") int page,
                                        HttpServletResponse response) {
        if (pageCuttingIllegal(size, page)) {
            return AjaxResult.error(HttpStatus.BAD_METHOD, "参数错误");
        }
        List<RentApply> rentApplies = historyService.selectRentApply(size, page);
        if (rentApplies == null) {
            return AjaxResult.error(HttpStatus.ERROR, "查询出错");
        }
        ExcelUtil<RentApply> util = new ExcelUtil<RentApply>(RentApply.class);
        util.exportExcel(response, rentApplies, "导出一个时间段内的申请历史");
        return AjaxResult.success();
    }

    /**
     * 检查时间参数是否非法
     *
     * @param begin 开始
     * @param end   结束
     * @return boolean
     * @author sora
     * @date 2022/02/12
     */
    private boolean timeIllegal(String begin, String end) {
        if (!StringUtils.isNotEmpty(begin) || !StringUtils.isNotEmpty(end)) {
            return true;
        }
        if (!DateUtils.dateStrIsValid(begin) || !DateUtils.dateStrIsValid(end)) {
            return true;
        }
        // 比较 begin 是否在 end 前面
        return !DateUtils.before(begin, end, DateUtils.YYYY_MM_DD);
    }

    /**
     * 检查分页参数是否非法
     *
     * @param size 大小
     * @param page 页面
     * @return boolean
     * @author sora
     * @date 2022/02/12
     */
    private boolean pageCuttingIllegal(int size, int page) {
        if (size <= 10 || page <= 0) {
            return true;
        }
        return size >= 3000;
    }
}
