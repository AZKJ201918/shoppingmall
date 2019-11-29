package com.shopping.shoppingmall.user.entity;

import lombok.Data;

@Data
public class UserCoupon {
    private Integer ucid;

    private Integer uid;

    private Integer cid;

    private Coupon coupon;

}