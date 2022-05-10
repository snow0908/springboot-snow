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
import java.util.concurrent.TimeUnit;

/**
 * 讲解ES 7.8.0 高级客户端API测试
 */
@SpringBootTest
class SpringbootEsApiApplicationTests2 {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    /**
     * 测试索引的创建 Request
     */
    @Test
    void testCreateIndex() throws IOException {
        //1. 创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("snow_index");
        //2. 客户端执行创建请求 自动补全返回值快捷键（ctrl+alt+v）
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    /**
     * 测试获取索引，判断其是否存在
     */
    @Test
    void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("snow_index");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);

    }

    /**
     * 测试删除索引
     */
    @Test
    void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("snow_index");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());

    }

    /**
     * 测试添加文档
     */
    @Test
    void testAddDocument() throws IOException {
        //创建对象
        User user = new User("流云飘雨", 3);
        //创建请求
        IndexRequest request = new IndexRequest("snow_index");
        //规则 PUT /snow_index/_doc/1
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        //request.timeout("1s");
        //将我们的数据放入请求 json
        request.source(JSON.toJSONString(user), XContentType.JSON);
        //客户端发送请求
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        //返回创建文档的JSON字符串信息
        System.out.println(indexResponse.toString());
        //对应我们命令返回的状态
        System.out.println(indexResponse.status());

    }

    /**
     * 获取文档，判断是否存在 GET /snow_index/_doc/1
     */
    @Test
    void testIsExists() throws IOException {
        GetRequest getRequest = new GetRequest("snow_index", "1");
        //不获取返回的_source的上下文了
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 获取文档的信息
     */
    @Test
    void testGetDocument() throws IOException {
        GetRequest getRequest = new GetRequest("snow_index", "1");
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        //打印文档的内容
        System.out.println(getResponse.getSourceAsString());
        //返回的全部内容和命令是一样的
        System.out.println(getResponse);
    }

    /**
     * 更新文档的信息
     */
    @Test
    void testUpdateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("snow_index", "1");
        updateRequest.timeout("1s");
        User user = new User("流云飘雨233", 18);
        updateRequest.doc(JSON.toJSONString(user), XContentType.JSON);
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse.status());

    }

    /**
     * 删除文档记录
     */
    @Test
    void testDeleteDocument() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("snow_index", "1");
        deleteRequest.timeout("1s");
        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.status());

    }

    /**
     * 特殊的，真实的项目一般都会批量插入数据
     */
    @Test
    void testBulkRequest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User("snow1", 3));
        userList.add(new User("snow2", 3));
        userList.add(new User("snow3", 3));
        //批处理请求
        for (int i = 0; i < userList.size(); i++) {
            //批量更新和批量删除，就在这里修改对应的请求就可以了
            bulkRequest.add(
                    new IndexRequest("snow_index")
                            .id("" + (i + 1))
                            .source(JSON.toJSONString(userList.get(i)), XContentType.JSON)
            );
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        //是否失败，成功返回false，失败返回true
        System.out.println(bulkResponse.hasFailures());
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
    void testSearchRequest() throws IOException {
        SearchRequest searchRequest = new SearchRequest("snow_index");
        //构建搜索的条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询条件，我们可以使用QueryBuilders工具类来实现
        //QueryBuilders.termQuery() 精确查询
        //QueryBuilders.matchAllQuery() 匹配所有
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "snow1");
        searchSourceBuilder.query(termQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        //注意这里fastjson版本要用最新版本，不然转换JSON的时候会出现版本冲突
        System.out.println(JSON.toJSONString(searchResponse.getHits()));
        System.out.println("==============================");
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }

    }

}




