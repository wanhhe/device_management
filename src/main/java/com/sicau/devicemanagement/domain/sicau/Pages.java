package com.sicau.devicemanagement.domain.sicau;

import lombok.Data;

/**
 * 分页信息，如无该信息则表示接口不自动分页，也未设置自定义分页参数
 *
 * @author sora
 * @date 2022/03/11
 */
@Data
public class Pages {


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
