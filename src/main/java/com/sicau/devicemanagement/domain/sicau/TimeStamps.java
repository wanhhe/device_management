package com.sicau.devicemanagement.domain.sicau;

import lombok.Data;

/**
 * 时间戳参数，如无该参数表示接口未指定时间戳参数
 *
 * @author sora
 * @date 2022/03/11
 */
@Data
public class TimeStamps {

    /**
     * 参数描述
     */
    private String desc;

    /**
     * 参数名
     */
    private String name;

    /**
     * 参数值
     */
    private String value;
}
