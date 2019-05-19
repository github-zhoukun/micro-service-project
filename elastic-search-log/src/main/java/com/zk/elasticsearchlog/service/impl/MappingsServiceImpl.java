package com.zk.elasticsearchlog.service.impl;

import com.zk.elasticsearchlog.es.MappingProcess;
import com.zk.elasticsearchlog.service.MappingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 实现
 *
 * @author zhoukun
 */
@Service
@Slf4j
public class MappingsServiceImpl implements MappingsService {
    private MappingProcess process;

    @Autowired
    public MappingsServiceImpl(MappingProcess process) {
        this.process = process;
    }


    @Override
    public boolean addMapping(String indexName, Map<String, Object> map) {
        boolean result = false;
        try {
            result = process.addMapping(indexName, map);
        } catch (IOException e) {
            log.error("MappingsServiceImpl addMapping IOException ...");
        }
        return result;
    }

    @Override
    public Map<String, Object> getJavaMapping(String indexName) {
        Map<String, Object> map = null;
        try {
            map = process.getJavaMapping(indexName);
        } catch (IOException e) {
            log.error("MappingsServiceImpl getJavaMapping IOException ...");
        }
        return map;
    }

    @Override
    public Map<String, Object> getFieldMapping(String indexName, String... fields) {
        Map<String, Object> map = null;
        try {
            map = process.getFieldMapping(indexName, fields);
        } catch (IOException e) {
            log.error("MappingsServiceImpl getFieldMapping IOException ...");
        }
        return map;
    }


}
