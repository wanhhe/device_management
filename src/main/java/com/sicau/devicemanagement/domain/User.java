package com.sicau.devicemanagement.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data

public class User implements Serializable {

    private String uid;
    private String name;
    private String password;
    private Integer isDel;

    @TableField(select = false)
    private String role;
}
