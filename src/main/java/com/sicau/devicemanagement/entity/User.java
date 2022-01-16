package com.sicau.devicemanagement.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String uid;
    private String name;
    private String password;
    private Boolean isDelete;
    private String role;
}
