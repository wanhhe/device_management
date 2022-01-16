package com.sicau.devicemanagement.service.impl;


import com.sicau.devicemanagement.domain.Roles;
import com.sicau.devicemanagement.mapper.RolesMapper;
import com.sicau.devicemanagement.service.IRolesService;
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
public class RolesServiceImpl implements IRolesService
{
    @Autowired
    private RolesMapper rolesMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Roles selectRolesById(String id)
    {
        return rolesMapper.selectRolesById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param roles 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Roles> selectRolesList(Roles roles)
    {
        return rolesMapper.selectRolesList(roles);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param roles 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertRoles(Roles roles)
    {
        return rolesMapper.insertRoles(roles);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param roles 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateRoles(Roles roles)
    {
        return rolesMapper.updateRoles(roles);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteRolesByIds(String[] ids)
    {
        return rolesMapper.deleteRolesByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteRolesById(String id)
    {
        return rolesMapper.deleteRolesById(id);
    }
}
