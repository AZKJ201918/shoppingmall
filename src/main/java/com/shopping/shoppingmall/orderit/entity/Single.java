package com.shopping.shoppingmall.orderit.entity;

import lombok.Data;

@Data
public class Single {

    private  Integer uid;

    private  Integer cid;

    //优惠卷id
    private  Integer couponid;
    //地址id
    private  Integer adressid;
    //运费
    private  Integer postage;
    //备注
    private  String remark;
    //商品详情
}
