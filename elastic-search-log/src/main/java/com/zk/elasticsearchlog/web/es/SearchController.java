package com.zk.elasticsearchlog.web.es;

import com.alibaba.fastjson.JSONObject;
import com.zk.elasticsearchlog.annotation.AnoRateLimiter;
import com.zk.elasticsearchlog.mode.vo.EsQueryVo;
import com.zk.elasticsearchlog.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 查询 ES
 *
 * @author zhoukun
 */
@Slf4j
@RestController
@Api("ES 搜索")
@RequestMapping("es")
public class SearchController {


    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @ApiOperation(value = "ES搜索", httpMethod = "POST", response = List.class, notes = "/simple")
    @PostMapping(value = "search")
    @AnoRateLimiter(value = "SearchController_search", permitsPerSecond = 0.1D)
    public List<Map<String, Object>> search(@RequestBody EsQueryVo esQueryVo) {
        log.info("SearchController.search param esQueryVo:{}", JSONObject.toJSON(esQueryVo));
        return searchService.search(esQueryVo);
    }


}
