package com.snow.es.api;

import com.alibaba.fastjson.JSON;
import com.snow.es.entity.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 讲解ES 7.8.0 高级客户端API测试
 */
@SpringBootTest
class SpringbootEsApiApplicationTests {

    @Autowired
    @Qualifier(value = "restHighLevelClient")
    private RestHighLevelClient client;

    @Test
    void testCreateIndex() throws IOException {
        // 1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("bntang666_index");

        // 2.客户端执行请求
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

        System.out.println(response);
    }


    /**
     * 测试获取索引，判断其是否存在
     */
    @Test
    void testExistsIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("bntang666_index");

        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);

        System.out.println(exists);
    }

    /**
     * 测试删除索引
     */
    @Test
    void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("bntang666_index");

        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);

        System.out.println(response.isAcknowledged());
    }

    /**
     * 测试添加文档
     */
    @Test
    void testAddDocument() throws IOException {
        // 1.创建对象
        User user = new User("灰灰", 23);

        // 2.创建请求
        IndexRequest request = new IndexRequest("bntang666_index");

        // 3.规则
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));

        // 4.将我们的数据放入请求中
        request.source(JSON.toJSONString(user), XContentType.JSON);

        // 5.客户端发送请求，获取响应结果
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
        System.out.println(response.status());
    }

    /**
     * 获取文档，判断是否存在 GET /snow_index/_doc/1
     */
    @Test
    void testIsExists() throws IOException {
        GetRequest request = new GetRequest("bntang666_index","1");

        // 不获取返回的_source的上下文了
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");

        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 获取文档的信息
     */
    @Test
    void testGetDocument() throws IOException {
        GetRequest request = new GetRequest("bntang666_index", "1");

        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSource());
        System.out.println(response);
    }

    /**
     * 更新文档的信息
     */
    @Test
    void testUpdateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest("bntang666_index", "1");
        request.timeout("1s");

        User user = new User("灰灰说Java", 18);

        request.doc(JSON.toJSONString(user), XContentType.JSON);

        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    /**
     * 删除文档记录
     */
    @Test
    void testDeleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("bntang666_index", "1");
        request.timeout("1s");

        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    /**
     * 特殊的，真实的项目一般都会批量插入数据
     */
    @Test
    void testBatchInsertDocument() throws IOException {
        BulkRequest request = new BulkRequest();
        request.timeout("10s");

        List<Object> list = new ArrayList<>();
        list.add(new User("BNTang1", 23));
        list.add(new User("BNTang2", 23));
        list.add(new User("BNTang3", 23));

        list.add(new User("BNTang6661", 23));
        list.add(new User("BNTang6662", 23));
        list.add(new User("BNTang6663", 23));

        for (int i = 0; i < list.size(); i++) {
            request.add(
                    new IndexRequest("bntang666_index")
                            .id("" + (i + 1))
                            .source(JSON.toJSONString(list.get(i)), XContentType.JSON)
            );
        }

        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.hasFailures());
    }

    /**
     * 查询
     * SearchRequest 搜索请求
     * SearchSourceBuilder 条件构造
     * HighLightBuilder 构建高亮
     * MatchAllQueryBuilder 匹配所有
     * TermQueryBuilder 精确查询
     * xxxQueryBuilder 对应我们刚才看到的命令
     */
    @Test
    void testSearch() throws IOException {
        SearchRequest request = new SearchRequest("bntang666_index");

        // 构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 查询条件，我们可以使用
        TermQueryBuilder termQuery = QueryBuilders.termQuery("name", "BNTang1");
        sourceBuilder.query(termQuery);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        request.source(sourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(response.getHits()));
        System.out.println("======================================");

        for (SearchHit searchHit : response.getHits().getHits()) {
            System.out.println(searchHit.getSourceAsMap());
        }
    }

}


