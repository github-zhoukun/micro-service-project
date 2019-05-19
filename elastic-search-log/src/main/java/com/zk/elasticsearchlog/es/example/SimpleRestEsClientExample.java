package com.zk.elasticsearchlog.es.example;

import com.zk.elasticsearchlog.config.EsConfigBean;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * RestHL版本
 *
 * @author zhoukun
 */
@Slf4j
@Service
public class SimpleRestEsClientExample {

    public void createIndex(RestHighLevelClient client) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("preson");
        buildSetting(request);
        buildMapping(request);
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    public void buildSetting(CreateIndexRequest request) {
        request.settings(Settings.builder()
                .put(EsConfigBean.INDEX_NUMBER_OF_SHARDS, 3)
                .put(EsConfigBean.INDEX_NUMBER_OF_REPLICAS, 1)
                .build());
    }

    public void buildMapping(CreateIndexRequest request) {
        Map<String, Object> name = new HashMap<>(1);
        name.put("type", "text");
        Map<String, Object> password = new HashMap<>(1);
        password.put("type", "text");
        Map<String, Object> age = new HashMap<>(1);
        age.put("type", "text");
        Map<String, Object> createDate = new HashMap<>(1);
        createDate.put("type", "date");

        Map<String, Object> properties = new HashMap<>(1);
        properties.put("name", name);
        properties.put("password", password);
        properties.put("age", age);
        properties.put("createDate", createDate);

        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);
        request.mapping(mapping);
    }

    public Boolean deleteIndex(RestHighLevelClient client) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("preson");
        boolean is = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if (is) {
            log.info("索引已经存在！");
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("preson");
            AcknowledgedResponse acknowledgedResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            log.info("deleteIndex 是否所有节点已经确认请求:{}", acknowledgedResponse.isAcknowledged());
            return acknowledgedResponse.isAcknowledged();
        }
        return true;
    }

    /**
     * 不知道干嘛的API
     * @param client
     */
    public void openIndex(RestHighLevelClient client) {
        OpenIndexRequest openIndexRequest = new OpenIndexRequest("preson");
        client.indices().openAsync(openIndexRequest, RequestOptions.DEFAULT, new ActionListener<OpenIndexResponse>() {
            @Override
            public void onResponse(OpenIndexResponse openIndexResponse) {
                //异步调用成功
//                openIndexResponse.
            }

            @Override
            public void onFailure(Exception e) {
                //异步调用成功

            }
        });
    }
}
