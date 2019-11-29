package com.shopping.shoppingmall.user.entity;

import lombok.Data;

@Data
public class Adress {
    //id 也可以不写值
    private Integer aid;
   //名称
    private String name;
    //手机号
    private String phone;
    //省
    private String province;
    //市
    private String city;
    //市或区
    private String country;
    //具体地址
    private String detailed;
    //用户ID
    private String uid;
    //是否是默认地址可以不写值
    private Integer status;

}