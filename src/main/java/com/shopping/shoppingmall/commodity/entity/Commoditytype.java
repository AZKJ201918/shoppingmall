package com.shopping.shoppingmall.commodity.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Commoditytype implements Serializable {
    //类型ID
    private Long id;
    //类型
    private String ctype;
    //类型状态
    private String status;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //非空为空
    private Boolean result=false;

    private List<Commodity> commodityList;

}