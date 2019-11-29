package com.shopping.shoppingmall.commodity.service.Impl;


import com.shopping.shoppingmall.commodity.dao.*;
import com.shopping.shoppingmall.commodity.entity.*;
import com.shopping.shoppingmall.commodity.service.CommodtiyService;
import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.user.dao.AssembleMapper;
import com.shopping.shoppingmall.user.dao.CollectMapper;
import com.shopping.shoppingmall.user.entity.Assemble;
import com.shopping.shoppingmall.user.entity.Collect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commodtiyServiceImpl")
public class CommodtiyServiceImpl implements CommodtiyService {

    @Autowired
    private CommoditytypeMapper commoditytypeMapper;

    @Autowired
    private CommodityMapper commodityMapper;


    @Autowired
    private CollectMapper collectMapper;


    @Autowired
    private AttributeMapper attributeMapper;

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private  ShoppingspreeMapper shoppingspreeMapper;


    @Autowired
    private AssembleMapper assembleMapper;

    @Autowired
    private CommodityPeopleAttributeMapper commodityPeopleAttributeMapper;


//    @Cacheable(cacheNames = Constants.CACHE_PRODUCT_COMMODITY,key = "#id")
    public List<Commodity> select(Long id)  {
        List<Commodity> commshopping=commodityMapper.SelectShoppingSpree();
        List<Commodity> commodityList=commodityMapper.selectByCommodtiy(id);


        commshopping.stream().forEach(
            part->{
                Long ss =part.getId();
                for (Integer i = 0; i < commodityList.size(); i++) {
                    if (ss == commodityList.get(i).getId()) {
                        commodityList.remove(commodityList.get(i));
                    }
                }
            }

);



        return commodityList;
    }
//    @Cacheable(cacheNames = Constants.CACHE_PRODUCT_CATEGORY)
    public List<Commoditytype> selectByCommoditytype() {
        return commoditytypeMapper.selectByCommoditytype();
    }


    public Commodity selectCommodit(Long id) {
        return commodityMapper.selectByPrimaryKey(id);
    }
    public List<Commodity> selectNavigation(String cname)throws SuperMarketException {
        return commodityMapper.selectNavigation(cname);
    }





    public Commodity selectCommdity(Long id,Integer uid) throws SuperMarketException {
        Commodity commodity=commodityMapper.selectByPrimaryKey(id);
        if (commodity==null){
            throw  new SuperMarketException("查找商品失败");
        }
        List<Attribute> attribute=attributeMapper.selectCommdity(id);
        List<Property> property=propertyMapper.selectCommdity(id);
        commodity.setAttributeList(attribute);
        commodity.setPropertyList(property);
        Shoppingspree shoppingspree=shoppingspreeMapper.SelectShoppingspree(commodity.getId());
        if(shoppingspree!=null){
            commodity.setShoppingspree(shoppingspree);
        }
        Assemble assemble=assembleMapper.selectBoolen(commodity.getId());
        if(assemble!=null){
            commodity.setIsGroup(true);
        }
        if (uid==0) {
            return commodity;
        }else{
            Collect collect=collectMapper.selectByCollect(uid, (int) (id*1));
            if(collect!=null){
                commodity.setCollectible(true);
            }
        }

        return commodity;
    }

    @Override
    public CommodityPeopleAttribute SelectCommdityPeople(CommodityPeopleAttribute commodityPeopleAttribute) {
        CommodityPeopleAttribute commodityPeopleAttributes;
        commodityPeopleAttributes=commodityPeopleAttributeMapper.selectCommdityPeople(commodityPeopleAttribute.getAid(),commodityPeopleAttribute.getCid(),commodityPeopleAttribute.getPid());
        Attribute attribute=attributeMapper.selectByPrimaryKey(commodityPeopleAttribute.getAid());
        Property property=propertyMapper.selectByPrimaryKey(commodityPeopleAttribute.getPid());
        Commodity commodity= commodityMapper.selectByPrimaryKey(commodityPeopleAttribute.getCid());
        commodityPeopleAttributes.setAttribute(attribute);
        commodityPeopleAttributes.setCommodity(commodity);
        commodityPeopleAttributes.setProperty(property);
        return commodityPeopleAttributes;
    }


}
