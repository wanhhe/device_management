package com.sicau.devicemanagement.domain.sicau;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class SicauBody<T> {

    /**
     * 消息
     */
    private String message;

    /**
     * 获取或受影响数据量
     */
    private int num;

    /**
     * 分页信息，如无该信息则表示接口不自动分页，也未设置自定义分页参数
     */
    private List<Pages> pages;

    /**
     * 查询结果中的字段名
     */
    private String[] resultHead;

    /**
     * /查询结果数据
     */
    private List<T> resultValue;

    /**
     * sql类型
     */
    private String sqlType;

    /**
     * 执行状态
     */
    private int status;

    /**
     * /执行是否成功
     */
    private boolean success;

    /**
     * 时间戳参数，如无该参数表示接口未指定时间戳参数
     */
    private List<TimeStamps> timeStamps;

    /**
     * 查询数据总量，sql为insert、update、delete则始终为0
     */
    private int total;
}
