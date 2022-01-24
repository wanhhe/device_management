package com.sicau.devicemanagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sicau.devicemanagement.domain.RentApply;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public interface RentApplyMapper extends BaseMapper<RentApply>
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteRentApplyById(String id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRentApplyByIds(String[] ids);

    /**
     * 该用户在该时间段内能使用的申请
     *
     * @param uid  uid
     * @param time 时间
     * @return {@link List<String> }
     * @author sora
     * @date 2022/01/21
     */
    public List<String> selectUserAccessApply(String uid, String time);

    /**
     * 通过id查找设备管理者
     *
     * @param id id
     * @return {@link String }
     * @author sora
     * @date 2022/01/21
     */
    public String selectOwnerById(String id);

    /**
     * 更新申请状态
     *
     * @param id id
     * @return int
     * @author sora
     * @date 2022/01/21
     */
    public int updateStatus(String id, String status);

    /**
     * 查找申请同样类型设备，且申请时间在当前时间之后的所有老师id
     *
     * @param typeId id类型
     * @param time   时间
     * @return {@link List<String> }
     * @author sora
     * @date 2022/01/21
     */
    public List<String> selectNowApplyTeacherTelByDeviceTypeId(String typeId, String time);

    /**
     * 查找申请同样设备，且申请时间在当前时间之后的所有老师id
     *
     * @param typeId id类型
     * @param time   时间
     * @return {@link List<String> }
     * @author sora
     * @date 2022/01/21
     */
    public List<String> selectNowApplyStudentTelByDeviceTypeId(String typeId, String time);
}
