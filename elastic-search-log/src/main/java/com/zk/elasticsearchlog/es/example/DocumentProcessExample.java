package com.zk.elasticsearchlog.es.example;

import com.alibaba.fastjson.JSONObject;
import com.zk.elasticsearchlog.mode.vo.PresonVo;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * 文档处理
 *
 * @author zhoukun
 */
public class DocumentProcessExample {

    /**
     * 添加
     *
     * @param client
     */
    public void addDocument(RestHighLevelClient client) throws IOException {
        IndexRequest request = new IndexRequest("preson");
        request.id("1");

        PresonVo presonInfo = new PresonVo();
        presonInfo.setName("周坤");
        presonInfo.setAge(24);
        presonInfo.setDesc("一个好人");
        String json = JSONObject.toJSONString(presonInfo);
        request.source(json, XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 获取
     *
     * @param client
     * @throws IOException
     */
    public void getDocument(RestHighLevelClient client) throws IOException {
        GetRequest getRequest = new GetRequest("preson", "1");
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        //判断是否存在
        boolean isExists = client.exists(getRequest, RequestOptions.DEFAULT);
    }

    /**
     * 删除
     *
     * @param client
     */
    public void deleteDocument(RestHighLevelClient client) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("preson", "1");
        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
    }

    /**
     * 删除
     *
     * @param client
     */
    public void updateDocument(RestHighLevelClient client) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("preson", "1");
        PresonVo presonInfo = new PresonVo();
        presonInfo.setName("周坤-1");
        presonInfo.setAge(18);
        String json = JSONObject.toJSONString(presonInfo);
        updateRequest.doc(json, XContentType.JSON);

        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
    }
}
