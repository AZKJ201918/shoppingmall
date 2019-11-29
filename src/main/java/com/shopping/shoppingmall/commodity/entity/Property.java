package com.shopping.shoppingmall.commodity.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Property {
    private Integer pid;

    private Integer cid;

    private String ptype;

    private Date createtime;

    private Date updatetime;


    }
