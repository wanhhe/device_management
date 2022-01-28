package com.sicau.devicemanagement.controller;

import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.domain.model.LoginUser;
import com.sicau.devicemanagement.service.impl.HistoryService;
import com.sicau.devicemanagement.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 通过角色查询借用历史
     *
     * @param role 角色
     * @param tid  tid
     * @return {@link AjaxResult }
     * @author sora
     * @date 2022/01/26
     */
    @GetMapping("/all/{role}/{tid}")
    public AjaxResult getAllRentHistoryByRole(@PathVariable("role") String role,
                                              @PathVariable(value = "tid", required = false) String tid) {
        if (!role.equals(Constants.TEACHER) && !role.equals(Constants.STUDENT)) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST, "请选择正确的角色");
        }
        return AjaxResult.success(historyService.adminGetBorrowHistoryByRole(role));
    }

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
}
