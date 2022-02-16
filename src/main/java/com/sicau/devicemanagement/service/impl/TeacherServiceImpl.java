package com.sicau.devicemanagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sicau.devicemanagement.common.core.redis.RedisCache;
import com.sicau.devicemanagement.common.utils.VerifyCodeUtils;
import com.sicau.devicemanagement.common.utils.uuid.IdUtils;
import com.sicau.devicemanagement.domain.Device;
import com.sicau.devicemanagement.domain.Student;
import com.sicau.devicemanagement.domain.Teacher;
import com.sicau.devicemanagement.domain.model.TeacherQuery;
import com.sicau.devicemanagement.mapper.DeviceMapper;
import com.sicau.devicemanagement.mapper.StudentMapper;
import com.sicau.devicemanagement.mapper.TeacherMapper;
import com.sicau.devicemanagement.service.ITeacherService;
import io.lettuce.core.RedisClient;
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

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisCache redisCache;

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
    public List<Teacher> selectTeacherList(TeacherQuery teacher)
    {
        List<Object>  list = null;
        if(teacher.getIsBelongDev())
        {
           list = deviceMapper.selectObjs(new QueryWrapper<Device>().select("manager_id").eq("is_del", 1));
            assert list != null;
            ArrayList<Teacher> res = new ArrayList<>();
            list.forEach(item->{
                Teacher teacher1 = teacherMapper.selectTeacherByUid(item.toString());
                res.add(teacher1);
            });
            return res;
        }
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
     * 学生修改自己账号的密码
     *
     * @param uid      uid
     * @param password 密码
     * @return int
     * @author sora
     * @date 2022/02/14
     */
    @Override
    public int teacherUpdatePassword(String uid, String password, String verify) {
        String code = redisCache.getCacheObject("update_password/" + uid);
        if (code == null || "".equals(code)) {
            return -1;
        }
        // 如果有缓存，更新
        if (!code.equals(verify)) {
            return -1;
        }
        UpdateWrapper<Teacher> teacherUpdateWrapper = new UpdateWrapper<>();
        teacherUpdateWrapper.eq("uid", uid).set("password", password);
        return teacherMapper.update(null, teacherUpdateWrapper);
    }

    /**
     * 生成更改密码的验证码
     *
     * @param uid uid
     * @author sora
     * @date 2022/02/15
     */
    @Override
    public boolean getPasswordVerify(String uid) {
        Teacher teacher = teacherMapper.selectTeacherByUid(uid);
        if (teacher == null) {
            return false;
        }
        String code = VerifyCodeUtils.generateVerifyCode(6);
        // 存redis
        redisCache.setCacheObject("update_password/"+uid, code, 5, TimeUnit.MINUTES);
        // 发短信
        String res = smsService.sendUpdatePasswordVerifyCode(teacher.getTel(), code);
        return "OK".equals(res.substring(0, 2));
    }

    @Override
    public int teacherUpdateTel(String uid, String tel, String verify) {
        String code = redisCache.getCacheObject("update_tel/" + uid);
        if (code == null || "".equals(code)) {
            return -1;
        }
        // 如果有缓存，更新
        if (!code.equals(verify)) {
            return -1;
        }
        UpdateWrapper<Teacher> teacherUpdateWrapper = new UpdateWrapper<>();
        teacherUpdateWrapper.eq("uid", uid).set("tel", tel);
        return teacherMapper.update(null, teacherUpdateWrapper);
    }

    @Override
    public boolean getTelVerify(String uid) {
        Teacher teacher = teacherMapper.selectTeacherByUid(uid);
        if (teacher == null) {
            return false;
        }
        String code = VerifyCodeUtils.generateVerifyCode(6);
        // 存redis
        redisCache.setCacheObject("update_tel/"+uid, code, 5, TimeUnit.MINUTES);
        // 发短信
        String res = smsService.sendUpdateTelVerifyCode(teacher.getTel(), code);
        return "OK".equals(res.substring(0, 2));
    }
}
