package com.shopping.shoppingmall.orderit.entity;

import lombok.Data;
import java.util.Map;

@Data
public class OrderitCreate {
    //用户id
    private  Integer uid;
    //优惠卷id
    private  Integer couponid;
    //地址id
    private  Integer adressid;
    //运费
    private  Integer postage;
    //备注
    private  String remark;
    //商品详情
    private Map<Integer,Integer> mapOrderid;
}
