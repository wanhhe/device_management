package com.sicau.devicemanagement.service;


import com.sicau.devicemanagement.domain.Teacher;

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
    public List<Teacher> selectTeacherList(Teacher teacher);

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
     * 学生修改自己账号的密码
     *
     * @param uid      uid
     * @param password 密码
     * @param verify   验证
     * @return int
     * @author sora
     * @date 2022/02/15
     */
    public int teacherUpdatePassword(String uid, String password, String verify);

    /**
     * 生成更改密码的验证码
     *
     * @param uid uid
     * @author sora
     * @date 2022/02/15
     */
    public boolean getPasswordVerify(String uid);

    /**
     * 学生修改自己账号的手机号
     *
     * @param uid    uid
     * @param tel    电话
     * @param verify 验证
     * @return int
     * @author sora
     * @date 2022/02/15
     */
    public int teacherUpdateTel(String uid, String tel, String verify);


    /**
     * 生成更改手机号的验证码
     *
     * @param uid uid
     * @author sora
     * @date 2022/02/15
     */
    public boolean getTelVerify(String uid);
}
