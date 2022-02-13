package com.sicau.devicemanagement.service;


import com.sicau.devicemanagement.domain.Teacher;
import com.sicau.devicemanagement.domain.model.TeacherQuery;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public interface ITeacherService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param uid 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Teacher selectTeacherByUid(String uid);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param teacher 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Teacher> selectTeacherList(TeacherQuery teacher);

    /**
     * 新增【请填写功能名称】
     * 
     * @param teacher 【请填写功能名称】
     * @return 结果
     */
    public int insertTeacher(Teacher teacher);

    /**
     * 修改【请填写功能名称】
     * 
     * @param teacher 【请填写功能名称】
     * @return 结果
     */
    public int updateTeacher(Teacher teacher);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param uids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteTeacherByUids(String[] uids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param uid 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteTeacherByUid(String uid);

    /**
     * 删除学生账号
     *
     * @param sid sid
     * @return boolean
     * @author sora
     * @date 2022/01/19
     */
    public void banStudent(String sid);
}
