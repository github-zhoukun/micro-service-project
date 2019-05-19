package com.zk.elasticsearchlog.db.service;

import com.zk.elasticsearchlog.db.model.RetailB2cOrderDo;

import java.util.List;

/**
 * 订单
 *
 * @author zhoukun
 */
public interface RetailB2cOrderService {

    /**
     * 查询所有
     *
     * @param offset 起始
     * @param limit  大小
     * @return
     */
    List<RetailB2cOrderDo> getAll(int offset, int limit);
}
