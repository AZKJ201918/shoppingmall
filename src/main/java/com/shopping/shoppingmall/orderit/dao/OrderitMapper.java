package com.shopping.shoppingmall.orderit.dao;


import com.shopping.shoppingmall.orderit.entity.Orderit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Orderit record);

    int insertSelective(Orderit record);

    Orderit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Orderit record);

    int updateByPrimaryKey(Orderit record);


    Orderit SelectOrderDetails(@Param("uid") Integer uid, @Param("id") Integer id);

    @Select("select * from orderit where uid=#{uid} and status!=30 AND otype=1 GROUP BY closetime DESC")
    List<Orderit> SelectallOrderDetails(Integer uid);

    @Select("select * from orderit where closetime=CURDATE() and status=10 ")
    List<Orderit> selectQuartzTask();

    @Select("select * from orderit where orderid=#{orderid}")
    Orderit SelectNotification(String orderid);

    @Select("select * FROM orderit WHERE orderid=#{orderid} AND uid=#{uid} ")
    Orderit selectOrderid(@Param("orderid") String orderid, @Param("uid") Integer uid);
     @Select("select  left(end_time,10) tdate ,SUM(zzprice)FROM  orderit GROUP BY  left(end_time,10)")
    List<Map<Date,Integer>> liushui();
    @Select("SELECT * FROM orderit WHERE orderid=#{orderid} AND status=40")
    Orderit SelectconfirmReceipt(String orderid);

    @Select("SELECT * FROM orderit WHERE status in(10,50,0) AND uid=#{uid} AND orderid=#{orderid}")
    Orderit selectByDeleteorderit(@Param("orderid")String orderid, @Param("uid")Integer uid);
}