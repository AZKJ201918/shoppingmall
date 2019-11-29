package com.shopping.shoppingmall.user.entity;

import lombok.Data;

import java.util.Date;


@Data
public class Assemble {
    private Integer id;

    private String size;

    private String discount;

    private Long cid;

    private Date createTime;

    private Date updateTime;

    private String status;

}