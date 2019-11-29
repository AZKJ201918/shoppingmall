package com.shopping.shoppingmall.user.dao;


import com.shopping.shoppingmall.user.entity.Coupon;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CouponMapper {
    int deleteByPrimaryKey(Integer cid);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer cid);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    @Select("select cid from coupon where end_time=CURDATE()")
    List<Integer> CouponsRegularly();

    @Update("update coupon set status=2 where end_time=CURDATE() ")
    void UpdateCouponsRegularly();

    @Select("select cid,ctype,full_money as fullMoney,threshold_money as thresholdMoney,sale,start_time as startTime\n" +
            " ,end_time as endTime,howmany,number,draw,status from coupon where end_time>CURDATE() and status=3")
    List<Coupon> SelectPurchase();
    @Update("UPDATE coupon SET draw=draw+1 WHERE cid=#{cid}")
    void updateByCoupon(Integer cid);
}