## elastic search MarkDown

#### 工程背景
 1. 搭建本地集群
 2. 熟悉ES API
 
#### 功能介绍
 1. 索引创建删除 com.zk.elasticsearchlog.web.es.IndexController
 2. Mappings修改新增  com.zk.elasticsearchlog.web.es.MappingController
 3. 文档新增修改删除 com.zk.elasticsearchlog.web.es.DocumentController
 4. 搜索 com.zk.elasticsearchlog.web.es.SearchController
 
 5.整合了Swagger2 提供了Api文档 地址localhost:8080/swagger-ui.htm

 6.整合了Mysql 实现根据用户提供的表 自动创建索引/Mappings/插入文档 com.zk.elasticsearchlog.web.db.TableController
 7. 整合漏桶对API进行并发访问限制 SearchController 见注解 com.zk.elasticsearchlog.annotation.AnoRateLimiter
 
 
 
#### 下一阶段想法  

    2. 整合Kafka 