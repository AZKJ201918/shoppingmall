package com.shopping.shoppingmall.shopcart.service.Impl;


import com.shopping.shoppingmall.commodity.dao.CommodityMapper;
import com.shopping.shoppingmall.commodity.dao.CommodityPeopleAttributeMapper;
import com.shopping.shoppingmall.commodity.dao.ShoppingspreeMapper;
import com.shopping.shoppingmall.commodity.entity.Commodity;
import com.shopping.shoppingmall.commodity.entity.CommodityPeopleAttribute;
import com.shopping.shoppingmall.commodity.entity.Shoppingspree;
import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.common.utils.UserUtil;
import com.shopping.shoppingmall.shopcart.dao.ShopcartMapper;
import com.shopping.shoppingmall.shopcart.entity.Shopcart;
import com.shopping.shoppingmall.shopcart.service.ShopcartService;
import com.shopping.shoppingmall.user.dao.UserMapper;
import com.shopping.shoppingmall.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("shopcartServiceImpl")
@Slf4j
public class ShopcartServiceImpl implements ShopcartService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShopcartMapper shopcartMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private ShoppingspreeMapper shoppingspreeMapper;


    @Autowired
    private CommodityPeopleAttributeMapper commodityPeopleAttributeMapper;

    @Autowired
    private UserUtil userUtil;


    public User Selectviewcart(Integer uid) throws SuperMarketException {
        userUtil.UserElent(uid);
        User user=userMapper.Selectviewcart(uid);
        if(user==null){
            throw  new SuperMarketException("购物车没有商品");
        }
        user.getShopcartList().stream().forEach(
                para->{
                    CommodityPeopleAttribute attribute=commodityPeopleAttributeMapper.selectByPrimaryKey(Math.toIntExact(para.getCid()));

                    Shoppingspree shoppingspree=shoppingspreeMapper.SelectShoppingspree(attribute.getCid());
                    if(shoppingspree!=null){
                        para.getCommodityPeopleAttribute().setShoppingspree(shoppingspree);
                    }
                    para.getCommodityPeopleAttribute().setCid(attribute.getCid());
                    para.getCommodityPeopleAttribute().setIsSelect(true);
                    para.getCommodityPeopleAttribute().setRight(0);
                }
        );

        return user;
    }

    @Transactional
    public void AddShopCart(Integer uid, Long id) throws SuperMarketException {
        userUtil.UserElent(uid);
        CommodityPeopleAttribute  commodityPeopleAttribute = commodityPeopleAttributeMapper.selectByPrimaryKey(Math.toIntExact(id));
        if(commodityPeopleAttribute==null){
            throw  new SuperMarketException("没有此商品");
        }
        Commodity commodity=commodityMapper.selectByPrimaryKey(commodityPeopleAttribute.getCid());
    if (commodity.getStock()-1 > 0) {
                Shopcart shopcart = shopcartMapper.selectByUidAndid(uid, id);
                if (shopcart == null) {
                     shopcartMapper.insertCart(uid, id);
                } else {
                    Shoppingspree shoppingspree=shoppingspreeMapper.SelectShoppingspree(commodity.getId());
                    if(shoppingspree!=null){
                        if(shopcart.getNumber()>=shoppingspree.getPer()){
                            throw new SuperMarketException("此商品为限时抢购商品最多加入购物车数量为"+shoppingspree.getPer());
                        }
                    }
                    shopcartMapper.updateCart(uid, id);
                }
        commodityMapper.upShopCart(id);
            } else {
                throw new SuperMarketException("库存不够,无法添加");
            }
    }



    @Transactional
    public void emptycart(Integer uid) throws SuperMarketException {
            userUtil.UserElent(uid);
            List<Shopcart>  shopcartList=shopcartMapper.SelectByShopUid(uid);
            shopcartList.stream().forEach(
                    para->{
                        commodityMapper.upEmptyCart(para.getCid(),para.getNumber());
                    }
            );
            int i= shopcartMapper.EmptyCart(uid);
      if(i<0){
          throw  new SuperMarketException("购物车里面没有商品");
      }
    }



    @Transactional(rollbackFor = Exception.class)
    public void ReduceCart(Integer uid, Long id) throws SuperMarketException {
        userUtil.UserElent(uid);
        Shopcart shopcart = shopcartMapper.selectByUidAndid(uid, id);
        if (shopcart == null) {
            throw  new SuperMarketException("没有减少的商品");
        }else{
            if (shopcart.getNumber() - 1 > 0) {
                shopcartMapper.updateCartNumber(shopcart.getSid());
            } else {
                shopcartMapper.deleteByPrimaryKey(shopcart.getSid());
            }
           commodityMapper.upByStock(id);

        }
    }

    public void EmptySomething(Integer uid, Long id) throws SuperMarketException {
        userUtil.UserElent(uid);
        Shopcart shopcart = shopcartMapper.selectByUidAndid(uid, id);
        if(shopcart==null){
            throw  new SuperMarketException("没有删除的商品");
        }
        shopcartMapper.deleteByPrimaryKey(shopcart.getSid());
    }
    }

