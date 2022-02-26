package com.sicau.devicemanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.domain.Roles;
import com.sicau.devicemanagement.domain.Student;
import com.sicau.devicemanagement.domain.model.LoginUser;
import com.sicau.devicemanagement.common.utils.StringUtils;
import com.sicau.devicemanagement.domain.Teacher;
import com.sicau.devicemanagement.domain.User;
import com.sicau.devicemanagement.mapper.RolesMapper;
import com.sicau.devicemanagement.mapper.StudentMapper;
import com.sicau.devicemanagement.mapper.TeacherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户验证处理
 *
 * @author ruoyi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RolesMapper rolesMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] usernameAndType = username.split("::");
        String type = usernameAndType[1];
        username = usernameAndType[0];
        /* teacher */
        if (StringUtils.equals(Constants.ROLE_TEACHER, type)) {
            Teacher teacher = teacherMapper.selectOne(new QueryWrapper<Teacher>()
                    .eq("name", username));
            check(teacher, username);
            if (StringUtils.isNotNull(teacher.getRoleId())) {
                Roles role = rolesMapper.selectOne(new QueryWrapper<Roles>()
                        .eq("id", teacher.getRoleId()));
                if (role != null) {
                    teacher.setRole(role.getName());
                }
            }
            return teacherLogin(teacher);
            /* student */
        } else {
            Student student = studentMapper.selectOne(new QueryWrapper<Student>()
                    .eq("name", username));
            check(student, username);
            if (StringUtils.isNotNull(student.getRoleId())) {
                Roles role = rolesMapper.selectOne(new QueryWrapper<Roles>()
                        .eq("id", student.getRoleId()));
                if (role != null) {
                    student.setRole(role.getName());
                }
            }
            return studentLogin(student);
        }

    }

    private void check(User user, String username) {
        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("用户不存在");
        }

        else if (user.getIsDel()==1) {
            log.info("登录用户：{} 已被删除.", username);
            throw new UsernameNotFoundException("用户被删除");
        }
    }


    private UserDetails studentLogin(Student student) {
        return new LoginUser(student.getUid(), student, student.getRole());
    }

    private UserDetails teacherLogin(Teacher teacher) {
        return new LoginUser(teacher.getUid(), teacher, teacher.getRole());

    }

}
