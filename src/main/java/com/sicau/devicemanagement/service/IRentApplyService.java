package com.sicau.devicemanagement.service;

import com.sicau.devicemanagement.domain.RentApply;

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
     * @param rentApply 【请填写功能名称】
     * @return 结果
     */
    public int insertRentApply(RentApply rentApply);

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
}
