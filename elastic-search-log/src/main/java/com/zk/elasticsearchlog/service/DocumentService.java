package com.zk.elasticsearchlog.service;

import java.io.IOException;
import java.util.Map;

/**
 * 接口
 *
 * @author zhoukun
 */
public interface DocumentService {

    /**
     * 创建doc
     *
     * @param indexName 索引名
     * @param docMap    docMap
     * @param id        唯一主键
     * @return
     */
    boolean createDoc(String indexName, Map<String, Object> docMap, String id);

    /**
     * 查询文档
     *
     * @param indexName 索引名
     * @param id        主键
     */
    Map<String, Object> getDoc(String indexName, String id);


    /**
     * 文档是否存在
     *
     * @param indexName 文档名
     * @param id        主键id
     * @return
     * @throws IOException
     */
    boolean existsDoc(String indexName, String id);


    /**
     * 删除文档
     *
     * @param indexName 索引名
     * @param id        主键
     * @return
     * @throws IOException
     */
    boolean deleteDoc(String indexName, String id);

    /**
     * 更新文档
     *
     * @param indexName 索引名
     * @param id        主键
     * @return
     */
    boolean updateDoc(String indexName, String id, Map<String, Object> docMap);
}
