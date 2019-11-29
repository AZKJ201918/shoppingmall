package com.shopping.shoppingmall.commodity.dao;


import com.shopping.shoppingmall.commodity.entity.Shoppingspree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ShoppingspreeMapper {
    int deleteByPrimaryKey(Integer sid);

    int insert(Shoppingspree record);

    int insertSelective(Shoppingspree record);

    Shoppingspree selectByPrimaryKey(Integer sid);

    int updateByPrimaryKeySelective(Shoppingspree record);

    int updateByPrimaryKey(Shoppingspree record);

    @Select("SELECT * FROM shoppingspree where curtime() BETWEEN startTime AND endTime and cid=#{id}")
    Shoppingspree SelectShoppingspree(Long id);
}