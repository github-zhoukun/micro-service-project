package com.zk.elasticsearchlog.config;

import lombok.Data;

/**
 * ES 配置定义 实体类
 *
 * @author zhoukun
 */
@Data
public class EsConfigBean {
    /**
     * 分片数
     */
    public static final String INDEX_NUMBER_OF_SHARDS = "index.number_of_shards";
    /**
     * 副本数
     */
    public static final String INDEX_NUMBER_OF_REPLICAS = "index.number_of_replicas";

    /**
     * 集群信息
     */
    private String clusterNameKey;
    private String clusterNameValue;
    /**
     * 自动嗅探
     */
    private String clientTransportSniffKey;
    private Boolean clientTransportSniffValue;
    /**
     * ip/port
     */
    private String ip;
    private Integer port;
    private String protocol;


}
