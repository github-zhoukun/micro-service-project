package com.zk.elasticsearchlog.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ES 配置Config
 *
 * @author zhoukun
 */

//@Component
//@ConfigurationProperties(prefix = "elastic.search.client", ignoreInvalidFields = false)
//@PropertySource(value = "classpath:elasticsearch.properties", ignoreResourceNotFound = false, encoding = "UTF-8", name = "elasticsearch.properties")
@Configuration
public class ElasticSearchConfig {


    @Bean
    @ConfigurationProperties(prefix = "es", ignoreInvalidFields = false)
    public EsConfigBean esConfigBean() {
        return new EsConfigBean();
    }

    @Bean
    public Settings settings(EsConfigBean esConfigBean) {
        Settings settings = Settings.builder()
                .put(esConfigBean.getClusterNameKey(), esConfigBean.getClusterNameValue())
                .put(esConfigBean.getClientTransportSniffKey(), esConfigBean.getClientTransportSniffValue())
                .build();
        return settings;
    }

    /**
     * 操作客户端 将在8.0 版本中彻底移除
     *
     * @param settings
     * @param esConfigBean
     * @return
     * @throws UnknownHostException
     */
    @Bean
    public TransportClient transportClient(Settings settings, EsConfigBean esConfigBean) throws UnknownHostException {
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(esConfigBean.getIp()), 9300));
        return client;
    }

    /**
     * 官网推荐客户端
     *
     * @param esConfigBean 配置
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient(EsConfigBean esConfigBean) {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(esConfigBean.getIp(), esConfigBean.getPort(), esConfigBean.getProtocol())
                ).setRequestConfigCallback(
                        new RestClientBuilder.RequestConfigCallback() {
                            @Override
                            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                                builder.setConnectTimeout(5000);
                                builder.setSocketTimeout(40000);
                                builder.setConnectionRequestTimeout(1000);
                                return builder;
                            }
                        })
        );
        return client;
    }
}
