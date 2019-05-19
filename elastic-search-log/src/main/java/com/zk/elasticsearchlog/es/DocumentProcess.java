package com.zk.elasticsearchlog.es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * Es 索引文档 处理
 * <p>
 * 参考文档： {@doc:https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-document-get.html}
 *
 * @author zhoukun
 */
@Slf4j
@Component
public class DocumentProcess {
    private RestHighLevelClient client;

    @Autowired
    public DocumentProcess(RestHighLevelClient client) {
        this.client = client;
    }


    /**
     * 创建doc
     *
     * @param indexName 索引名
     * @param docMap    docMap
     * @return
     */
    public boolean createDoc(String indexName, Map<String, Object> docMap) throws IOException {
        IndexRequest request = new IndexRequest(indexName);
        request.source(docMap, XContentType.JSON);
        return processCreate(indexName, request);
    }

    /**
     * 创建doc
     *
     * @param indexName 索引名
     * @param docMap    docMap
     * @param id        唯一主键
     * @return
     */
    public boolean createDoc(String indexName, Map<String, Object> docMap, String id) throws IOException {
        IndexRequest request = new IndexRequest(indexName)
                .id(id)
                .source(docMap, XContentType.JSON);
//                .opType(DocWriteRequest.OpType.CREATE); 可选 操作类型

        return processCreate(indexName, request);
    }

    /**
     * 通用处理创建doc
     *
     * @param request 请求
     * @return
     */
    public boolean processCreate(String indexName, IndexRequest request) throws IOException {
        try {
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            printProcessResultMsg(response);
            log.info("DocumentProcess createDoc result Status: ", response.status().getStatus());
            return true;
        } catch (ElasticsearchException e) {
            //很多种状态...
            log.error("DocumentProcess createDoc indexName:{} ElasticsearchException status:{} refer RestStatus", indexName, e.status());
            if (e.status() == RestStatus.CONFLICT) {
                return false;
            }
            return false;
        }
    }

