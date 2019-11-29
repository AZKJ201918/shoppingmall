package com.shopping.shoppingmall.user.dao;


import com.shopping.shoppingmall.user.entity.UcouponRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UcouponRoleMapper {
    int deleteByPrimaryKey(Integer ucid);

    int insert(UcouponRole record);

    int insertSelective(UcouponRole record);

    UcouponRole selectByPrimaryKey(Integer ucid);

    int updateByPrimaryKeySelective(UcouponRole record);

    int updateByPrimaryKey(UcouponRole record);
    @Select("SELECT * FROM ucoupon_role where uid=#{uid} and cid=#{cid}")
    UcouponRole selectByCoponRole(@Param("uid") Integer uid, @Param("cid") Integer cid);
}