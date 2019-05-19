package com.zk.elasticsearchlog.db.service.impl;

import com.zk.elasticsearchlog.db.dao.RetailB2cOrderDao;
import com.zk.elasticsearchlog.db.model.RetailB2cOrderDo;
import com.zk.elasticsearchlog.db.service.RetailB2cOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单查询类
 *
 * @author zhoukun
 */
@Service
public class RetailB2cOrderServiceImpl implements RetailB2cOrderService {
    @Autowired
    private RetailB2cOrderDao retailB2cOrderDao;

    @Override
    public List<RetailB2cOrderDo> getAll(int offset, int limit) {
        return retailB2cOrderDao.getAll(offset, limit);
    }
}
