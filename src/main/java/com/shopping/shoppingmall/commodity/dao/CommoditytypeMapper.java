package com.shopping.shoppingmall.commodity.dao;


import com.shopping.shoppingmall.commodity.entity.Commoditytype;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommoditytypeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Commoditytype record);

    int insertSelective(Commoditytype record);

    Commoditytype selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Commoditytype record);

    int updateByPrimaryKey(Commoditytype record);

    Commoditytype selectByCommodtiy(Long id);

    @Select("select * from commoditytype")
    List<Commoditytype> selectByCommoditytype();
}