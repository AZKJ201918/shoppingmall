package com.shopping.shoppingmall.commodity.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Shoppingspree {
    private Integer sid;

    private Integer cid;

    private Date starttime;

    private Date endtime;


    private Integer sum;

    private Integer per;

    private Integer subtract;


}