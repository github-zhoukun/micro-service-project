package com.zk.elasticsearchlog.es;

import org.elasticsearch.client.TimedRequest;
import org.elasticsearch.common.unit.TimeValue;

public class RequestBuild {
    /**
     * 超时等待所有节点确认索引创建为 TimeValue
     * 设置为两分钟
     *
     * @param request
     */
    public static void setTimeout(TimedRequest request) {
        request.setTimeout(TimeValue.timeValueMinutes(2));
    }

    /**
     * 超时连接到主节点作为 TimeValue
     * 1分钟
     */
    public static void setMasterTimeout(TimedRequest request) {
        request.setMasterTimeout(TimeValue.timeValueMinutes(1));
    }

}
