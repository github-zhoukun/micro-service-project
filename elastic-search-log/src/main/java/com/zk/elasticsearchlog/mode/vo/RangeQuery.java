package com.zk.elasticsearchlog.mode.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 区间查询条件
 *
 * @author zhoukun
 */
@ApiModel(value = "区间查询条件", description = "区间查询条件实体类")
@Data
public class RangeQuery {
    /**
     * 区间字段名
     */
    @ApiModelProperty(name = "区间字段名", example = "date")
    private String fieldName;

    /**
     * 区间左值
     */
    @ApiModelProperty(name = "区间左值", example = "2018-01-01")
    private String gte;

    /**
     * 区间右值
     */
    @ApiModelProperty(name = "区间右值", example = "2018-02-01")
    private String lte;
}
