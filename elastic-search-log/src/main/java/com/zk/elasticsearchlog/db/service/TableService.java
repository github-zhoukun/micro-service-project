package com.zk.elasticsearchlog.db.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 表相关操作
 *
 * @author zhoukun
 */
public interface TableService {

    /**
     * 查询目标库下面所有表
     *
     * @param dataBase
     * @return
     */
    List<String> findAllTable(@Param("dataBase") String dataBase);

    /**
     * 现实建表语句
     *
     * @param tableName 表名
     * @return
     */
    List<Map> showDDL(@Param("tableName") String tableName);

}
