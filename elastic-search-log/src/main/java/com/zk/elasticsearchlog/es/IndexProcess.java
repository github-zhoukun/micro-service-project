package com.zk.elasticsearchlog.es;

import com.zk.elasticsearchlog.config.EsConfigBean;
import com.zk.elasticsearchlog.exception.ParamNullException;
import com.zk.elasticsearchlog.mode.dto.IndexDto;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * 索引处理类
 *
 * @author zhoukun
 */
@Slf4j
@Component
public class IndexProcess {

    private RestHighLevelClient client;

    @Autowired
    public IndexProcess(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     * 创建索引
     *
     * @param indexInfo 索引信息
     * @return
     */
    public boolean createIndex(IndexDto indexInfo) throws ParamNullException, IOException {
        if (Objects.isNull(indexInfo)) {
            throw new ParamNullException("IndexProcess createIndex indexInfo is Null!");
        }
        if (StringUtils.isEmpty(indexInfo.getName())) {
            throw new ParamNullException("IndexProcess createIndex indexInfo Class filed name is Null!");
        }
        if (existsIndex(indexInfo.getName())) {
            log.info("IndexProcess createIndex indexName:{} already exists", indexInfo.getName());
            return true;
        }
        CreateIndexRequest request = new CreateIndexRequest(indexInfo.getName());
        request.settings(buildSetting(indexInfo));
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        log.info("IndexProcess createIndex result 是否所有节点确认请求acknowledged:{}, 是否每个分片启动了对应的副本shardsAcknowledged:{}", acknowledged, shardsAcknowledged);
        return acknowledged;
    }

    /**
     * 异步执行
     *
     * @param indexInfo 索引信息
     * @return
     * @throws Exception
     */
    public boolean asynCreateIndex(IndexDto indexInfo) throws Exception {
        //todo 待实现
        return false;
    }


    /**
     * 构建Settings
     *
     * @param indexInfo 索引信息
     * @return
     */
    private Settings buildSetting(IndexDto indexInfo) {
        return Settings.builder()
                .put(EsConfigBean.INDEX_NUMBER_OF_SHARDS, indexInfo.getIndexNumberOfShards())
                .put(EsConfigBean.INDEX_NUMBER_OF_REPLICAS, indexInfo.getIndexNumberOfReplicas())
                .build();
    }

    /**
     * 删除索引
     *
     * @param indexName 索引名
     * @return
     */
    public boolean deleteIndex(String indexName) throws IOException {
        if (!existsIndex(indexName)) {
            log.info("IndexProcess deleteIndex indexName:{} does not exist！", indexName);
            return true;
        }
        DeleteIndexRequest request = new DeleteIndexRequest();
        request.indices(indexName);
        AcknowledgedResponse acknowledgedResponse = client.indices().delete(request, RequestOptions.DEFAULT);
        boolean result = acknowledgedResponse.isAcknowledged();
        log.info("IndexProcess deleteIndex indexName:{} Result isAcknowledged:{}", indexName, result);
        return result;
    }

    /**
     * 异步删除
     *
     * @param indexName 索引名
     * @return
     * @throws IOException
     */
    public boolean asynDeleteIndex(String indexName) throws IOException {
        //todo
        return false;
    }

    /**
     * 索引是否存在
     *
     * @param indexName 索引名
     * @return
     * @throws IOException
     */
    public boolean existsIndex(String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        boolean isExists = client.indices().exists(request, RequestOptions.DEFAULT);
        return isExists;
    }
}
