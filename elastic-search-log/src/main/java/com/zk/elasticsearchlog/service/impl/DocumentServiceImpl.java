package com.zk.elasticsearchlog.service.impl;

import com.zk.elasticsearchlog.es.DocumentProcess;
import com.zk.elasticsearchlog.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * 实现
 *
 * @author zhoukun
 */
@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {
    private DocumentProcess process;

    @Autowired
    public DocumentServiceImpl(DocumentProcess process) {
        this.process = process;
    }

    @Override
    public boolean createDoc(String indexName, Map<String, Object> docMap, String id) {
        try {
            if (Objects.isNull(id)) {
                return process.createDoc(indexName, docMap);
            }
            return process.createDoc(indexName, docMap, id);
        } catch (IOException e) {
            log.error("DocumentServiceImpl.createDoc IOException");
            return false;
        }
    }

    @Override
    public Map<String, Object> getDoc(String indexName, String id) {
        try {
            if (Objects.isNull(id)) {
                return process.getDoc(indexName);
            }
            return process.getDoc(indexName, id);
        } catch (IOException e) {
            log.error("DocumentServiceImpl.getDoc IOException");
            return Collections.emptyMap();
        }
    }

    @Override
    public boolean existsDoc(String indexName, String id) {
        try {
            return process.existsDoc(indexName, id);
        } catch (IOException e) {
            log.error("DocumentServiceImpl.existsDoc IOException");
            return false;
        }

    }

    @Override
    public boolean updateDoc(String indexName, String id, Map<String, Object> docMap) {
        try {
            return process.updateDoc(indexName, id, docMap);
        } catch (IOException e) {
            log.error("DocumentServiceImpl.updateRequest IOException");
            return false;
        }

    }

    @Override
    public boolean deleteDoc(String indexName, String id) {
        try {
            if (Objects.isNull(id)) {
                return process.deleteDoc(indexName);
            }
            return process.deleteDoc(indexName, id);
        } catch (IOException e) {
            log.error("DocumentServiceImpl.updateRequest IOException");
            return false;
        }

    }


}
