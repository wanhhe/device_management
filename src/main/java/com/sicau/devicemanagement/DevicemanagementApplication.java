package com.sicau.devicemanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sicau.devicemanagement.mapper")
public class DevicemanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevicemanagementApplication.class, args);
    }

}
