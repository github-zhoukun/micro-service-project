package com.zk.elasticsearchlog.es;

import com.zk.elasticsearchlog.mode.vo.EsQueryVo;
import com.zk.elasticsearchlog.mode.vo.RangeQuery;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ES 搜索
 *
 * @author zhoukun
 */
@Component
public class SearchProcess {
    private RestHighLevelClient client;

    @Autowired
    public SearchProcess(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     * 查询
     *
     * @param indexName
     */
    @Deprecated
    public List<Map<String, Object>> search(String indexName, Map<String, Object> queryMap, int offSet, int limit) throws IOException {
        SearchRequest request = new SearchRequest(indexName);
        SearchSourceBuilder builder = buildSearchSource(queryMap, offSet, limit);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> map = hit.getSourceAsMap();
            list.add(map);
        }
        return list;
    }

    @Deprecated
    public List<Map<String, Object>> search(String indexName, Map<String, Object> queryMap, Map<String, String> sortMap,
                                            String[] includeFields, String[] excludeFields, int offSet, int limit) throws IOException {
        SearchRequest request = new SearchRequest(indexName);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if (queryMap == null || queryMap.isEmpty()) {
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        } else {
            queryMap.forEach((k, v) -> searchSourceBuilder.query(QueryBuilders.termQuery(k, v)));
        }
        if (sortMap != null && !sortMap.isEmpty()) {
            sortMap.forEach((k, v) -> searchSourceBuilder.sort(new FieldSortBuilder(k).order("DESC".equals(v) ? SortOrder.DESC : SortOrder.ASC)));
        }
        searchSourceBuilder.fetchSource(true);
        if (includeFields != null && includeFields.length > 0) {
            searchSourceBuilder.fetchSource(includeFields, excludeFields);
        }
        //分页
        searchSourceBuilder.from(offSet).size(limit);
        //超时时间
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        request.source(searchSourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> map = hit.getSourceAsMap();
            list.add(map);
        }
        return list;

    }

    /**
     * 构建查询参数类
     *
     * @return
     */
    @Deprecated
    public SearchSourceBuilder buildSearchSource(Map<String, Object> queryMap, int offSet, int limit) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询匹配
        if (queryMap == null || queryMap.isEmpty()) {
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        } else {
            queryMap.forEach((k, v) -> searchSourceBuilder.query(QueryBuilders.termQuery(k, v)));
        }
        //分页
        searchSourceBuilder.from(offSet).size(limit);
        //超时时间
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        return searchSourceBuilder;
    }

    /**
     * 构建查询参数类 2
     *
     * @param offSet
     * @param limit
     * @return
     */
    @Deprecated
    public SearchSourceBuilder buildSearchSource2(String key, String value, int offSet, int limit) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(key, value)
                //模糊查询
                .fuzziness(Fuzziness.AUTO)
                //前缀长度
                .prefixLength(3)
                //最大长度
                .maxExpansions(10);
        searchSourceBuilder.query(matchQueryBuilder).from(offSet).size(limit);
        //根据字段排序
        searchSourceBuilder.sort(new FieldSortBuilder("order_code").order(SortOrder.ASC));
        return searchSourceBuilder;
    }

    /**
     * 查询
     *
     * @param query
     */
    public List<Map<String, Object>> search(EsQueryVo query) throws IOException {
        SearchRequest request = new SearchRequest(query.getIndexName());
        request.source(buildSearchSource(query));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        return processResult(response);
    }


    /**
     * 处理响应
     *
     * @param response
     * @return
     */
    private List<Map<String, Object>> processResult(SearchResponse response) {
        SearchHits hits = response.getHits();
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> map = hit.getSourceAsMap();
            list.add(map);
        }
        return list;

    }

    /**
     * 构建查询条件
     *
     * @param query
     * @return
     */
    public SearchSourceBuilder buildSearchSource(EsQueryVo query) {
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        ssb.from(query.getFrom());
        ssb.size(query.getSize());
        ssb.fetchSource(true);
        ssb.fetchSource(query.getIncludeFields(), query.getExcludeFields());
        if (query.getSortMap() != null && !query.getSortMap().isEmpty()) {
            query.getSortMap().forEach((k, v) -> ssb.sort(new FieldSortBuilder(k).order("DESC".equalsIgnoreCase(v) ? SortOrder.DESC : SortOrder.DESC)));
        }
        ssb.query(builderBoolQuery(query));
        return ssb;
    }

    /**
     * 多条件
     *
     * @param query
     * @return
     */
    private BoolQueryBuilder builderBoolQuery(EsQueryVo query) {
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        buildMatchQuery(bqb, query);
        buildTermQuery(bqb, query);
        buildRangeQuery(bqb, query);
        return bqb;
    }

    /**
     * 构建MatchQuery
     *
     * @param bqb
     * @param query
     */
    private void buildMatchQuery(BoolQueryBuilder bqb, EsQueryVo query) {
        Map<String, Object> matchMap = query.getMatchMap();
        if (matchMap != null && !matchMap.isEmpty()) {
            matchMap.forEach((k, v) ->
                    bqb.must(QueryBuilders.matchQuery(k, v)));
        }
    }

    /**
     * 构建TermQuery
     *
     * @param bqb
     * @param query
     */
    private void buildTermQuery(BoolQueryBuilder bqb, EsQueryVo query) {
        Map<String, Object> termMap = query.getTermMap();
        if (termMap != null && !termMap.isEmpty()) {
            termMap.forEach((k, v) ->
                    bqb.must(QueryBuilders.termQuery(k, v)));
        }
    }

    /**
     * 构建RangeQuer
     *
     * @param bqb
     * @param query
     */
    private void buildRangeQuery(BoolQueryBuilder bqb, EsQueryVo query) {
        List<RangeQuery> rangeQueryList = query.getRangeQueryList();
        if (rangeQueryList != null && !rangeQueryList.isEmpty()) {
            rangeQueryList.forEach(rangeQuery ->
                    bqb.must(QueryBuilders.rangeQuery(rangeQuery.getFieldName())
                            .gte(rangeQuery.getGte())
                            .lte(rangeQuery.getLte()))
            );
        }
    }

}



