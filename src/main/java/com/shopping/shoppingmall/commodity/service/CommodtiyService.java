package com.shopping.shoppingmall.commodity.service;


import com.shopping.shoppingmall.commodity.entity.Commodity;
import com.shopping.shoppingmall.commodity.entity.CommodityPeopleAttribute;
import com.shopping.shoppingmall.commodity.entity.Commoditytype;
import com.shopping.shoppingmall.common.exception.SuperMarketException;

import java.util.List;

public interface CommodtiyService {
    List<Commodity> select(Long id);

    List<Commoditytype> selectByCommoditytype();

    Commodity selectCommodit(Long id);

    List<Commodity> selectNavigation(String cname) throws SuperMarketException;

    Commodity selectCommdity(Long id, Integer uid) throws  SuperMarketException;

    CommodityPeopleAttribute SelectCommdityPeople(CommodityPeopleAttribute commodityPeopleAttribute);
}
