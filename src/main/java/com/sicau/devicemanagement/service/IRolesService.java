package com.sicau.devicemanagement.service;


import com.sicau.devicemanagement.domain.Roles;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public interface IRolesService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Roles selectRolesById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param roles 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Roles> selectRolesList(Roles roles);

    /**
     * 新增【请填写功能名称】
     * 
     * @param roles 【请填写功能名称】
     * @return 结果
     */
    public int insertRoles(Roles roles);

    /**
     * 修改【请填写功能名称】
     * 
     * @param roles 【请填写功能名称】
     * @return 结果
     */
    public int updateRoles(Roles roles);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteRolesByIds(String[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteRolesById(String id);
}
