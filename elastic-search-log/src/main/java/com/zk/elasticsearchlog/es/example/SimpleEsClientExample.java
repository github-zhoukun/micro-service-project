package com.zk.elasticsearchlog.es.example;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.util.Objects;

/**
 * 对Index Document 操作封装简化类
 *
 * @author zhoukun
 */
@Slf4j
public class SimpleEsClientExample {
    private TransportClient client;

    public SimpleEsClientExample(TransportClient client) {
        this.client = client;
    }

    /**
     * 默认type
     */
    public static final String DEFAULT_TYPE = "_doc";

    /**
     * @param indexName 索引名
     * @param type      ES7.0已经不建议用这个了 8.0将删除 推荐IndexName替代
     * @param id        唯一id
     */
    public boolean createIndex(String indexName, String type, String id) {
        log.warn("SimpleEsClientExample createIndex param indexName:{} type:{} id:{}", indexName, type, id);
        IndexResponse indexResponse;
        if (Objects.isNull(id)) {
            indexResponse = client.prepareIndex(indexName, type).get();
            log.info("SimpleEsClientExample createIndex param indexName:{} type:{} id:{} result:{}", indexName, type, id, indexResponse.status().getStatus());
            return indexResponse.status().getStatus() == 0;
        }
        indexResponse = client.prepareIndex(indexName, type, id).get();
        log.info("SimpleEsClientExample createIndex param indexName:{} type:{} id:{} result:{}", indexName, type, id, indexResponse.status().getStatus());
        return indexResponse.status().getStatus() == 0;
    }


    /**
     * @param indexName 索引名
     * @param type      类型
     */
    public boolean createIndex(String indexName, String type) {
        return createIndex(indexName, DEFAULT_TYPE, null);
    }

    /**
     * @param indexName 索引名
     */
    public boolean createIndex(String indexName) {
        return createIndex(indexName, DEFAULT_TYPE);
    }
}
