package com.atguigu.gmall.search.service;

import com.atguigu.gmall.search.pojo.Goods;
import com.atguigu.gmall.search.pojo.SearchParam;
import com.atguigu.gmall.search.pojo.SearchResponseAttrVO;
import com.atguigu.gmall.search.pojo.SearchResponseVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author rzhstart
 * @create 2020 - 03 - 28 - 19:51
 */

@Service
public class SearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();//yon用于对象序列化成字符串的工具，化成json

    public SearchResponseVO search(SearchParam searchParam) throws IOException {

        //构建DSL语句
        SearchRequest searchRequest = this.buildQueryDsl(searchParam);
        SearchResponse response = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);//放入查询条件

        //把response变成responseVO
        SearchResponseVO responseVO = this.parseSearchResult(response);
        responseVO.setPageSize(searchParam.getPageSize());
        responseVO.setPageNum(searchParam.getPageNum());
        return responseVO;
    }

    //把response变成responseVO
    private SearchResponseVO parseSearchResult(SearchResponse response) throws JsonProcessingException {
        SearchResponseVO responseVO = new SearchResponseVO();

        //从命中结果集hits中取总记录数
        SearchHits hits = response.getHits();
        responseVO.setTotal(hits.totalHits);

        //从SearchResponseAttrVO对象中获取品牌属性进行设置
        SearchResponseAttrVO brand = new SearchResponseAttrVO();
        brand.setName("品牌");
        //获取聚合结果集
        Map<String, Aggregation> aggregationMap = response.getAggregations().asMap();//把聚合名称作为key了（brandIdAgg、attrAgg、categoryIdAgg），后面的是聚合内容
        //获取品牌的聚合结果集，解析查询品牌名称的值
        ParsedLongTerms brandIdAgg = (ParsedLongTerms)aggregationMap.get("brandIdAgg");//强转之后的类型有获取桶的方法
        List<String> brandValues = brandIdAgg.getBuckets().stream().map(bucket -> {//拿到聚合后的多个桶，变成map再进行序列化放到list集合
            Map<String, String> map = new HashMap<>();
            map.put("id", bucket.getKeyAsString());//获取品牌ID（buckets的key）放到map
            Map<String, Aggregation> brandIdSubMap = bucket.getAggregations().asMap();//获取品牌名称（buckets的子聚合的key），通过buckets的子聚合获取
            ParsedStringTerms brandNameAgg = (ParsedStringTerms) brandIdSubMap.get("brandNameAgg");
            String brandName = brandNameAgg.getBuckets().get(0).getKeyAsString();//一个桶只有一个brand故get（0）第一个元素
            map.put("name", brandName);
            try {
                return OBJECT_MAPPER.writeValueAsString(map);   //序列化返回String，最终返回List<String>      .readValue是反序列化
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());//把每一个桶转化成map
        brand.setValue(brandValues);//参数是json字符串
        responseVO.setBrand(brand);

        //解析查询商品分类
        SearchResponseAttrVO category = new SearchResponseAttrVO();
        category.setName("分类");
        ParsedLongTerms categoryIdAgg = (ParsedLongTerms)aggregationMap.get("categoryIdAgg");//强转之后的类型有获取桶的方法
        List<String> cateValues = categoryIdAgg.getBuckets().stream().map(bucket -> {//拿到聚合后的桶，变成一个map再进行序列化
            Map<String, String> map = new HashMap<>();
            //获取分类ID
            map.put("id", bucket.getKeyAsString());
            //获取分类名称，通过子聚合获取
            Map<String, Aggregation> categoryIdSubMap = bucket.getAggregations().asMap();
            ParsedStringTerms categoryNameAgg = (ParsedStringTerms) categoryIdSubMap.get("categoryNameAgg");
            String categoryName = categoryNameAgg.getBuckets().get(0).getKeyAsString();
            map.put("name", categoryName);
            try {
                return OBJECT_MAPPER.writeValueAsString(map);   //序列化      .readValue是反序列化
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());//把每一个桶转化成map
        category.setValue(cateValues);
        responseVO.setCatelog(category);

        //解析查询商品列表
        SearchHit[] subHits = hits.getHits();
        List<Goods> goodsList = new ArrayList<>();
        for (SearchHit subHit : subHits) {//遍历的hits数组的元素的source，变成Goods对象
            Goods goods = OBJECT_MAPPER.readValue(subHit.getSourceAsString(), new TypeReference<Goods>() {//反序列化成goods对象
            });
            //拿高亮结果集设置到title
            goods.setTitle(subHit.getHighlightFields().get("title").getFragments()[0].toString());
            goodsList.add(goods);
        }
        responseVO.setProducts(goodsList);

        //规格参数的解析查询
        ParsedNested attrAgg = (ParsedNested) aggregationMap.get("attrAgg");//拿到嵌套聚合对象
        ParsedLongTerms attrIdAgg = (ParsedLongTerms) attrAgg.getAggregations().get("attrIdAgg");//规格参数的聚合对象
        List<Terms.Bucket> buckets = (List<Terms.Bucket>) attrIdAgg.getBuckets();//筒不为空才行
        if (!CollectionUtils.isEmpty(buckets)){
            List<SearchResponseAttrVO> searchResponseAttrVOS = buckets.stream().map(bucket -> {
                SearchResponseAttrVO responseAttrVO = new SearchResponseAttrVO();
                //设置规格参数Id
                responseAttrVO.setProductAttributeId(bucket.getKeyAsNumber().longValue());
                //设置规格参数名（子聚合的桶的key）
                List<? extends Terms.Bucket> nameBuckets = ((ParsedStringTerms) (bucket.getAggregations().get("attrNameAgg"))).getBuckets();
                responseAttrVO.setName(nameBuckets.get(0).getKeyAsString());
                //设置规格参数值列表
                List<? extends Terms.Bucket> valueBuckets = ((ParsedStringTerms) (bucket.getAggregations().get("attrValueAgg"))).getBuckets();
                List<String> values = valueBuckets.stream().map(Terms.Bucket::getKeyAsString)//对每个元素调用Bucket的getKeyAsString方法
                        .collect(Collectors.toList());
                responseAttrVO.setValue(values);
                return responseAttrVO;
            }).collect(Collectors.toList());
            responseVO.setAttrs(searchResponseAttrVOS);
        }
        return responseVO;
    }

    //把SearchParam封装成SearchRequest。Dsl的Java方法实现
    private SearchRequest buildQueryDsl(SearchParam searchParam){

        //查询关键字
        String keyword = searchParam.getKeyword();
        if(StringUtils.isEmpty(keyword)){
            return null;
        }
        //检索条件构建器(跟nativeSearchQueryBuild不同，nativeSearchQueryBuild用来请求数据过来到本地)
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //1.构建query（查询条件跟过滤条件）
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //1.1.构建查询条件
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", keyword).operator(Operator.AND));
        //1.2.构建过滤条件
        //1.2.1.构建品牌的过滤条件
        String[] brand = searchParam.getBrand();
        if(brand != null && brand.length != 0){
            boolQueryBuilder.filter(QueryBuilders.termsQuery("brandId", brand));
        }
        //1.2.2.构建分类的过滤条件
        String[] catelog3 = searchParam.getCatelog3();
        if(catelog3 != null && catelog3.length != 0){
            boolQueryBuilder.filter(QueryBuilders.termsQuery("categoryId", catelog3));
        }
        //1.2.3.构建嵌套的过滤条件（规格属性cpu型号属性组、电池型号组等）
        String[] props = searchParam.getProps();
        if(props != null && props.length != 0){
            for (String prop : props) {//可能根据多个prop嵌套进行过滤，所以对每一个prop都进行如下构建
                //切分为连两个元素才是合法的,1-attrId  2-attrValue(以-分割的字符串),不合法跳出本次prop属性组的过滤条件构建
                String[] split = StringUtils.split(prop, ":");
                if(split == null || split.length !=2){
                    continue;
                }
                //以-分割处理出attrValue每个元素
                String[] attrValues = StringUtils.split(split[1], "-");

                //构建嵌套查询里的bool请求
                BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();//嵌套里的bool请求
                //嵌套里bool的子boolquery
                BoolQueryBuilder subBoolQuery = QueryBuilders.boolQuery();//嵌套里bool的子bool
                //构建嵌套中的子查询中的过滤条件
                subBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", split[0]));//冒号前面的id
                subBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues));//冒号后面用—分隔的集合
                //filter下的bool.must
                boolQuery.must(QueryBuilders.nestedQuery("attrs", subBoolQuery, ScoreMode.None));
                //嵌套过滤放入过滤器中
                boolQueryBuilder.filter(boolQuery);
            }

        }
        //1.2.4.构建价格区间
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("price");
        Integer priceFrom = searchParam.getPriceFrom();
        Integer priceTo = searchParam.getPriceTo();
        if (priceFrom != null) {
            rangeQueryBuilder.gte(priceFrom);
        }
        if (priceTo != null) {
            rangeQueryBuilder.lte(priceTo);
        }
        boolQueryBuilder.filter(rangeQueryBuilder);

        //query放入检索构建器
        sourceBuilder.query(boolQueryBuilder);

        //2.构建分页（from size放入检索构建器）
        Integer pageNum = searchParam.getPageNum();
        Integer pageSize = searchParam.getPageSize();
        sourceBuilder.from((pageNum - 1) * pageSize);
        sourceBuilder.size(pageSize);

        //3.构建排序（0按照得分，1按照销量，2按照价格）
        String order = searchParam.getOrder();
        if(!StringUtils.isEmpty(order)){
            String[] split = StringUtils.split(order, ":");
            if(split != null && split.length == 2){
                String field = null;
                switch (split[0]){
                    case "1": field = "sale"; break;
                    case "2": field = "price"; break;
                }
                sourceBuilder.sort(field, StringUtils.equals("asc", split[1]) ? SortOrder.ASC : SortOrder.DESC);
            }
        }

        //4.构建高亮
        sourceBuilder.highlighter(new HighlightBuilder().field("title").preTags("<em>").postTags("</em>"));

        //5.构建聚合
        //5.1.品牌聚合
        sourceBuilder.aggregation(AggregationBuilders.terms("brandIdAgg").field("brandId")
                .subAggregation(AggregationBuilders.terms("brandNameAgg").field("brandName")));
        //5.2.分类聚合
        sourceBuilder.aggregation(AggregationBuilders.terms("categoryIdAgg").field("categoryId")
                .subAggregation(AggregationBuilders.terms("categoryNameAgg").field("categoryName")));
        //5.3.搜索的规格属性聚合（有个嵌套）
        sourceBuilder.aggregation(AggregationBuilders.nested("attrAgg", "attrs")
                .subAggregation(AggregationBuilders.terms("attrIdAgg").field("attrs.attrId")//聚合的子
                        .subAggregation(AggregationBuilders.terms("attrNameAgg").field("attrs.attrName"))//聚合子的两个子
                        .subAggregation(AggregationBuilders.terms("attrValueAgg").field("attrs.attrValue"))));

        System.out.println(sourceBuilder.toString());//输出看一下DSL语句

        //6.结果集过滤
        sourceBuilder.fetchSource(new String[]{"skuId", "pic", "title", "price"}, null);

        //查询参数
        SearchRequest searchRequest = new SearchRequest("goods");
        searchRequest.types("info");
        searchRequest.source(sourceBuilder);

        return searchRequest;
    }

}
