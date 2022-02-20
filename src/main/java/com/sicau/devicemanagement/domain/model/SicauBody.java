package com.sicau.devicemanagement.domain.model;

import lombok.Data;

@Data
public class SicauBody {

    private BasicResponse basicResponse;
    /**
     * 获取或受影响数据量
     */
    private int num;

    private String[] resultHead;

    private String resultValue;

    /**
     * 查询数据总量，sql为insert、update、delete则始终为0
     */
    private int total;
}
