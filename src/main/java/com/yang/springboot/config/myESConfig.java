package com.yang.springboot.config;

import lombok.val;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

@Configuration
public class myESConfig extends AbstractElasticsearchConfiguration {
    @Bean("elasticsearchClient")
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("192.168.234.128:9200")
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
//    @Bean("transportClient")
//    public TransportClient transportClient() throws UnknownHostException {
        /**
         *         进行相关配置（本次测试不做额外配置）
         *         Settings settings=Settings.builder()
         *                 .put("cluster.name","集群名称")
         *                 .put("client.transport.sniff", true)
         *                 .put("client.transport.ping_timeout", "20s")
         *                 .put("client.transport.nodes_sampler_interval","20s")
         *                 .build();
         */

//    }
}
