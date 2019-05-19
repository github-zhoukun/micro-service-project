package com.zk.elasticsearchlog.db.dao;

import com.zk.elasticsearchlog.db.model.RetailB2cOrderDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Dao
 *
 * @author zhoukun
 */
public interface RetailB2cOrderDao {

    /**
     * 查询所有
     *
     * @param offset 起始
     * @param limit  大小
     * @return
     */
    List<RetailB2cOrderDo> getAll(@Param("offset") int offset, @Param("limit") int limit);
}
