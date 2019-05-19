package com.zk.elasticsearchlog.mode.dto;

import lombok.Data;

/**
 * 数据库 字段映射
 *
 * @author zhoukun
 */
@Data
public class FieldMappingDto {
    /**
     * 库
     */
    private String TABLE_SCHEMA;
    /**
     * 表
     */
    private String TABLE_NAME;

    /**
     * Field
     */
    private String COLUMN_NAME;
    /**
     * 字段说明
     */
    private String COLUMN_COMMENT;

    /**
     * 字段实际定义类型 如：varchar(32)
     */
    private String COLUMN_TYPE;

    /**
     * 字段类型 如：varchar
     */
    private String DATA_TYPE;
    /**
     * 字符编码 如：utf8mb4
     */
    private String CHARACTER_SET_NAME;

    /**
     * ES的数据类型
     */
    private String ES_DATA_TYPE;
}
