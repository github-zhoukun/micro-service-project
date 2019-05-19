package com.zk.elasticsearchlog.db.service.impl;

import com.zk.elasticsearchlog.db.dao.TableDao;
import com.zk.elasticsearchlog.db.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 表相关操作
 *
 * @author zhoukun
 */
@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableDao tableDao;

    @Override
    public List<String> findAllTable(String dataBase) {
        return tableDao.findAllTable(dataBase);
    }

    @Override
    public List<Map> showDDL(String tableName) {
        return tableDao.showDDL(tableName);
    }
}
