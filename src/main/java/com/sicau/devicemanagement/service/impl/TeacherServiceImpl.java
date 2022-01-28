package com.sicau.devicemanagement.service.impl;

import java.util.List;

import com.sicau.devicemanagement.domain.Teacher;
import com.sicau.devicemanagement.mapper.StudentMapper;
import com.sicau.devicemanagement.mapper.TeacherMapper;
import com.sicau.devicemanagement.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@Service
public class TeacherServiceImpl implements ITeacherService
{
    @Autowired
    private TeacherMapper teacherMapper;

    @Resource
    private StudentMapper studentMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param uid 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Teacher selectTeacherByUid(String uid)
    {
        return teacherMapper.selectTeacherByUid(uid);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param teacher 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Teacher> selectTeacherList(Teacher teacher)
    {
        return teacherMapper.selectTeacherList(teacher);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param teacher 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertTeacher(Teacher teacher)
    {
        return teacherMapper.insertTeacher(teacher);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param teacher 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateTeacher(Teacher teacher)
    {
        return teacherMapper.updateTeacher(teacher);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param uids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteTeacherByUids(String[] uids)
    {
        return teacherMapper.deleteTeacherByUids(uids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param uid 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteTeacherByUid(String uid)
    {
        return teacherMapper.deleteTeacherByUid(uid);
    }

    /**
     * 删除学生账号
     *
     * @param sid sid
     * @return boolean
     * @author sora
     * @date 2022/01/19
     */
    @Override
    public void banStudent(String sid) {

    }

}
