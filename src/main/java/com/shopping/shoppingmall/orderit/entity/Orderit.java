package com.shopping.shoppingmall.orderit.entity;


import com.shopping.shoppingmall.user.entity.Adress;
import com.shopping.shoppingmall.user.entity.Coupon;
import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;
import java.util.List;

@Data
@SolrDocument(solrCoreName = "test3")
public class Orderit {

    @Field("id")
    private Integer id;
    @Field("orderit_orderid")
    private String orderid;
    @Field("orderit_uid")
    private Integer uid;
    @Field("orderit_allprice")
    private Integer allprice;

    @Field("orderit_couponid")
    private Integer couponid;

    @Field("orderit_zzprice")
    private Float zzprice;
    @Field("orderit_status")
    private Integer status;

    private Integer postage;
    @Field("orderit_status")
    private Integer adressid;

    @Field("orderit_remark")
    private String remark;

    @Field("orderit_keySign")
    private String keySign;

    @Field("orderit_createtime")
    private Date createtime;

    @Field("orderit_closetime")
    private Date closetime;

    @Field("orderit_sendtime")
    private Date sendtime;

    @Field("orderit_endTime")
    private Date endTime;

    @Field("orderit_couriernum")
    private String couriernum;
    @Field("orderit_couriername")
    private String couriername;

    private Integer otype;


    private Adress adress;

    private Coupon coupon;


    private  String orderitImg;

    private List<OrderitCommodity> orderitCommodityList;

    private Express express;




}