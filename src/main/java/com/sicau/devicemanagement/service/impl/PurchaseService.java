package com.sicau.devicemanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sicau.devicemanagement.common.utils.file.DateUtils;
import com.sicau.devicemanagement.common.utils.uuid.IdUtils;
import com.sicau.devicemanagement.domain.Purchase;
import com.sicau.devicemanagement.mapper.PurchaseMapper;
import com.sicau.devicemanagement.mapper.TeacherMapper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 选择所有
     *
     * @return {@link List< Purchase > }
     * @author sora
     * @date 2022/02/26
     */
    public List<Purchase> selectAll() {
        QueryWrapper<Purchase> shopQueryWrapper = new QueryWrapper<>();
        shopQueryWrapper.orderByDesc("want");
        return purchaseMapper.selectList(shopQueryWrapper);
    }

    public List<Purchase> select(int size, int page) {
        int offset = size * (page - 1);
        QueryWrapper<Purchase> purchaseQueryWrapper = new QueryWrapper<>();
        purchaseQueryWrapper.orderByDesc("suppose_purchase_time");
        purchaseQueryWrapper.last("limit "+offset+ ", "+size);
        List<Purchase> purchases = purchaseMapper.selectList(purchaseQueryWrapper);
        for (Purchase tmp : purchases) {
            tmp.setTname(teacherMapper.selectNameByUid(tmp.getTid()));
        }
        return purchases;
    }

    public int reduce(String id) {
        UpdateWrapper<Purchase> purchaseUpdateWrapper = new UpdateWrapper<>();
        purchaseUpdateWrapper.setSql("count = count - 1").eq("id", id);
        return purchaseMapper.update(null, purchaseUpdateWrapper);
    }

    public int add(String id, int num) {
        UpdateWrapper<Purchase> purchaseUpdateWrapper = new UpdateWrapper<>();
        purchaseUpdateWrapper.setSql("count = count + "+num).eq("id", id);
        return purchaseMapper.update(null, purchaseUpdateWrapper);
    }

    public int create(Purchase purchase) {
        purchase.setId(IdUtils.simpleUUID());
        String time = DateUtils.dateTime();
        purchase.setCreateTime(time);
        return purchaseMapper.insert(purchase);
    }

    public int del(String id) {
        return purchaseMapper.deleteById(id);
    }
}
