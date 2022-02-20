package com.sicau.devicemanagement.mapper;

import com.sicau.devicemanagement.domain.Lab;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public interface LabMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Lab selectLabById(String id);

    /**
     * 根据教室号选择实验室
     *
     * @param campus 校区号
     * @param num    全国矿工工会
     * @return {@link Lab }
     * @author sora
     * @date 2022/02/18
     */
    public Lab selectLabByNum(String campus, String num);

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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteLabById(String id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLabByIds(String[] ids);
}
