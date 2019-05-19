package com.zk.elasticsearchlog.db.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("TableDo")
@Data
public class TableDo {
    private String tableName;

    private String ddl;
}
