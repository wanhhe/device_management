package com.sicau.devicemanagement.service.impl;


import com.sicau.devicemanagement.common.utils.file.DateUtils;
import com.sicau.devicemanagement.domain.RentApply;
import com.sicau.devicemanagement.mapper.RentApplyMapper;
import com.sicau.devicemanagement.service.IRentApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@Service
public class RentApplyServiceImpl implements IRentApplyService
{
    @Autowired
    private RentApplyMapper rentApplyMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public RentApply selectRentApplyById(String id)
    {
        return rentApplyMapper.selectRentApplyById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param rentApply 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<RentApply> selectRentApplyList(RentApply rentApply)
    {
        return rentApplyMapper.selectRentApplyList(rentApply);
    }

    /**
     * 新增【借用申请】
     * 
     * @param rentApply 【借用申请】
     * @return 结果
     */
    @Override
    public int insertRentApply(RentApply rentApply)
    {
        rentApply.setCreateTime(DateUtils.getNowDate());
        return rentApplyMapper.insertRentApply(rentApply);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param rentApply 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateRentApply(RentApply rentApply)
    {
        return rentApplyMapper.updateRentApply(rentApply);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteRentApplyByIds(String[] ids)
    {
        return rentApplyMapper.deleteRentApplyByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteRentApplyById(String id)
    {
        return rentApplyMapper.deleteRentApplyById(id);
    }
}
