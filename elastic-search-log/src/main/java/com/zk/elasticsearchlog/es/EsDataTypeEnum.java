package com.zk.elasticsearchlog.es;

/**
 * ES 数据类型枚举
 * TODO 待完善
 *
 * @author zhoukun
 */
public enum EsDataTypeEnum {
    /**
     * 字符串会被分析器分成一个一个词项 字段内容会被分析。text类型的字段不用于排序，很少用于聚合
     */
    ANALYSIS_STRING(new String[]{"String"}, "text"),
    /**
     * keyword类型适用于索引结构化的字段
     * 如果字段需要进行过滤(比如查找已发布博客中status属性为published的文章)、排序、聚合。keyword类型的字段只能通过精确值搜索到。
     */
    STRING(new String[]{"String"}, "keyword");




    private String[] javaType;
    private String esType;


    EsDataTypeEnum(String[] javaType, String esType) {
        this.javaType = javaType;
        this.esType = esType;
    }

    public String[] getJavaType() {
        return javaType;
    }

    public void setJavaType(String[] javaType) {
        this.javaType = javaType;
    }

    public String getEsType() {
        return esType;
    }

    public void setEsType(String esType) {
        this.esType = esType;
    }}
