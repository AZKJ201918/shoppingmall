package com.shopping.shoppingmall.orderit.entity;

import lombok.Data;

@Data
public class Group {
    private  Integer gid;

    private Integer uid;

    //地址id
    private  Integer adressid;
    //运费
    private  Integer postage;
    //备注
    private  String remark;

}
