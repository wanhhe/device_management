package com.sicau.devicemanagement.domain;

import lombok.Data;

@Data
public class Purchase {

    private String id;

    private String name;

    private Integer want;

    private Integer count;

    private String createTime;

    private String updateTime;
}
