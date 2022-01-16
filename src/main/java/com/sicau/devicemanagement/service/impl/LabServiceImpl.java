package com.sicau.devicemanagement.service.impl;

import java.util.List;

import com.sicau.devicemanagement.domain.Lab;
import com.sicau.devicemanagement.mapper.LabMapper;
import com.sicau.devicemanagement.service.ILabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@Service
public class LabServiceImpl implements ILabService
{
    @Autowired
    private LabMapper labMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Lab selectLabById(String id)
    {
        return labMapper.selectLabById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param lab 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Lab> selectLabList(Lab lab)
    {
        return labMapper.selectLabList(lab);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param lab 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertLab(Lab lab)
    {
        return labMapper.insertLab(lab);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param lab 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateLab(Lab lab)
    {
        return labMapper.updateLab(lab);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteLabByIds(String[] ids)
    {
        return labMapper.deleteLabByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteLabById(String id)
    {
        return labMapper.deleteLabById(id);
    }
}
