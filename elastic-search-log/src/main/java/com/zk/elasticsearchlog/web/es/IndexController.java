package com.zk.elasticsearchlog.web.es;

import com.zk.elasticsearchlog.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Index 控制器
 *
 * @author zhoukun
 */
@RestController
@Slf4j
@RequestMapping("es/index")
@Api(value = "ES Index 操作")
public class IndexController {
    @Autowired
    private IndexService indexService;

    @ApiOperation(value = "创建索引", httpMethod = "PUT", response = Boolean.class, notes = "/simple")
    @PutMapping("{indexName}")
    public Boolean createIndex(@ApiParam(name = "indexName", value = "索引名", required = true)
                               @PathVariable(value = "indexName") String indexName,
                               @ApiParam(name = "shard", value = "分片数", required = true)
                               @RequestParam(value = "shard", required = true) Integer shard,
                               @ApiParam(name = "replica", value = "副本数", required = true)
                               @RequestParam(value = "replica", required = true) Integer replica) {
        log.info("IndexController createIndex indexName:{}-shard:{}-replica:{}", indexName, shard, replica);
        return indexService.createIndex(indexName, shard, replica);
    }

    @ApiOperation(value = "查询索引是否存在", httpMethod = "GET", response = String.class, notes = "/simple")
    @GetMapping("{indexName}")
    public String existsIndex(@ApiParam(name = "indexName", value = "索引名", required = true)
                              @PathVariable(value = "indexName") String indexName) {

        log.info("IndexController existsIndex indexName:{}", indexName);
        boolean result = indexService.existsIndex(indexName);
        return result ? "存在" : "不存在";
    }

    @ApiOperation(value = "删除索引", httpMethod = "DELETE", response = Boolean.class, notes = "/simple")
    @DeleteMapping("{indexName}")
    public boolean deleteIndex(@ApiParam(name = "indexName", value = "索引名", required = true)
                               @PathVariable(value = "indexName") String indexName) {
        log.info("IndexController deleteIndex indexName:{}", indexName);
        Boolean result = indexService.deleteIndex(indexName);
        return result;

    }


}