    /**
     * 打印处理信息
     *
     * @param response doc处理结果
     */
    public void printProcessResultMsg(IndexResponse response) {
        final String index = response.getIndex();
        final String id = response.getId();
        final long version = response.getVersion();

        if (response.getResult() == DocWriteResponse.Result.CREATED) {
            //第一次创建文档
            log.info("文档创建成功！ index:{} - id:{} - version:{}", index, id, version);
        } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
            //已经存在 文档被重写
            log.info("文档已经存被重写！ index:{} - id:{} - version:{}", index, id, version);
        } else if (response.getResult() == DocWriteResponse.Result.NOT_FOUND) {
            //文档没有找到
            log.info("文档没有找到！ index:{} - id:{} - version:{}", index, id, version);
        } else if (response.getResult() == DocWriteResponse.Result.DELETED) {
            //文档已经删除
            log.info("文档已经删除! index:{} - id:{} - version:{}", index, id, version);
        }
        ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            //处理成功的分片数小于总分片数
            log.info("处理成功的分片数小于总分片数! shard:[total:{}-successful:{}] index:{} - id:{} - version:{}", shardInfo.getTotal(), shardInfo.getSuccessful(), index, id, version);
        }
        if (shardInfo.getFailed() > 0) {
            //潜在的失败
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                log.info("处理成功的分片数小于总分片数! shard:[Failed:{}-reason:{}] index:{} - id:{} - version:{}", shardInfo.getFailed(), reason, index, id, version);
            }
        }

    }

    /**
     * 查询文档
     *
     * @param indexName 索引名
     * @param id        主键
     */
    public Map<String, Object> getDoc(String indexName, String id) throws IOException {
        GetRequest request = new GetRequest(indexName, id);
//        request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE); 可选
        //检索前刷新 默认false
        request.realtime(true);

        return processGet(request);
    }

    /**
     * 查询文档
     *
     * @param indexName 索引名
     */
    public Map<String, Object> getDoc(String indexName) throws IOException {
        GetRequest request = new GetRequest(indexName);
        return processGet(request);
    }

    /**
     * 查询公共逻辑
     *
     * @param request
     * @return
     * @throws IOException
     */
    private Map<String, Object> processGet(GetRequest request) throws IOException {
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        final String indexName = response.getIndex();
        if (response.isExists()) {
            long version = response.getVersion();
            String strSource = response.getSourceAsString();
            log.info("查询文档结果为 [{}] indexName:{} version:{}", strSource, indexName, version);
            return response.getSourceAsMap();
        }
        log.info("查询文档结果为 Null! indexName:{}", indexName);
        return Collections.emptyMap();
    }

    /**
     * 文档是否存在
     *
     * @param indexName 文档名
     * @param id        主键id
     * @return
     * @throws IOException
     */
    public boolean existsDoc(String indexName, String id) throws IOException {
        GetRequest request = new GetRequest(indexName).id(id);
        //减轻请求 禁止提取_source的资源
        request.fetchSourceContext(new FetchSourceContext(false));
        //减轻请求 禁止提取存储的任何字段
        request.storedFields("_none_");
        boolean isExists = client.exists(request, RequestOptions.DEFAULT);
        log.info("DocumentProcess existsDoc result isExists:{}", isExists);
        return isExists;

    }

    /**
     * 删除文档
     *
     * @param indexName 索引名
     * @param id        主键
     * @return
     * @throws IOException
     */
    public boolean deleteDoc(String indexName, String id) throws IOException {
        DeleteRequest request = new DeleteRequest(indexName).id(id);
        try {
            return processDelete(request);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                log.info("DocumentProcess deleteDoc 版本冲突错误!indexName:{} - id:{} ", indexName, id);
            }
            return false;
        }

    }

    /**
     * 删除文档
     *
     * @param indexName 索引名
     * @return
     * @throws IOException
     */
    public boolean deleteDoc(String indexName) throws IOException {
        DeleteRequest request = new DeleteRequest(indexName);
        try {
            return processDelete(request);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                log.info("DocumentProcess deleteDoc 版本冲突错误!indexName:{}", indexName);
            }
            return false;
        }
    }

    /**
     * 删除通用处理
     *
     * @param request
     * @return
     * @throws IOException
     */
    private boolean processDelete(DeleteRequest request) throws IOException {
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        String index = response.getIndex();
        String id = response.getId();
        long version = response.getVersion();
        ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            //处理成功的分片数小于总分片数
            log.info("processDelete 处理成功的分片数小于总分片数! shard:[total:{}-successful:{}] index:{} - id:{} - version:{}", shardInfo.getTotal(), shardInfo.getSuccessful(), index, id, version);
        }
        if (shardInfo.getFailed() > 0) {
            //潜在的失败
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                log.info("processDelete 处理成功的分片数小于总分片数! shard:[Failed:{}-reason:{}] index:{} - id:{} - version:{}", shardInfo.getFailed(), reason, index, id, version);
            }
        }
        log.info("DocumentProcess processDelete result Status: ", response.status().getStatus());
        return true;
    }

    /**
     * 更新文档
     *
     * @param indexName 索引名
     * @param id        主键
     * @return
     */
    public boolean updateDoc(String indexName, String id, Map<String, Object> docMap) throws IOException {
        UpdateRequest request = new UpdateRequest(indexName, id);
        request.doc(docMap);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        printUpdateResponse(response);
        log.info("DocumentProcess updateRequest! index:{}- id:{}, result status:{}", indexName, id, response.status().getStatus());
        return true;
    }

    /**
     * 打印更新结果
     *
     * @param response
     * @return
     */
    private void printUpdateResponse(UpdateResponse response) {
        String index = response.getIndex();
        String id = response.getId();
        long version = response.getVersion();
        if (response.getResult() == DocWriteResponse.Result.CREATED) {
            //文档不存在
            log.info("文档不存在 创建成功！ index:{} - id:{} - version:{}", index, id, version);
        } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
            log.info("文档更新成功！ index:{} - id:{} - version:{}", index, id, version);
        } else if (response.getResult() == DocWriteResponse.Result.DELETED) {
            log.info("文档删除！ index:{} - id:{} - version:{}", index, id, version);
        } else if (response.getResult() == DocWriteResponse.Result.NOOP) {
            log.info("未对文档执行任何操作！ index:{} - id:{} - version:{}", index, id, version);
        }
    }

}
