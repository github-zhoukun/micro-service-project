package com.zk.elasticsearchlog.service.impl;

import com.zk.elasticsearchlog.es.IndexProcess;
import com.zk.elasticsearchlog.exception.ParamNullException;
import com.zk.elasticsearchlog.mode.dto.IndexDto;
import com.zk.elasticsearchlog.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 实现
 *
 * @author zhoukun
 */
@Service
@Slf4j
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IndexProcess indexProcess;

    @Override
    public Boolean createIndex(String indexName, Integer indexNumberOfShards, Integer indexNumberOfReplicas) {
        IndexDto indexInfo = IndexDto.builder()
                .name(indexName)
                .indexNumberOfShards(indexNumberOfShards)
                .indexNumberOfReplicas(indexNumberOfReplicas)
                .build();

        boolean result = false;
        try {
            result = indexProcess.createIndex(indexInfo);
        } catch (ParamNullException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error("createIndex IOException ...");
        } catch (Exception e) {
            log.error("createIndex Exception ...");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Boolean existsIndex(String indexName) {
        boolean result = false;
        try {
            result = indexProcess.existsIndex(indexName);
        } catch (IOException e) {
            log.error("existsIndex IOException ...");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Boolean deleteIndex(String indexName) {
        boolean result = false;
        try {
            result = indexProcess.deleteIndex(indexName);
        } catch (IOException e) {
            log.error("deleteIndex IOException ...");
        }
        return result;
    }
}
