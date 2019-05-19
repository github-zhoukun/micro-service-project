package com.zk.elasticsearchlog.service.impl;

import com.zk.elasticsearchlog.es.SearchProcess;
import com.zk.elasticsearchlog.mode.vo.EsQueryVo;
import com.zk.elasticsearchlog.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 搜索实现
 *
 * @author zhoukun
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    private SearchProcess searchProcess;

    @Autowired
    public SearchServiceImpl(SearchProcess searchProcess) {
        this.searchProcess = searchProcess;
    }

    @Override
    public List<Map<String, Object>> search(EsQueryVo esQueryVo) {
        try {
            if (esQueryVo != null) {
                return searchProcess.search(esQueryVo);
            }
        } catch (IOException e) {
            log.error("SearchServiceImpl.search IOException");
            e.printStackTrace();
        } catch (Exception e) {
            log.error("SearchServiceImpl.search Exception");
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
