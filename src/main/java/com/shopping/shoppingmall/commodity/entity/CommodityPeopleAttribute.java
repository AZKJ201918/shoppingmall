package com.shopping.shoppingmall.commodity.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CommodityPeopleAttribute {
    private Integer id;

    private Long cid;

    private Integer aid;

    private Integer pid;

    private Double price;

    private Date createtime;

    private Date updatetime;

    private Property property;

    private Attribute attribute;

    private Commodity commodity;

    private Boolean isSelect=false;

    private Integer right;

    private Shoppingspree shoppingspree;

}