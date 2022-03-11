package com.sicau.devicemanagement.service;

import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.domain.RentApply;
import com.sicau.devicemanagement.domain.model.ApplyForm;
import com.sicau.devicemanagement.domain.model.LoginUser;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public interface IRentApplyService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public RentApply selectRentApplyById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param rentApply 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<RentApply> selectRentApplyList(RentApply rentApply);

    /**
     * 新增【请填写功能名称】
     * 
     * @param applyForm 【申请表单】
     * @return 结果
     */
    public AjaxResult insertRentApply(ApplyForm applyForm, LoginUser loginUser);

    /**
     * 修改【请填写功能名称】
     * 
     * @param rentApply 【请填写功能名称】
     * @return 结果
     */
    public int updateRentApply(RentApply rentApply);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteRentApplyByIds(String[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteRentApplyById(String id);

    /**
     * 老师开始使用设备
     *
     * @param id  申请使用id
     * @author sora
     * @date 2022/01/19
     */
    void teacherStartUsingDevice(String id);

    /**
     * 学生开始使用设备
     *
     * @param id  申请使用id
     * @author sora
     * @date 2022/01/19
     */
    void studentStartUsingDevice(String id);

    /**
     * 申请归还
     *
     * @param uid uid
     * @param id  id
     * @author sora
     * @date 2022/01/21
     */
    void finishUse(String uid, String id);

    /**
     * 确认物品已归还
     *
     * @param id  id
     * @author sora
     * @date 2022/01/21
     */
    void confirmReturn(String id);

    /**
     * 设备报修
     *
     * @param id id
     * @author sora
     * @date 2022/01/21
     */
    void deviceBroken(String id);

    /**
     * 是否是借用设备的管理者
     *
     * @param uid uid
     * @param id  id
     * @return boolean
     * @author sora
     * @date 2022/01/21
     */
    boolean isDeviceOwner(String uid, String id);

    /**
     * 判断该时间段用户是否能够使用该设备
     *
     * @param uid uid
     * @param id  申请使用id
     * @return boolean
     * @author sora
     * @date 2022/01/19
     */
    boolean isUserAccessDevice(String uid, String id);

    /**
     * 获取需要老师审核的申请
     * @return
     */
    AjaxResult queryApplyCheckedByTeacher(LoginUser loginUser);

    /**
     * 获取需要superAdmin 省核的申请
     * @return
     */
    AjaxResult queryApplyCheckedBySuperAdmin(String  userId);
    /*
     * 判断用户是否可以结束使用设备
     *
     * @param uid uid
     * @param id  id
     * @return boolean
     * @author sora
     * @date 2022/02/16
     */
    boolean isUserFinishDevice(String uid, String id);

    List<RentApply> getReturnApply(String uid, int size, int page);

    /**
     * 处理请求
     * @param rid 请求记录的id
     * @param res 处理的结果 true or false
     * @param reason 拒绝的原因
     * @param loginUser 用户信息
     * @return
     */
    AjaxResult handleApply(String rid, Integer res,String reason,LoginUser loginUser);

}
