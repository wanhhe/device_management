package com.sicau.devicemanagement.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sicau.devicemanagement.domain.Teacher;
import org.springframework.stereotype.Component;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@Component
public interface TeacherMapper extends BaseMapper<Teacher>
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
     * 根据工号选择uid
     *
     * @param employeeId 雇员id
     * @return {@link String }
     * @author sora
     * @date 2022/01/19
     */
    public String selectUidByEmployeeId(String employeeId);

    /**
     * 查询管理员uid
     *
     * @return {@link List<String> }
     * @author sora
     * @date 2022/01/19
     */
    public List<String> selectAdminUid();

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
     * 删除【请填写功能名称】
     * 
     * @param uid 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteTeacherByUid(String uid);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param uids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTeacherByUids(String[] uids);
}
