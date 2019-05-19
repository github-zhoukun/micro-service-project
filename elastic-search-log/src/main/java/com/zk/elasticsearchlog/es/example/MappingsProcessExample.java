package com.zk.elasticsearchlog.es.example;

import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.Map;

/**
 * 映射处理
 *
 * @author zhoukun
 */
public class MappingsProcessExample {

    /**
     * 添加
     *
     * @param client
     */
    public void addMapping(RestHighLevelClient client) throws IOException {
        PutMappingRequest putMappingRequest = new PutMappingRequest("preson");
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("level");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        putMappingRequest.source(builder);
        AcknowledgedResponse acknowledgedResponse = client.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);
    }

    /**
     * 查询
     *
     * @param client
     * @throws IOException
     */
    public void getMapping(RestHighLevelClient client) throws IOException {
        GetMappingsRequest getMappingsRequest = new GetMappingsRequest().indices("preson");
        GetMappingsResponse getMappingsResponse = client.indices().getMapping(getMappingsRequest, RequestOptions.DEFAULT);
    }

    public void getFileMapping(RestHighLevelClient client) throws IOException {
        GetFieldMappingsRequest getFieldMappingsRequest = new GetFieldMappingsRequest().indices("preson");
        getFieldMappingsRequest.fields("name", "age");
        GetFieldMappingsResponse getFieldMappingsResponse = client.indices().getFieldMapping(getFieldMappingsRequest, RequestOptions.DEFAULT);
        //返回所有请求的索引字段的映射
        Map<String, Map<String, GetFieldMappingsResponse.FieldMappingMetaData>> mappings = getFieldMappingsResponse.mappings();
        //检索特定索引的映射
        final Map<String, GetFieldMappingsResponse.FieldMappingMetaData> fieldMappings = mappings.get("twitter");
        //获取message字段的映射元数据
        final GetFieldMappingsResponse.FieldMappingMetaData metaData = fieldMappings.get("message");
        //获取该字段的全名
        final String fullName = metaData.fullName();
        //获取该字段的映射源
        final Map<String, Object> source = metaData.sourceAsMap();
    }

}
