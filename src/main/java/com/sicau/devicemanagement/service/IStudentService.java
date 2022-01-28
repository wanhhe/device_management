package com.sicau.devicemanagement.service;


import com.sicau.devicemanagement.domain.Student;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public interface IStudentService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param uid 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Student selectStudentByUid(String uid);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param student 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Student> selectStudentList(Student student);

    /**
     * 新增【请填写功能名称】
     * 
     * @param student 【请填写功能名称】
     * @return 结果
     */
    public int insertStudent(Student student);

    /**
     * 修改【请填写功能名称】
     * 
     * @param student 【请填写功能名称】
     * @return 结果
     */
    public int updateStudent(Student student);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param uids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteStudentByUids(String[] uids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param uid 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteStudentByUid(String uid);

    /**
     * 封禁学生
     *
     * @param uids uid
     * @return int
     * @author sora
     * @date 2022/01/28
     */
    public int banStudentByUids(String[] uids);

    public int banStudentByUid(String uid);

    /**
     * 判断是否是学生的指导老师
     *
     * @param tid tid
     * @param sid sid
     * @return boolean
     * @author sora
     * @date 2022/01/28
     */
    public boolean isStudentMaster(String tid, String sid);

    /**
     * 延长学生账号使用时间
     *
     * @param sid  sid
     * @param week 周
     * @author sora
     * @date 2022/01/28
     */
    public void extendStudent(String sid, int week);

    /**
     * 查询学生账号当前状态
     *
     * @param sid sid
     * @return int
     * @author sora
     * @date 2022/01/28
     */
    public int queryStudentStatus(String sid);
}
