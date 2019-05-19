package com.zk.elasticsearchlog.es.example;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class ElasticSearchClientExample {

    public static TransportClient createClient() throws Exception {

        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch-app")
                .put("client.transport.sniff", true)
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        return client;
    }

    //实例一个json mapper
    ObjectMapper mapper = new ObjectMapper();

    /**
     * 创建
     *
     * @param client
     * @throws Exception
     */
    public static void createIndexDocument(TransportClient client) throws Exception {
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("name", "小红")
                .field("password", 1324)
                .field("age", "24")
                .field("created", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .endObject();

        IndexResponse respone = client.prepareIndex("access", "_doc", "1")
                .setSource(builder).get();

        System.out.println("es:" + respone.getIndex());
        System.out.println("_type:" + respone.getType());
        System.out.println("id:" + respone.getId());
        System.out.println("version:" + respone.getVersion());
        System.out.println("status:" + respone.status());
    }

    /**
     * 查询
     *
     * @param client
     * @throws Exception
     */
    public static void getIndexDocument(TransportClient client) throws Exception {
        GetResponse response = client.prepareGet("access", "_doc", "2").get();
        Map<String, Object> map = response.getSource();
        System.out.println("------");
    }

    /**
     * 批量操作
     *
     * @param client
     */
    public static void bulkRequest(TransportClient client) throws Exception {
        BulkRequestBuilder builder = client.prepareBulk();
        builder.add(client.prepareIndex("access", "_doc", "3")
                .setSource(jsonBuilder().startObject()
                        .field("name", "王思聪")
                        .field("password", 123123123)
                        .field("age", "36")
                        .field("created", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .endObject()));

        builder.add(client.prepareIndex("access", "_doc", "4")
                .setSource(jsonBuilder().startObject()
                        .field("name", "祁钰")
                        .field("password", 123123123)
                        .field("age", "26")
                        .field("created", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .endObject()));

        BulkResponse bulkItemResponses = builder.get();
        if (bulkItemResponses.hasFailures()) {
            System.out.println("失败");
        }
    }

    /**
     * 请求多个结果
     *
     * @param client
     */
    public static void multiGetIndexDocument(TransportClient client) {
        MultiGetResponse responses = client.prepareMultiGet().add("access", "_doc", "2", "1").get();
        Iterator<MultiGetItemResponse> multiGetItemResponseIterator = responses.iterator();
        while (multiGetItemResponseIterator.hasNext()) {
            GetResponse getResponse = multiGetItemResponseIterator.next().getResponse();
            System.out.println(JSONObject.toJSON(getResponse.getSource()));
        }

    }

    /**
     * 删除指定
     *
     * @param client
     * @throws Exception
     */
    public static void deleteDocument(TransportClient client) throws Exception {
        DeleteResponse response = client.prepareDelete("access", "_doc", "1").get();
        System.out.println("------");
    }

    /**
     * 根据请求结果删除
     *
     * @param client
     * @throws Exception
     */
    public static void deleteByQueryDocument(TransportClient client) throws Exception {
        BulkByScrollResponse response = new DeleteByQueryRequestBuilder(client, DeleteByQueryAction.INSTANCE)
                .filter(QueryBuilders.matchQuery("name", "小明"))
                .source("access").get();
        System.out.println(response.getDeleted());
    }

    /**
     * 更新
     *
     * @param client
     * @throws Exception
     */
    public static void updateDocument(TransportClient client) throws Exception {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("access");
        updateRequest.type("_doc");
        updateRequest.id("2");
        updateRequest.doc(jsonBuilder().startObject().field("lvoe", "me").endObject());
        UpdateResponse response = client.update(updateRequest).get();
        System.out.println("更新结果：" + response.status());

        //方式二
        UpdateResponse response2 = client.prepareUpdate("access", "_doc", "2")
                .setDoc(jsonBuilder().startObject().startObject().field("desc", "我是哈哈哈").endObject()).get();

    }


    public void createIndex(TransportClient client) throws Exception {
        //JSON文档生成方式
        //方式1
        Map<String, Object> json = new HashMap();
        json.put("age", 13);
        json.put("name", "小红");
        json.put("password", "123456");
        json.put("created", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        //使用第三方库来序列化您的bean，例如 Jackson
        byte[] jsonBytes = mapper.writeValueAsBytes(json);

        // 使用内置帮助器XContentFactory.jsonBuilder（）
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("name", "周坤")
                .field("password", 1324)
                .field("age", "24")
                .field("created", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .endObject();

    }

    /**
     * 批量处理 带监听
     * https://www.elastic.co/guide/en/elasticsearch/client/java-api/7.0/java-docs-bulk-processor.html
     *
     * @param client
     */
    public static void bulkProcess(TransportClient client) throws Exception {
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            /**
             *
             * 在批量执行之前调用此方法。例如，您可以查看numberOfActions request.numberOfActions()
             * @param l
             * @param bulkRequest
             */
            @Override
            public void beforeBulk(long l, BulkRequest bulkRequest) {
                System.out.println("批量执行之前调用此方法" + bulkRequest.numberOfActions());
            }

            /**
             * 批量执行后调用此方法。例如，您可以检查是否存在某些失败请求response.hasFailures()
             * @param
             * @param bulkRequest
             * @param bulkResponse
             */
            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {

            }

            /**
             * 批量失败并引发a时调用此方法 Throwable
             * @param l
             * @param bulkRequest
             * @param throwable
             */
            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {

            }
        })       //每10个请求执行批量处理
                .setBulkActions(10)
                //每5mb请求一次
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                //无论请求数量多少，我们都希望每隔5秒刷新一次
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                //设置并发请求数。值为0表示只允许执行单个请求。值1表示允许执行1个并发请求，同时累积新的批量请求。
                .setConcurrentRequests(1)
                //设置自定义退避策略，该策略最初将等待100毫秒，以指数方式增加并重试最多三次。
                // 每当一个或多个批量项目请求失败时，尝试重试，EsRejectedExecutionException 这表示可用于处理请求的计算资源太少。
                // 要禁用退避，请传递BackoffPolicy.noBackoff()。
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();

        bulkProcessor.add(new IndexRequest("book", "_doc")
                .source(jsonBuilder().startObject().field("number", "001").endObject()));
        bulkProcessor.add(new DeleteRequest("book", "_doc"));
        //刷新剩余的请求
        bulkProcessor.flush();
        //等待任务完成
        bulkProcessor.awaitClose(10, TimeUnit.MINUTES);
        //不会等待任务
        bulkProcessor.close();

    }

    public static void main(String[] args) throws Exception {
        TransportClient transportClient = createClient();
//        createIndexDocument(transportClient);
//        deleteDocument(transportClient);
//        deleteByQueryDocument(transportClient);
//        updateDocument(transportClient);
//        getIndexDocument(transportClient);
//        multiGetIndexDocument(transportClient);
//        bulkRequest(transportClient);
        bulkProcess(transportClient);
        transportClient.close();
        System.out.println();
    }
}
