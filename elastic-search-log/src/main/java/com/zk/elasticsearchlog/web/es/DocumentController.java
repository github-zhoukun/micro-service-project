package com.zk.elasticsearchlog.web.es;

import com.zk.elasticsearchlog.service.DocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 文档存储
 *
 * @author zhoukun
 */
@RestController
@RequestMapping("es/index/document")
@Api(value = "Es Index Document  操作")
@Slf4j
public class DocumentController {

    private DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @ApiOperation(value = "创建文档", httpMethod = "PUT", response = Boolean.class, notes = "/simple")
    @PutMapping("{indexName}/{id}")
    public boolean createDoc(@ApiParam(name = "indexName", value = "索引名", required = true)
                             @PathVariable(value = "indexName") String indexName,
                             @ApiParam(name = "docMap", value = "文档Map", required = true)
                             @RequestBody Map<String, Object> docMap,
                             @ApiParam(name = "id", value = "主键")
                             @PathVariable(value = "id", required = false) String id) {
        log.info("DocumentController.createDoc params[indexName:{} - docMap:{} - id:{}]", indexName, docMap, id);
        return documentService.createDoc(indexName, docMap, id);
    }

    @ApiOperation(value = "查询文档", httpMethod = "GET", response = Map.class, notes = "/simple")
    @GetMapping("{indexName}/{id}")
    public Map<String, Object> getDoc(@ApiParam(name = "indexName", value = "索引名", required = true)
                                      @PathVariable(value = "indexName") String indexName,
                                      @ApiParam(name = "id", value = "主键")
                                      @PathVariable(value = "id", required = false) String id) {
        log.info("DocumentController.getDoc params[indexName:{} - id:{}]", indexName, id);
        return documentService.getDoc(indexName, id);
    }

    @ApiOperation(value = "查询文档是否存", httpMethod = "GET", response = Map.class, notes = "/simple")
    @GetMapping("{indexName}/{id}/exists")
    public boolean existsDoc(@ApiParam(name = "indexName", value = "索引名", required = true)
                             @PathVariable(value = "indexName") String indexName,
                             @ApiParam(name = "id", value = "主键")
                             @PathVariable(value = "id", required = false) String id) {
        log.info("DocumentController.existsDoc params[indexName:{} - id:{}]", indexName, id);
        return documentService.existsDoc(indexName, id);
    }

    @ApiOperation(value = "删除文档", httpMethod = "DELETE", response = Map.class, notes = "/simple")
    @DeleteMapping("{indexName}/{id}")
    public boolean deleteDoc(@ApiParam(name = "indexName", value = "索引名", required = true)
                             @PathVariable(value = "indexName") String indexName,
                             @ApiParam(name = "id", value = "主键")
                             @PathVariable(value = "id", required = false) String id) {
        log.info("DocumentController.deleteDoc params[indexName:{} - id:{}]", indexName, id);
        return documentService.deleteDoc(indexName, id);
    }

    @ApiOperation(value = "更新文档", httpMethod = "POST", response = Map.class, notes = "/simple")
    @PostMapping("{indexName}/{id}")
    public boolean updateDoc(@ApiParam(name = "indexName", value = "索引名", required = true)
                             @PathVariable(value = "indexName") String indexName,
                             @ApiParam(name = "id", value = "主键")
                             @PathVariable(value = "id", required = false) String id,
                             @ApiParam(name = "文档Map", value = "docMap", required = true)
                             @RequestBody Map<String, Object> docMap) {
        log.info("DocumentController.updateDoc params[indexName:{} - id:{} - docMap:{}]", indexName, id, docMap);
        return documentService.updateDoc(indexName, id, docMap);
    }
}
