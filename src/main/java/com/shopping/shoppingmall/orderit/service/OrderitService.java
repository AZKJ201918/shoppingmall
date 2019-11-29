package com.shopping.shoppingmall.orderit.service;



import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.orderit.entity.Group;
import com.shopping.shoppingmall.orderit.entity.Orderit;
import com.shopping.shoppingmall.orderit.entity.OrderitCreate;
import com.shopping.shoppingmall.orderit.entity.Single;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderitService {

    Orderit SelectOrderDetails(Integer uid, Integer id)throws SuperMarketException;

    List<Orderit> SelectallOrderDetails(Integer uid)throws  SuperMarketException;

    Orderit AggregatePrice(OrderitCreate orderitCreate) throws SuperMarketException, ParseException;

    void SelectOrderitTask();

    Map<String,Object> SinglePurchase(Single single) throws SuperMarketException, ParseException;

    void Notification(Orderit orderit)throws SuperMarketException;

    void Asynchronous(String openid, Float price)throws SuperMarketException;

    void cancelOrder(Orderit orderit)throws SuperMarketException;

    void paymentGroup(Group group) throws  SuperMarketException, ParseException;


    List<Map<Date,Integer>>  liushui();

//    List<Orderit>  select() throws IOException, SolrServerException;

    void SelectconfirmReceipt(String orderid) throws SuperMarketException;

    void Deleteorderit(String orderid, Integer uid)throws SuperMarketException;
}
