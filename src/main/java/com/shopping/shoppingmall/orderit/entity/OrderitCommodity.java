package com.shopping.shoppingmall.orderit.entity;


import com.shopping.shoppingmall.commodity.entity.Commodity;
import com.shopping.shoppingmall.commodity.entity.CommodityPeopleAttribute;
import com.shopping.shoppingmall.commodity.entity.Shoppingspree;
import lombok.Data;

import java.util.List;

@Data
public class OrderitCommodity {
    private Integer id;

    private Integer oid;

    private Integer cid;

    private Integer number;

    private CommodityPeopleAttribute commodityPeopleAttribute;

    private Integer sid;

    private Shoppingspree shoppingspree;
}