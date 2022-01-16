package com.sicau.devicemanagement.service;


import com.sicau.devicemanagement.domain.Lab;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public interface ILabService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Lab selectLabById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param lab 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Lab> selectLabList(Lab lab);

    /**
     * 新增【请填写功能名称】
     * 
     * @param lab 【请填写功能名称】
     * @return 结果
     */
    public int insertLab(Lab lab);

    /**
     * 修改【请填写功能名称】
     * 
     * @param lab 【请填写功能名称】
     * @return 结果
     */
    public int updateLab(Lab lab);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteLabByIds(String[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteLabById(String id);
}
