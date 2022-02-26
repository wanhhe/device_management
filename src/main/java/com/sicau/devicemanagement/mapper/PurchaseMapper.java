package com.sicau.devicemanagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sicau.devicemanagement.domain.Purchase;
import org.springframework.stereotype.Component;

@Component
public interface PurchaseMapper extends BaseMapper<Purchase> {

    int insert(Purchase purchase);
}
