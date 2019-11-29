package com.shopping.shoppingmall.user.entity;


import com.shopping.shoppingmall.shopcart.entity.Shopcart;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class User implements Serializable{
    private Integer uid;
    //微信号
    private String openid;
    //真实名字
    private String name;
    //手机号
    private String iphone;
    //省
    private String province;
    //市
    private String city;

    private Date createTime;

    private List<Collect> collectList;

    private List<Shopcart> shopcartList;

    private List<UserCoupon> userCouponList;

}