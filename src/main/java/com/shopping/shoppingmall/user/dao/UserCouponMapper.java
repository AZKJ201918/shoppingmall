package com.shopping.shoppingmall.user.dao;


import com.shopping.shoppingmall.user.entity.UserCoupon;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserCouponMapper {
    int deleteByPrimaryKey(Integer ucid);

    int insert(UserCoupon record);

    int insertSelective(UserCoupon record);

    UserCoupon selectByPrimaryKey(Integer ucid);

    int updateByPrimaryKeySelective(UserCoupon record);

    int updateByPrimaryKey(UserCoupon record);

    @Delete("DELETE  FROM user_coupon where cid=#{cid}")
    void UserCouponsRegularly(Integer cid);

    @Delete("DELETE  FROM user_coupon where cid=#{cid} and uid=#{uid}")
    void updateByAggregatePrice(@Param("uid") Integer uid, @Param("cid") Integer cid);

    @Select("select * from user_coupon where cid=#{cid} and uid=#{uid}")
    UserCoupon SelectByDrawDown(@Param("cid") Integer cid, @Param("uid") Integer uid);
}