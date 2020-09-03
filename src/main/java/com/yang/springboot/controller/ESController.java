package com.yang.springboot.controller;

import lombok.val;
import org.apache.ibatis.annotations.Update;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.query.QuerySearchRequest;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/es")
public class ESController {
    @Autowired
    @Qualifier("elasticsearchClient")
    private RestHighLevelClient elasticClient;
    @RequestMapping("add")
    public void add() throws IOException {
        IndexRequest indexRequest=new IndexRequest("ems","employee","1");
        indexRequest.source("{\"name\":\"小黑\",\"age\":\"23\"}", XContentType.JSON);
        IndexResponse indexResponse=elasticClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.status());
    }
    @RequestMapping("delete")
    public void delete() throws IOException {
        DeleteRequest deleteRequest=new DeleteRequest("ems","employee","1");
        DeleteResponse deleteResponse=elasticClient.delete(deleteRequest,RequestOptions.DEFAULT);
        System.out.println(deleteRequest);
    }
    @RequestMapping("query")
    public void query() throws IOException {
        Logger logger= LoggerFactory.getLogger(getClass());
        SearchRequest searchRequest=new SearchRequest().indices("ems");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery())
                .sort("age",SortOrder.DESC);
        searchRequest.types("employee").source(sourceBuilder);
        SearchResponse searchResponse=elasticClient.search(searchRequest,RequestOptions.DEFAULT);
        logger.info("查询总数："+String.valueOf(searchResponse.getHits().totalHits));
        logger.info("查询最高分数："+searchResponse.getHits().getMaxScore());
        for(SearchHit searchHit:searchResponse.getHits()){
            logger.info(searchHit.getSourceAsString());
        }
    }
}
