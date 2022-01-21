package com.sicau.devicemanagement.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sicau.devicemanagement.domain.Student;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
public interface StudentMapper extends BaseMapper<Student>
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
     * 根据学号选择uid
     *
     * @param stuNumber 斯图号
     * @return {@link String }
     * @author sora
     * @date 2022/01/19
     */
    public String selectStudentUidByStuNumber(String stuNumber);

    /**
     * 查询指导老师id
     *
     * @param uid uid
     * @return {@link String }
     * @author sora
     * @date 2022/01/19
     */
    public String selectTeacherId(String uid);

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
     * 删除【请填写功能名称】
     * 
     * @param uid 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteStudentByUid(String uid);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param uids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudentByUids(String[] uids);
}
