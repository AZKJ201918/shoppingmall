package com.shopping.shoppingmall.commodity.dao;

import com.shopping.shoppingmall.commodity.entity.CommodityPeopleAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommodityPeopleAttributeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommodityPeopleAttribute record);

    int insertSelective(CommodityPeopleAttribute record);

    CommodityPeopleAttribute selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommodityPeopleAttribute record);

    int updateByPrimaryKey(CommodityPeopleAttribute record);

    @Select("SELECT * FROM commodity_people_attribute WHERE aid=#{aid} AND cid=#{cid} and pid=#{pid}")
    CommodityPeopleAttribute selectCommdityPeople(@Param("aid") Integer aid, @Param("cid") Long cid, @Param("pid") Integer pid);

    @Select("select price FROM commodity_people_attribute where id=#{cid}")
    Integer selectBycommodtiyPrice(Integer cid);

    @Select("select * FROM commodity_people_attribute where id=#{cid}")
    CommodityPeopleAttribute selectBycommodtiys(Integer cid);
}