package com.shopping.shoppingmall.orderit.dao;


import com.shopping.shoppingmall.orderit.entity.OrderitCommodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderitCommodityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderitCommodity record);

    int insertSelective(OrderitCommodity record);

    OrderitCommodity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderitCommodity record);

    int updateByPrimaryKey(OrderitCommodity record);

    List<OrderitCommodity>SelectCommodityList(Integer id);

    @Select("select * from orderit_commodity where oid=#{id}")
    List<OrderitCommodity> selectQuartzTask(Integer id);
}