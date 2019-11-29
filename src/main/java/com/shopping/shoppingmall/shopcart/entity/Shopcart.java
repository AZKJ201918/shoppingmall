package com.shopping.shoppingmall.shopcart.entity;


import com.shopping.shoppingmall.commodity.entity.Commodity;
import com.shopping.shoppingmall.commodity.entity.CommodityPeopleAttribute;
import lombok.Data;

@Data
public class Shopcart {
    private Integer sid;

    private Integer uid;

    private Long cid;

    private Integer number;

   private CommodityPeopleAttribute commodityPeopleAttribute;

}