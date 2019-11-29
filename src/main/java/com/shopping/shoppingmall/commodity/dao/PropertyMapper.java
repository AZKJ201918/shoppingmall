package com.shopping.shoppingmall.commodity.dao;

import com.shopping.shoppingmall.commodity.entity.Property;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PropertyMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(Property record);

    int insertSelective(Property record);

    Property selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(Property record);

    int updateByPrimaryKey(Property record);

    @Select("SELECT pid,ptype FROM property WHERE cid=#{id}")
    List<Property> selectCommdity(Long id);
}