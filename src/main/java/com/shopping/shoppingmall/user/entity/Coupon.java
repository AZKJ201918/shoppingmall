package com.shopping.shoppingmall.user.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Coupon {
    //优惠卷Id
    private Integer cid;
    //优惠卷类型
    private Integer ctype;
    //满级多少
    private Integer fullMoney;
    //无门槛多少钱
    private Integer thresholdMoney;
   //打折多少钱
    private Integer sale;
   //开始时间
    private Date startTime;
    //过期时间
    private Date endTime;
    //满多少开始减
    private Integer howmany;

    //总量
    private Integer number;
    //已抢购
    private Integer draw;
    //状态
    private Integer status;

}