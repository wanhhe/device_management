package com.sicau.devicemanagement;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sicau.devicemanagement.common.utils.http.HttpUtils;
import com.sicau.devicemanagement.common.utils.uuid.IdUtils;
import com.sicau.devicemanagement.domain.Student;
import com.sicau.devicemanagement.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class DevicemanagementApplicationTests {
    @Autowired
    StudentMapper studentMapper;

    @Test
    void contextLoads() {

    }

    @Test
    void register() {
        Student student = new Student();
        student.setUid(IdUtils.simpleUUID());
        student.setName("admin");
        student.setPassword(new BCryptPasswordEncoder().encode("123456"));
        studentMapper.insert(student);
    }

    @Test
    void update() {
        UpdateWrapper<Student> studentUpdateWrapper = new UpdateWrapper<>();
        studentUpdateWrapper.set("is_del", 0).eq("name", "admin");
        studentMapper.update(null, studentUpdateWrapper);
        System.out.println("finish");
    }
}
