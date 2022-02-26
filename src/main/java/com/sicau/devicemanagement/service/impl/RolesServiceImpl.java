package com.sicau.devicemanagement.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.domain.Roles;
import com.sicau.devicemanagement.domain.Teacher;
import com.sicau.devicemanagement.mapper.RolesMapper;
import com.sicau.devicemanagement.mapper.TeacherMapper;
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

    @Autowired
    private TeacherMapper teacherMapper;

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

    @Override
    public List<Teacher> getAdmin() {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("role_id", Constants.ROLE_ADMIN_ID).or().eq("role_id", Constants.ROLE_SUPER_ADMIN_ID);
        return teacherMapper.selectList(teacherQueryWrapper);
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

    @Override
    public boolean checkRole(String id, String role) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("uid", id).eq("role_id", role);
        Integer count = teacherMapper.selectCount(teacherQueryWrapper);
        return count > 0;
    }

    @Override
    public int addAdmin(String id) {
        return updateRole(id, Constants.ROLE_ADMIN_ID);
    }

    @Override
    public int updateSuperAdmin(String sid, String id) {
        int i = updateRole(id, Constants.ROLE_SUPER_ADMIN_ID);
        if (i < 1) {
            return -1;
        }
        return updateRole(sid, Constants.ROLE_ADMIN_ID);
    }

    @Override
    public int cancelAdmin(String id) {
        return updateRole(id, Constants.ROLE_TEACHER_ID);
    }

    private int updateRole(String id, String roleId) {
        UpdateWrapper<Teacher> teacherUpdateWrapper = new UpdateWrapper<>();
        teacherUpdateWrapper.set("role_id", roleId).eq("uid", id);
        return teacherMapper.update(null, teacherUpdateWrapper);
    }
}
