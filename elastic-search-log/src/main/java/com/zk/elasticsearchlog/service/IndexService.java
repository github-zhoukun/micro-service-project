package com.zk.elasticsearchlog.service;

/**
 * 接口
 *
 * @author zhoukun
 */
public interface IndexService {

    /**
     * 创建索引
     *
     * @param indexName             索引名
     * @param indexNumberOfShards   分片
     * @param indexNumberOfReplicas 副本
     * @return
     */
    Boolean createIndex(String indexName, Integer indexNumberOfShards, Integer indexNumberOfReplicas);


    /**
     * 索引是否存在
     *
     * @param indexName 索引名
     * @return
     */
    Boolean existsIndex(String indexName);

    /**
     * 删除索引
     *
     * @param indexName 索引名
     * @return
     */
    Boolean deleteIndex(String indexName);
}
