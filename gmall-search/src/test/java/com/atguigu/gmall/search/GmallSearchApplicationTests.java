package com.atguigu.gmall.search;

import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.search.feign.GmallPmsClient;
import com.atguigu.gmall.search.feign.GmallWmsClient;
import com.atguigu.gmall.search.pojo.Goods;
import com.atguigu.gmall.search.pojo.SearchAttr;
import com.atguigu.gmall.search.respository.GoodsRepository;
import com.atguigu.gmall.wms.entity.WareSkuEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class GmallSearchApplicationTests {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @Autowired
    private GmallPmsClient pmsClient;

    @Autowired
    private GmallWmsClient wmsClient;

    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    void contextLoads() {
        this.restTemplate.createIndex(Goods.class);
        this.restTemplate.putMapping(Goods.class);
    }

    @Test
    void importData(){

        Long pageNum = 1l;
        Long pageSize = 100L;

        do {
            //分页查询spu
            QueryCondition queryCondition = new QueryCondition();
            queryCondition.setPage(pageNum);
            queryCondition.setLimit(pageSize);
            Resp<List<SpuInfoEntity>> spusResp = this.pmsClient.querySpusByPage(queryCondition);
            List<SpuInfoEntity> spus = spusResp.getData();

            //遍历spu，查询sku
            spus.forEach(spuInfoEntity -> {
                Resp<List<SkuInfoEntity>> skuResp = this.pmsClient.querySkusBySpuId(spuInfoEntity.getId());
                List<SkuInfoEntity> skuInfoEntities = skuResp.getData();
                if(!CollectionUtils.isEmpty(skuInfoEntities)){//这里用判断空不是判断null，因为集合里空跟null两码事。。如新建一个对象默认值是空而不是null

                    //把sku转化为goods对象
                    List<Goods> goodsList = skuInfoEntities.stream().map(skuInfoEntity -> {
                        Goods goods = new Goods();

                        //查询搜索属性及值
                        Resp<List<ProductAttrValueEntity>> attrValueResp = this.pmsClient.querySearchValueBySpuId(spuInfoEntity.getId());
                        List<ProductAttrValueEntity> attrValueEntities = attrValueResp.getData();
                        if(!CollectionUtils.isEmpty(attrValueEntities)){
                            List<SearchAttr> searchAttrs = attrValueEntities.stream().map(productAttrValueEntity -> {
                                SearchAttr searchAttr = new SearchAttr();
                                searchAttr.setAttrId(productAttrValueEntity.getAttrId());
                                searchAttr.setAttrName(productAttrValueEntity.getAttrName());
                                searchAttr.setAttrValue(productAttrValueEntity.getAttrValue());
                                return searchAttr;
                            }).collect(Collectors.toList());
                            goods.setAttrs(searchAttrs);
                        }

                        //查询品牌
                        Resp<BrandEntity> brandEntityResp = this.pmsClient.queryBrandById(skuInfoEntity.getBrandId());
                        BrandEntity brandEntity = brandEntityResp.getData();
                        if (brandEntity != null) {
                            goods.setBrandId(brandEntity.getBrandId());
                            goods.setBrandName(brandEntity.getName());
                        }

                        //查询分类
                        Resp<CategoryEntity> categoryEntityResp = this.pmsClient.queryCategoryById(skuInfoEntity.getCatalogId());
                        CategoryEntity categoryEntity = categoryEntityResp.getData();
                        if (categoryEntity != null) {
                            goods.setCategoryId(skuInfoEntity.getCatalogId());
                            goods.setCategoryName(categoryEntity.getName());
                        }

                        goods.setCreateTime(spuInfoEntity.getCreateTime());
                        goods.setPic(skuInfoEntity.getSkuDefaultImg());
                        goods.setPrice(skuInfoEntity.getPrice().doubleValue());
                        goods.setSale(0l);//销量直接设置为0
                        goods.setSkuId(skuInfoEntity.getSkuId());

                        //查询库存信息
                        Resp<List<WareSkuEntity>> listResp = this.wmsClient.queryWareSkusBySkuId(skuInfoEntity.getSkuId());
                        List<WareSkuEntity> wareSkuEntities = listResp.getData();
                        if(!CollectionUtils.isEmpty(wareSkuEntities)){
                            boolean flag = wareSkuEntities.stream().anyMatch(wareSkuEntity -> wareSkuEntity.getStock() > 0 );//只要有一个>0即为匹配上，返回true
                            goods.setStore(flag);
                        }
                        goods.setTitle(skuInfoEntity.getSkuTitle());
                        return goods;
                    }).collect(Collectors.toList());

                    this.goodsRepository.saveAll(goodsList);//saveAll参数是一个集合
                }
            });

            pageSize = (long)spus.size();
            pageNum++;

        }while (pageNum == 100);

    }

}
