package com.zk.elasticsearchlog.web.db;

import com.alibaba.fastjson.JSONObject;
import com.zk.elasticsearchlog.db.model.RetailB2cOrderDo;
import com.zk.elasticsearchlog.db.service.RetailB2cOrderService;
import com.zk.elasticsearchlog.db.service.TableService;
import com.zk.elasticsearchlog.mode.dto.FieldMappingDto;
import com.zk.elasticsearchlog.service.DocumentService;
import com.zk.elasticsearchlog.service.IndexService;
import com.zk.elasticsearchlog.service.MappingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表 操作
 *
 * @author zhoukun
 */
@Slf4j
@RestController
@RequestMapping("db")
@Api(value = "数据库表 操作类")
public class TableController {

    private TableService tableService;
    private IndexService indexService;
    private MappingsService mappingsService;
    private DocumentService documentService;
    private RetailB2cOrderService retailB2cOrderService;


    @Autowired
    public TableController(TableService tableService, IndexService indexService, MappingsService mappingsService, DocumentService documentService, RetailB2cOrderService retailB2cOrderService) {
        this.tableService = tableService;
        this.indexService = indexService;
        this.mappingsService = mappingsService;
        this.documentService = documentService;
        this.retailB2cOrderService = retailB2cOrderService;
    }

    @ApiOperation(value = "查询目标库下所有表", httpMethod = "GET", response = List.class, notes = "/simple")
    @GetMapping("{db}")
    public List<String> findAll(@ApiParam(name = "db", value = "数据库名", required = true)
                                @PathVariable("db") String db) {
        log.info("TableController.findAll db:{}", db);
        return tableService.findAllTable(db);
    }


    @ApiOperation(value = "查询目标库下所有表", httpMethod = "GET", response = List.class, notes = "/simple")
    @GetMapping("db/{tableName}")
    public List<Map> showDDL(@ApiParam(name = "tableName", value = "表名", required = true)
                             @PathVariable("tableName") String tableName) {
        log.info("TableController.showDDL tableName:{}", tableName);
        return tableService.showDDL(tableName);
    }

    @ApiOperation(value = "根据目标库创建Es Index和Mappings", httpMethod = "PUT", response = Boolean.class, notes = "/simple")
    @PutMapping("db/{tableName}")
    public List<FieldMappingDto> createEsIndexAndMappings(@ApiParam(name = "tableName", value = "表名", required = true)
                                                          @PathVariable("tableName") String tableName) {
        log.info("TableController.createEsIndexAndMappings tableName:{}", tableName);
        List<Map> tableStructures = tableService.showDDL(tableName);
        List<FieldMappingDto> fieldMappingDtoList = new ArrayList<>();
        Map<String, Object> fieldsMap = new HashMap<>();
        if (tableStructures != null && !tableStructures.isEmpty()) {
            for (Map map : tableStructures) {
                FieldMappingDto fieldMappingDto = JSONObject.parseObject(JSONObject.toJSONString(map), FieldMappingDto.class);
                if ("int".equals(fieldMappingDto.getDATA_TYPE())) {
                    fieldMappingDto.setES_DATA_TYPE("integer");
                } else if ("varchar".equals(fieldMappingDto.getDATA_TYPE())) {
                    fieldMappingDto.setES_DATA_TYPE("text");
                } else if ("bigint".equals(fieldMappingDto.getDATA_TYPE())) {
                    fieldMappingDto.setES_DATA_TYPE("long");
                } else if ("datetime".equals(fieldMappingDto.getDATA_TYPE())) {
                    fieldMappingDto.setES_DATA_TYPE("date");
                } else if ("timestamp".equals(fieldMappingDto.getDATA_TYPE())) {
                    fieldMappingDto.setES_DATA_TYPE("date");
                } else if ("tinyint".equals(fieldMappingDto.getDATA_TYPE())) {
                    fieldMappingDto.setES_DATA_TYPE("short");
                }
                fieldsMap.put(fieldMappingDto.getCOLUMN_NAME(), buildTypeMap(fieldMappingDto.getES_DATA_TYPE()));
                fieldMappingDtoList.add(fieldMappingDto);
            }
            for (FieldMappingDto dto : fieldMappingDtoList) {
                StringBuilder sb = new StringBuilder();
                sb.append(" <result column=\"")
                        .append(dto.getCOLUMN_NAME())
                        .append("\" property=\"")
                        .append(dto.getCOLUMN_NAME())
                        .append("\"/>");
                System.out.println(sb);

            }

            boolean isCreateResult = indexService.createIndex(tableName, 3, 1);
            boolean isAddMapResult = mappingsService.addMapping(tableName, fieldsMap);
            log.info("TableController.createEsIndexAndMappings tableName:{} - isCreateResult:{} - isAddMapResult:{}", tableName, isCreateResult, isAddMapResult);
        }

        return fieldMappingDtoList;
    }

    @ApiOperation(value = "查询订单", httpMethod = "GET", response = List.class, notes = "/simple")
    @GetMapping("order")
    public List<RetailB2cOrderDo> getAllRetailB2cOrder() {
        return retailB2cOrderService.getAll(0, 10);
    }

    @ApiOperation(value = "插入订单数据到es", httpMethod = "PUT", response = List.class, notes = "/simple")
    @PutMapping("order")
    public Boolean saveDocument(@ApiParam(name = "pageSize", value = "多少页数据1页/500", required = true)
                                @RequestParam(value = "pageSize") Integer pageSize) {
        for (Integer i = 1; i <= pageSize; i++) {
            List<RetailB2cOrderDo> list = retailB2cOrderService.getAll((i - 1) * 500, 500);
            for (RetailB2cOrderDo orderDo : list) {
                String id = orderDo.getOrder_id().toString();
                Map<String, Object> docMap = JSONObject.parseObject(JSONObject.toJSONString(orderDo), Map.class);
                boolean result = documentService.createDoc("retail_b2c_order", docMap, id);
                log.info("创建结果：result:{}", result);
            }

        }
        return true;
    }


    private Map<String, Object> buildTypeMap(String esDataType) {
        Map<String, Object> typeMap = new HashMap<>(1);
        typeMap.put("type", esDataType);
        return typeMap;
    }
}
