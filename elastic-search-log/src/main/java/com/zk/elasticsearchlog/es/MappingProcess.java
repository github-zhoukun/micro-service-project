package com.zk.elasticsearchlog.es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 索引 Mapping 处理类
 *
 * @author zhoukun
 */
@Slf4j
@Component
public class MappingProcess {
    private RestHighLevelClient client;

    @Autowired
    public MappingProcess(RestHighLevelClient client) {
        this.client = client;
    }


    /**
     * 添加Field映射
     *
     * @param indexName 索引
     * @param map
     */
    public boolean addMapping(String indexName, Map<String, Object> map) throws IOException {
        PutMappingRequest request = new PutMappingRequest(indexName);
        Map<String, Object> mapping = new HashMap<>(map.size());
        mapping.put("properties", map);
        request.source(mapping);
        AcknowledgedResponse response = client.indices().putMapping(request, RequestOptions.DEFAULT);
        // 指示是否所有节点都已确认请求
        boolean acknowledged = response.isAcknowledged();
        log.info("MappingProcess addMapping 是否所有节点都已确认请求Acknowledged:{}", acknowledged);
        return acknowledged;
    }

    /**
     * 查询Mapping
     * 返回所有索引的映射
     *
     * @param indexName 索引
     * @throws IOException
     */
    public Map<String, MappingMetaData> getAllMappings(String... indexName) throws IOException {
        GetMappingsRequest request = new GetMappingsRequest().indices(indexName);
        GetMappingsResponse response = client.indices().getMapping(request, RequestOptions.DEFAULT);
        return response.mappings();
    }

    /**
     * 查询Mapping
     * 检索特定索引的映射
     *
     * @param indexName 索引
     * @return
     * @throws IOException
     */
    public MappingMetaData getMappingMetaData(String indexName) throws IOException {
        Map<String, MappingMetaData> mappingMetaDataMap = getAllMappings(indexName);
        return mappingMetaDataMap.get(indexName);
    }

    /**
     * 查询Mapping
     * 将映射作为Java Map获取
     *
     * @param indexName 索引
     * @return
     * @throws IOException
     */
    public Map<String, Object> getJavaMapping(String indexName) throws IOException {
        MappingMetaData indexMapping = getMappingMetaData(indexName);
        Map<String, Object> mapping = indexMapping.getSourceAsMap();
        return mapping;
    }


    /**
     * 查询 Mapping 指定field字段
     *
     * @param indexName 索引
     * @param fields    字段
     */
    public Map<String, Object> getFieldMapping(String indexName, String... fields) throws IOException {
        GetFieldMappingsRequest request = new GetFieldMappingsRequest()
                .indices(indexName)
                .fields(fields)
                // 可选 控制别名是否需要在本地簇状态或者在由当选主节点所保持的群集状态要查找
                .local(true);

        GetFieldMappingsResponse response = client.indices().getFieldMapping(request, RequestOptions.DEFAULT);
        //所有请求的索引字段的映射
        final Map<String, Map<String, GetFieldMappingsResponse.FieldMappingMetaData>> mappings = response.mappings();
        //检索特定索引的映射
        final Map<String, GetFieldMappingsResponse.FieldMappingMetaData> fieldMappings =
                mappings.get(indexName);
//        //获取message字段的映射元数据
//        final GetFieldMappingsResponse.FieldMappingMetaData metaData = fieldMappings.get(fields);
//        //获取该字段的全名
//        final String fullName = metaData.fullName();
//        //获取该字段的映射源
//        final Map<String, Object> source = metaData.sourceAsMap();
        final Map<String, Object> source = processFieldMappings(fieldMappings);
        log.info("MappingProcess getFieldMapping param:[indexName:[] - fields:{}] - result:[fullName:{} - source:{}]", indexName, fields, source);
        return source;
    }

    /**
     * 简单处理返回指定FieldMap
     *
     * @param fieldMappings
     * @return
     */
    private Map<String, Object> processFieldMappings(Map<String, GetFieldMappingsResponse.FieldMappingMetaData> fieldMappings) {
        Map<String, Object> map = new HashMap();
        if (fieldMappings == null || fieldMappings.isEmpty()) {
            return map;
        }
        for (GetFieldMappingsResponse.FieldMappingMetaData metaData : fieldMappings.values()) {
            map.putAll(metaData.sourceAsMap());
        }
        return map;
    }
}
