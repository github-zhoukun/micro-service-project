package com.zk.elasticsearchlog.web.es;

import com.zk.elasticsearchlog.service.MappingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Mapping 操作
 *
 * @author zhoukun
 */
@RestController
@RequestMapping("es/index/mapping")
@Api(value = "Es Index Mapping 操作")
@Slf4j
public class MappingController {

    private MappingsService mappingsService;

    @Autowired
    public MappingController(MappingsService mappingsService) {
        this.mappingsService = mappingsService;
    }

    @ApiOperation(value = "添加Mapping", httpMethod = "POST", response = Boolean.class, notes = "/simple")
    @PostMapping("{indexName}")
    public boolean addMapping(
            @ApiParam(name = "indexName", value = "索引名", required = true)
            @PathVariable(value = "indexName") String indexName,
            @ApiParam(name = "jsonMap", value = "filedMap", required = true)
            @RequestBody Map<String, Object> jsonMap) {
        log.info("MappingController addMapping param indexName:{} - jsonMap:{}", indexName, jsonMap);
        return mappingsService.addMapping(indexName, jsonMap);

    }

    @ApiOperation(value = "获取指定索引mapping", httpMethod = "GET", response = Boolean.class, notes = "/simple")
    @GetMapping("{indexName}/mapping")
    public Map<String, Object> getJavaMapping(@ApiParam(name = "indexName", value = "索引名", required = true)
                                              @PathVariable(value = "indexName") String indexName) {
        log.info("MappingController getJavaMapping param indexName:{}", indexName);
        return mappingsService.getJavaMapping(indexName);
    }

    @ApiOperation(value = "获取指定field mapping", httpMethod = "GET", response = Boolean.class, notes = "/simple")
    @GetMapping("{indexName}/mapping/field")
    public Map<String, Object> getFieldMapping(@ApiParam(name = "indexName", value = "索引名", required = true)
                                               @PathVariable(value = "indexName") String indexName,
                                               @ApiParam(name = "fields", value = "fields", required = true)
                                               @RequestParam(value = "fields") String... fields) {
        log.info("MappingController getFieldMapping param indexName:{}-fields:{}", indexName, fields);
        return mappingsService.getFieldMapping(indexName, fields);
    }

}
