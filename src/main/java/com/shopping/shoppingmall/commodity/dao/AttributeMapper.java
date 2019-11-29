package com.shopping.shoppingmall.commodity.dao;

import com.shopping.shoppingmall.commodity.entity.Attribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AttributeMapper {
    int deleteByPrimaryKey(Integer aid);

    int insert(Attribute record);

    int insertSelective(Attribute record);

    Attribute selectByPrimaryKey(Integer aid);

    int updateByPrimaryKeySelective(Attribute record);

    int updateByPrimaryKey(Attribute record);

    @Select("SELECT aid,atype FROM attribute WHERE cid=#{id}")
    List<Attribute> selectCommdity(Long id);
}