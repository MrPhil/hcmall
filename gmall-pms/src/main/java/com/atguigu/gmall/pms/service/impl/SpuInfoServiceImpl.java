package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.dao.SkuInfoDao;
import com.atguigu.gmall.pms.dao.SpuInfoDescDao;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.pms.feign.GmallSmsClient;
import com.atguigu.gmall.pms.service.*;
import com.atguigu.gmall.pms.vo.BaseAttrVO;
import com.atguigu.gmall.pms.vo.SkuInfoVO;
import com.atguigu.gmall.pms.vo.SpuInfoVO;
import com.atguigu.gmall.sms.vo.SkuSaleVO;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescDao descDao;//dao用于逐个遍历（调用方法得是字符串）

    @Autowired
    private ProductAttrValueService attrValueService;//service可用于批量保存(调用方法参数得是集合)

    @Autowired
    private SkuInfoDao skuInfoDao;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService SaleAttrValueService;

    @Autowired
    private GmallSmsClient gmallSmsClient;

    @Autowired
    private SpuInfoDescService spuInfoDescService;//通过接口注入进来调用保存方法

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo querySpuPage(QueryCondition condition, Long cid) {

        //SELECT * FROM guli_pms.pms_spu_info where catalog_id=225 and (id='华为' or spu_name like '%华为%');

        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
        //查询条件
        //判断分类是不是0（是0查全栈）
        if(cid != 0){
            wrapper.eq("catalog_id", cid);
        }

        //判断搜索关键字是否为空,如果不为空
        String key = condition.getKey();
        if(StringUtils.isNotBlank(key)){
            //      相当于上面sql语句的 and(xxx)       sql语句or后面没有()所以不用带函数接口的.or()
            //                                       如果直接.eq那么默认的是 .and().eq()
            wrapper.and(t -> t.eq("id", key).or().like("spu_name", key));//函数接口，有参数有返回值
        }

        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(condition),
                wrapper
        );

        return new PageVo(page);
    }

    @Override
    //保存顺序不能颠倒
    @GlobalTransactional
    public void bigSave(SpuInfoVO spuInfoVO) {
        //1.保存spu相关的3张表，1.1先保存（先存id）
        //1.1. 保存pms_spu_info
        Long spuId = saveSpuInfo(spuInfoVO);

        //1.2. 保存pms_spu_info_desc
        this.spuInfoDescService.saveSpuInfoDesc(spuInfoVO, spuId);

        //1.3. 保存pms_spu_attr_value
        saveBaseAttrValue(spuInfoVO, spuId);

        //保存skus
        saveSkuAndSale(spuInfoVO, spuId);
    }

    public void saveSkuAndSale(SpuInfoVO spuInfoVO, Long spuId) {
        List<SkuInfoVO> skus = spuInfoVO.getSkus();//对别的表操作，先获取前端传过来的skus的信息
        if(CollectionUtils.isEmpty(skus)){
            return ;
        }
        skus.forEach(skuInfoVO -> {//skus多个集合，遍历保存
            //2.保存sku相关的3张表，2.1先保存
            //2.1. 保存pms_sku_info
            skuInfoVO.setSpuId(spuId);
            skuInfoVO.setSkuCode(UUID.randomUUID().toString());//设置唯一标识字段
            skuInfoVO.setBrandId(spuInfoVO.getBrandId());
            skuInfoVO.setCatalogId(spuInfoVO.getCatalogId());
            List<String> images = skuInfoVO.getImages();
            //设置默认图片
            if(!CollectionUtils.isEmpty(images)){
                //如果默认图片没有就用images的第一张
                skuInfoVO.setSkuDefaultImg(StringUtils.isNoneBlank(skuInfoVO.getSkuDefaultImg()) ? skuInfoVO.getSkuDefaultImg() : images.get(0));
            }
            this.skuInfoDao.insert(skuInfoVO);
            Long skuId = skuInfoVO.getSkuId();

            //2.2. 保存pms_sku_images   //images是集合，可以批量保存。131行
            if(!CollectionUtils.isEmpty(images)){
                List<SkuImagesEntity> skuImagesEntities = images.stream().map(image -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setImgUrl(image);
                    skuImagesEntity.setSkuId(skuId);
                    //设置是否默认图片
                    skuImagesEntity.setDefaultImg(StringUtils.equals(skuInfoVO.getSkuDefaultImg(), image) ? 1 : 0);//如果默认图片与当前图片地址一样就是1，反之0
                    return skuImagesEntity;
                }).collect(Collectors.toList());//对象集合转化完毕
                this.skuImagesService.saveBatch(skuImagesEntities);
            }

            //2.3. 保存pms_sku_attr_value
            List<SkuSaleAttrValueEntity> saleAttrs = skuInfoVO.getSaleAttrs();
            if(!CollectionUtils.isEmpty(saleAttrs)){
                //设置skuId
                saleAttrs.forEach(skuSaleAttrValueEntity -> skuSaleAttrValueEntity.setSkuId(skuId));
                //批量保存
                this.SaleAttrValueService.saveBatch(saleAttrs);
            }

            //3.保存sms营销信息的3张表，平级关系,依赖于2.
            //3.1. 保存sms_sku_bounds;3.2. 保存sms_sku_ladders;3.3. 保存sms_sku_full_reduction
            //通过feign远程调用sms来实现保存
            SkuSaleVO skuSaleVO = new SkuSaleVO();
            BeanUtils.copyProperties(skuInfoVO, skuSaleVO);
            skuSaleVO.setSkuId(skuId);//SaleVo比InfoVO多一个属性
            this.gmallSmsClient.saveSale(skuSaleVO);
        });
    }

    public void saveBaseAttrValue(SpuInfoVO spuInfoVO, Long spuId) {
        List<BaseAttrVO> baseAttrs = spuInfoVO.getBaseAttrs();//获取基本属性
        if (!CollectionUtils.isEmpty(baseAttrs)) {
            //类型转为ProductAttrValueEntity才能放入attrValueService的saveBatch
            List<ProductAttrValueEntity> attrValueEntities = baseAttrs.stream().map(baseAttrVO -> {
                ProductAttrValueEntity attrValueEntity = baseAttrVO;
                attrValueEntity.setSpuId(spuId);//前端不传spuid，自己设置
                return attrValueEntity;
            }).collect(Collectors.toList());
            this.attrValueService.saveBatch(attrValueEntities);//写入数据库，批量保存
        }
    }

    public Long saveSpuInfo(SpuInfoVO spuInfoVO) {
        spuInfoVO.setCreateTime(new Date());
        spuInfoVO.setUodateTime(spuInfoVO.getCreateTime());
        this.save(spuInfoVO);
        return spuInfoVO.getId();
    }

}