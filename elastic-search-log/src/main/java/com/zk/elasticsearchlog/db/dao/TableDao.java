package com.zk.elasticsearchlog.db.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 表相关操作
 *
 * @author zhoukun
 */
public interface TableDao {

    /**
     * 查询目标库下面所有表
     *
     * @param dataBase
     * @return
     */
    List<String> findAllTable(@Param("dataBase") String dataBase);

    /**
     * 现实创建表语句
     *
     * @param tableName 表名
     * @return
     */
    @Select("select * from information_schema.COLUMNS where TABLE_SCHEMA = (select database()) and TABLE_NAME=#{tableName}")
    List<Map> showDDL(@Param("tableName") String tableName);
}
