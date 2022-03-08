package com.sicau.devicemanagement.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sicau.devicemanagement.common.constant.Constants;
import com.sicau.devicemanagement.common.core.redis.RedisCache;
import com.sicau.devicemanagement.common.utils.VerifyCodeUtils;
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
    public boolean insertStudent(Student student)
    {
        String s = smsService.sendStudentCreatedSms(student.getTel(), student.getName());
        return studentMapper.insertStudent(student) > 0 && "OK".equals(s.substring(0, 2));
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
     * 学生修改自己账号的密码
     *
     * @param uid      uid
     * @param password 密码
     * @return int
     * @author sora
     * @date 2022/02/14
     */
    @Override
    public int studentUpdatePassword(String uid, String password, String verify) {
        String code = redisCache.getCacheObject("update_password/" + uid);
        if (code == null || "".equals(code)) {
            return -1;
        }
        // 如果有缓存，更新
        if (!code.equals(verify)) {
            return -1;
        }
        UpdateWrapper<Student> studentUpdateWrapper = new UpdateWrapper<>();
        studentUpdateWrapper.eq("uid", uid).set("password", password);
        return studentMapper.update(null, studentUpdateWrapper);
    }

    /**
     * 生成更改密码的验证码
     *
     * @param uid uid
     * @author sora
     * @date 2022/02/15
     */
    @Override
    public String getPasswordVerify(String uid) {
        Student student = studentMapper.selectStudentByUid(uid);
        if (student == null) {
            return null;
        }
        String code = VerifyCodeUtils.generateVerifyCode(6);
        // 存redis
        redisCache.setCacheObject("update_password/"+uid, code, 5, TimeUnit.MINUTES);
        // 发短信
        String res = smsService.sendUpdatePasswordVerifyCode(student.getTel(), code);
        if ("OK".equals(res.substring(0, 2))) {
            return code;
        }
        return null;
    }

    @Override
    public int studentUpdateTel(String uid, String tel, String verify) {
        String code = redisCache.getCacheObject("update_tel/" + uid);
        if (code == null || "".equals(code)) {
            return -1;
        }
        // 如果有缓存，更新
        if (!code.equals(verify)) {
            return -1;
        }
        UpdateWrapper<Student> studentUpdateWrapper = new UpdateWrapper<>();
        studentUpdateWrapper.eq("uid", uid).set("tel", tel);
        return studentMapper.update(null, studentUpdateWrapper);
    }

    @Override
    public String getTelVerify(String uid) {
        Student student = studentMapper.selectStudentByUid(uid);
        if (student == null) {
            return null;
        }
        String code = VerifyCodeUtils.generateVerifyCode(6);
        // 存redis
        redisCache.setCacheObject("update_tel/"+uid, code, 5, TimeUnit.MINUTES);
        // 发短信
        String res = smsService.sendUpdateTelVerifyCode(student.getTel(), code);
        if ("OK".equals(res.substring(0, 2))) {
            return code;
        }
        return null;
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
     * 老师批量解封下属学生
     *
     * @param uids uid
     * @return int
     * @author sora
     * @date 2022/02/14
     */
    @Override
    public int cancelBanStudentByUids(String[] uids) {
        UpdateWrapper<Student> studentUpdateWrapper = new UpdateWrapper<>();
        int count = 0;
        for (String id : uids) {
            studentUpdateWrapper.eq("uid", id).set("is_del", Constants.NATURAL);
            if (studentMapper.update(null, studentUpdateWrapper) > 0) {
                count++;
            }
        }
        return count;
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
//        Date now = new Date();
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.select("expiration_date").eq("uid", sid);
        String far = studentMapper.selectList(studentQueryWrapper).get(0).getExpirationDate();
        System.out.println("haha" + far);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = simpleDateFormat.format(new Date(DateUtils.dateTime(far, DateUtils.YYYY_MM_DD).getTime() + (long) week * 7 * 24 * 60 * 60 * 1000));
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
