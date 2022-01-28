package com.sicau.devicemanagement.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sicau.devicemanagement.common.utils.file.DateUtils;
import com.sicau.devicemanagement.domain.Student;
import com.sicau.devicemanagement.mapper.StudentMapper;
import com.sicau.devicemanagement.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@Service
public class StudentServiceImpl implements IStudentService
{
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param uid 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Student selectStudentByUid(String uid)
    {
        return studentMapper.selectStudentByUid(uid);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param student 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Student> selectStudentList(Student student)
    {
        return studentMapper.selectStudentList(student);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param student 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertStudent(Student student)
    {
        return studentMapper.insertStudent(student);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param student 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateStudent(Student student)
    {
        return studentMapper.updateStudent(student);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param uids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteStudentByUids(String[] uids)
    {
        return studentMapper.deleteStudentByUids(uids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param uid 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteStudentByUid(String uid)
    {
        return studentMapper.deleteStudentByUid(uid);
    }

    /**
     * 封禁学生
     *
     * @param uids uid
     * @return int
     * @author sora
     * @date 2022/01/28
     */
    @Override
    public int banStudentByUids(String[] uids) {
        return studentMapper.banStudentByUids(uids);
    }

    @Override
    public int banStudentByUid(String uid) {
        return studentMapper.banStudentByUid(uid);
    }

    /**
     * 判断是否是学生的指导老师
     *
     * @param tid tid
     * @param sid sid
     * @return boolean
     * @author sora
     * @date 2022/01/28
     */
    @Override
    public boolean isStudentMaster(String tid, String sid) {
        return tid.equals(studentMapper.selectTeacherId(sid));
    }

    /**
     * 延长学生账号使用时间
     *
     * @param sid  sid
     * @param week 周
     * @author sora
     * @date 2022/01/28
     */
    @Override
    public void extendStudent(String sid, int week) {
        // 获取当前时间
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = simpleDateFormat.format(new Date(now.getTime() + (long) week * 7 * 24 * 60 * 60 * 1000));
        UpdateWrapper<Student> studentUpdateWrapper = new UpdateWrapper<>();
        studentUpdateWrapper.eq("uid", sid);
        Student student = new Student();
        student.setExpirationDate(str);
        studentMapper.update(student, studentUpdateWrapper);
    }

    /**
     * 查询学生的账号状态
     *
     * @param sid sid
     * @return int
     * @author sora
     * @date 2022/01/28
     */
    @Override
    public int queryStudentStatus(String sid) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.select("is_del").eq("uid", sid);
        List<Student> students = studentMapper.selectList(studentQueryWrapper);
        return students.get(0).getIsDel();
    }
}
