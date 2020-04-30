package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.gmall.pms.dao.AttrDao;
import com.atguigu.gmall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gmall.pms.entity.AttrEntity;
import com.atguigu.gmall.pms.vo.GroupVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.AttrGroupDao;
import com.atguigu.gmall.pms.entity.AttrGroupEntity;
import com.atguigu.gmall.pms.service.AttrGroupService;
import org.springframework.util.CollectionUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrAttrgroupRelationDao relationDao;//用于查询关系表

    @Autowired
    private AttrDao attrDao;//用于查询参数表

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo queryGroupByPage(QueryCondition condition, Long catId){

        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();//条件构造器
        if(catId != null){
            wrapper.eq("catelog_id", catId);
        }
        IPage<AttrGroupEntity> page = this.page(//调用的serviceimpl的selectpage方法
                new Query<AttrGroupEntity>().getPage(condition),//转化成IPage对象,zhe ge page() fang fa zhi neng chu li IPage
                wrapper
        );

        return new PageVo(page);//page是mybatisplus产生的分页结果集

    }

    @Override
    //根据组id拿到中间表关系，再拿到参数。最后返回的是符合的条件的组字段+关系字段+规格参数字段
    public GroupVO queryGroupWithAttrsByGid(Long gid) {

        GroupVO groupVO = new GroupVO();
        //查询group（查的组表）
        AttrGroupEntity groupEntity = this.getById(gid);//根据id查询（在实体类已经设置好的主键id）
        BeanUtils.copyProperties(groupEntity, groupVO);//左属性设置到右属性

        //根据gid查询关联关系，并获取attrIds(查的关系表)
        List<AttrAttrgroupRelationEntity> relations = this.relationDao
                .selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", gid));
        if(CollectionUtils.isEmpty(relations)){
            return groupVO;
        }
        groupVO.setRelations(relations);

        //根据attrIds查询，所有的规格参数（用stream表达式
        //拿attrId(关系表上把组id映射到所有参数id上)
        List<Long> attrIds = relations.stream()//stream().map(x -> xx.get())用来获取某一列的值，再.collect转化为集合
                .map(RelationEntity -> RelationEntity.getAttrId()).collect(Collectors.toList());// collect作用就是把attrId的值们转合成新的一个集合
        List<AttrEntity> attrEntities = this.attrDao.selectBatchIds(attrIds);//进来的是id集合，多个id，所以用的batchIds.mybatisplus的方法
        groupVO.setAttrEntities(attrEntities);

        return groupVO;
    }

    @Override
    public List<GroupVO> queryGroupWithAttrsByCid(Long cid) {

        //根据cid查询三级分类下的所有属性分组
        List<AttrGroupEntity> groupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", cid));

        //1.根据分组中的id查询中间表2.根据中间表的attrIds查询参数3.map里做了个AttrGroupEntity到GroupVO转化（最终使得集合类型转化：List<AttrGroupEntity>到List<GroupVO>的数据类型转化；）
        List<GroupVO> groupVOS = groupEntities.stream().map(attrGroupEntity ->
            //根据组id拿到中间表关系，再拿到参数
            this.queryGroupWithAttrsByGid(attrGroupEntity.getAttrGroupId())
        ).collect(Collectors.toList());
        return groupVOS;
    }

}