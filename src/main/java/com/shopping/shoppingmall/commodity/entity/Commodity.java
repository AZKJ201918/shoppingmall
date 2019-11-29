package com.shopping.shoppingmall.commodity.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Commodity implements Serializable {
    //商品ID
    private Long id;
    //商品类型ID
    private Long commoditytypeid;
    //商品名称
    private String cname;
    //商品副标题
    private String subtitle;
    //商品图片地址 url
    private String mainImage;
    //图片地址,json格式,扩展用
    private String subImages;
    //商品详情
    private String detail;
    //价格
    private Double price;
    //库存
    private Integer stock;
    //销量
    private Integer salesVolume;
    //商品状态
    private Long status;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
   //测试数量
    private Integer goodnum;

    private  Boolean isSelect;

    private Integer right;

    private  Boolean collectible=false;

    private Boolean isGroup=false;

    private Shoppingspree shoppingspree;

    List<Property> propertyList;

    List<Attribute>  attributeList;


}