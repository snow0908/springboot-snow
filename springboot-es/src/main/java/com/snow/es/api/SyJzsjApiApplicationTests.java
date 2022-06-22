package com.snow.es.api;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snow.es.entity.TbSyJzsj;
import com.snow.es.service.TbSyJzsjService;
import com.sun.org.apache.regexp.internal.RE;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * MybatiesAPI测试
 */
@SpringBootTest
class SyJzsjApiApplicationTests {
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;
    @Resource
    private TbSyJzsjService tbSyJzsjService;

    @Test
    void testCreateIndex() throws IOException {
        //1. 创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("jzsj_index");
        //2. 客户端执行创建请求 自动补全返回值快捷键（ctrl+alt+v）
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    @Test
    void testBulkRequest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("30s");
        TbSyJzsj jzsj = new TbSyJzsj();
        jzsj.setChzjzlxbm("3");
        List<TbSyJzsj> userList = tbSyJzsjService.list(new QueryWrapper<>(jzsj));
        //批处理请求
        for (int i = 0; i < userList.size(); i++) {
            //批量更新和批量删除，就在这里修改对应的请求就可以了
            bulkRequest.add(
                    new IndexRequest("jzsj_index")
                            .id("" + (i + 1))
                            .source(JSON.toJSONString(userList.get(i)), XContentType.JSON)
            );
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        //是否失败，成功返回false，失败返回true
        System.out.println(bulkResponse.hasFailures());
    }
    @Test
    void searchEs() throws IOException {
        SearchRequest searchRequest = new SearchRequest("jzsj_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder() ;
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        List<TbSyJzsj> list = getList();

        for (TbSyJzsj term:list) {
            boolQueryBuilder.should(QueryBuilders.boolQuery()
                    .must(QueryBuilders.termQuery("chzbs.keyword",term.getChzbs()))
                            .must(QueryBuilders.termQuery("cyljgdm.keyword",term.getCyljgdm()))
                            .must(QueryBuilders.termQuery("cjzh.keyword",term.getCjzh())));
        }
        boolQueryBuilder.must(QueryBuilders.matchQuery("czdmc.keyword","不稳定型心绞痛"));
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        System.out.println(searchRequest.source());
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println("//////////////////////////////////////////////////////////////");
        for (SearchHit hit : hits.getHits()) {
            System.out.println(hit);
        }

    }
    public List<TbSyJzsj> getList(){
        LambdaQueryWrapper<TbSyJzsj> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TbSyJzsj::getChzjzlxbm,"3");
        queryWrapper.eq(TbSyJzsj::getCjzksdm,"2061");
        queryWrapper.like(TbSyJzsj::getChzbs,"000289");
        queryWrapper.like(TbSyJzsj::getChzbs,"58");
        System.out.println();
        return tbSyJzsjService.list(queryWrapper);
    }



    @Test
    void searchEs2() throws IOException {
        SearchRequest searchRequest = new SearchRequest("jzsj_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        List<TbSyJzsj> list = getList();
        BoolQueryBuilder boolQueryBuilder_2 = QueryBuilders.boolQuery();
        for (TbSyJzsj term:list) {
            boolQueryBuilder_2.should(QueryBuilders.boolQuery()
                    .must(QueryBuilders.termQuery("chzbs.keyword",term.getChzbs()))
                    .must(QueryBuilders.termQuery("cyljgdm.keyword",term.getCyljgdm()))
                    .must(QueryBuilders.termQuery("cjzh.keyword",term.getCjzh())));
        }
        boolQueryBuilder.must(boolQueryBuilder_2);
        boolQueryBuilder.must(QueryBuilders.termQuery("czdmc.keyword","不稳定型心绞痛"));
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        System.out.println(searchRequest.source());
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println("//////////////////////////////////////////////////////////////");
        for (SearchHit hit : hits.getHits()) {
            System.out.println(hit);
        }

    }


}




