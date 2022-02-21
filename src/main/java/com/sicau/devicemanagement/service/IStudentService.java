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
    public boolean insertStudent(Student student);

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
     * 学生修改自己账号的密码
     *
     * @param uid      uid
     * @param password 密码
     * @param verify   验证
     * @return int
     * @author sora
     * @date 2022/02/15
     */
    public int studentUpdatePassword(String uid, String password, String verify);


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
    public int studentUpdateTel(String uid, String tel, String verify);


    /**
     * 生成更改手机号的验证码
     *
     * @param uid uid
     * @author sora
     * @date 2022/02/15
     */
    public boolean getTelVerify(String uid);

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
     * 老师批量解封下属学生
     *
     * @param uids uid
     * @return int
     * @author sora
     * @date 2022/02/14
     */
    public int cancelBanStudentByUids(String[] uids);

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
