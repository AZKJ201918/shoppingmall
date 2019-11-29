package com.shopping.shoppingmall.orderit.dao;

import com.shopping.shoppingmall.orderit.entity.Express;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExpressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Express record);

    int insertSelective(Express record);

    Express selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Express record);

    int updateByPrimaryKey(Express record);


    Express selectByExpress(String orderid);
}