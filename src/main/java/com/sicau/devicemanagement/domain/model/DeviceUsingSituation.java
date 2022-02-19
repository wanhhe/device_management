package com.sicau.devicemanagement.domain.model;

import lombok.Data;

/**
 * 设备使用情况
 *
 * @author sora
 * @date 2022/01/18
 */
@Data
public class DeviceUsingSituation {

    // 设备总数
    private Integer total;

    // 正在使用中的设备数量
    private Integer using;

    // 超时未归还的设备总数量
    private Integer overtime;
}
