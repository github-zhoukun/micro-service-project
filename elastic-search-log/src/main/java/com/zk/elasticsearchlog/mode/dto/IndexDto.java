package com.zk.elasticsearchlog.mode.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 索引实体类
 *
 * @author zhoukun
 */
@Data
@Builder
public class IndexDto {
    /**
     * 索引名
     */
    private String name;
    /**
     * 分片数
     */
    private Integer indexNumberOfShards;
    /**
     * 副本数
     */
    private Integer indexNumberOfReplicas;
}
