package com.zk.elasticsearchlog.service;

import java.io.IOException;
import java.util.Map;

/**
 * 接口
 *
 * @author zhoukun
 */
public interface MappingsService {

    /**
     * 添加Field映射
     *
     * @param indexName 索引
     * @param map
     */
    boolean addMapping(String indexName, Map<String, Object> map);


    /**
     * 查询Mapping
     * 将映射作为Java Map获取
     *
     * @param indexName 索引
     * @return
     * @throws IOException
     */
    Map<String, Object> getJavaMapping(String indexName);

    /**
     * 查询 Mapping 指定field字段
     *
     * @param indexName 索引
     * @param fields    字段
     * @return
     */
    Map<String, Object> getFieldMapping(String indexName, String... fields);
}
