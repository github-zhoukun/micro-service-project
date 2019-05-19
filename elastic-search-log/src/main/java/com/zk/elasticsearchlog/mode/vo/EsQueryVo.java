package com.zk.elasticsearchlog.mode.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * ES 查询Vo
 *
 * @author zhoukun
 */
@Data
@ApiModel(value = "ES查询实体类", description = "ES 查询条件")
public class EsQueryVo {
    /**
     * 索引名
     */
    @ApiModelProperty(name = "索引名", example = "user_index", required = true)
    private String indexName;

    /**
     * 返回哪些字段
     */
    @ApiModelProperty(name = "查询字段列表", example = "id, name")
    private String[] includeFields;

    /**
     * 排除哪些字段
     */
    @ApiModelProperty(name = "排除字段列表", example = "id, name")
    private String[] excludeFields;

    /**
     * 查询条件
     * 查询内容 分词去匹配
     */
    @ApiModelProperty(name = "查询条件（分词去匹配）", example = "{\"name\": \"zhoukun\"}")
    private Map<String, Object> matchMap;

    /**
     * 查询条件
     * 查询内容直接匹配 不会区分词匹配
     */
    @ApiModelProperty(name = "查询条件直接匹配", example = "{\"name\": \"周坤\"}")
    private Map<String, Object> termMap;

    /**
     * 排序条件
     */
    @ApiModelProperty(name = "排序条件", example = "{\"date\": \"DESC\"}")
    private Map<String, String> sortMap;

    /**
     * 分页
     * 页码偏移量
     */
    @ApiModelProperty(name = "页码偏移量", example = "0", required = true)
    private Integer from;
    /**
     * 分页
     * 偏移量大小
     */
    @ApiModelProperty(name = "偏移量大小", example = "10", required = true)
    private Integer size;

    /**
     * 是否开启模糊查询 默认0
     */
    @ApiModelProperty(name = "是否开启模糊查询 默认0", example = "0")
    private int isFuzziness = 0;

    /**
     * 区间查询条件
     */
    @ApiModelProperty(name = "区间查询条件", example = "[{\"fieldName\":\"date\", \"gte\":\"2018-01-01\", \"lte\":\"2018-01-02\"}]")
    private List<RangeQuery> rangeQueryList;
}
