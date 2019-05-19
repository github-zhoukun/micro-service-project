package com.zk.elasticsearchlog.service;

import com.zk.elasticsearchlog.mode.vo.EsQueryVo;

import java.util.List;
import java.util.Map;

/**
 * 查询接口
 *
 * @author zhoukun
 */
public interface SearchService {
    /**
     * 搜索
     *
     * @param esQueryVo
     * @return
     */
    List<Map<String, Object>> search(EsQueryVo esQueryVo);
}
