package com.sicau.devicemanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sicau.devicemanagement.common.core.model.LoginUser;
import com.sicau.devicemanagement.common.utils.StringUtils;
import com.sicau.devicemanagement.domain.Teacher;
import com.sicau.devicemanagement.entity.User;
import com.sicau.devicemanagement.mapper.TeacherMapper;
import com.sicau.devicemanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherMapper teacherMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
//        User user = userService.selectUserByUserName(username);
        //================== TODO 测试写法
        Teacher teacher = teacherMapper.selectOne(new QueryWrapper<Teacher>().eq("name", username));
        User user = new User();
        BeanUtils.copyProperties(teacher,user);
        //==================
        if (StringUtils.isNull(user))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("用户不存在");
        }
        // TODO 为了测试把下面的代码注释
//        else if (user.getIsDelete())
//        {
//            log.info("登录用户：{} 已被删除.", username);
//            throw new UsernameNotFoundException("用户被删除");
//        }
        System.out.println("==========");
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(User user)
    {
        return new LoginUser(user.getUid(), user,user.getRole());
    }
}
