package com.shopping.shoppingmall.commodity.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Attribute {
    private Integer aid;

    private Long cid;

    private String atype;

    private Date createtime;

    private Date updatetime;

}