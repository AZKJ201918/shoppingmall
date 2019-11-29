package com.shopping.shoppingmall.homepage.dao;


import com.shopping.shoppingmall.homepage.entity.Introduce;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IntroduceMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(Introduce record);

    int insertSelective(Introduce record);

    Introduce selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(Introduce record);

    int updateByPrimaryKey(Introduce record);

    @Select("select * from introduce where slid=#{slid}")
    Introduce selectByIntroduce(Integer slid);
}