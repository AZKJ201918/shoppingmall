package com.shopping.shoppingmall.shopcart.dao;


import com.shopping.shoppingmall.shopcart.entity.Shopcart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShopcartMapper {
    int deleteByPrimaryKey(Integer sid);

    int insert(Shopcart record);

    int insertSelective(Shopcart record);

    Shopcart selectByPrimaryKey(Integer sid);

    int updateByPrimaryKeySelective(Shopcart record);

    int updateByPrimaryKey(Shopcart record);

    @Select("select * from shopcart where uid=#{uid} and cid=#{id}")
    Shopcart selectByUidAndid(@Param("uid") Integer uid, @Param("id") Long id);

    @Insert("INSERT  INTO shopcart (uid,cid) VALUES (#{uid},#{id})")
    Integer insertCart(@Param("uid") Integer uid, @Param("id") Long id);

    @Update("UPDATE shopcart SET number=number+1 WHERE uid=#{uid} AND cid=#{id}")
    Integer updateCart(@Param("uid") Integer uid, @Param("id") Long id);

    @Delete("DELETE FROM shopcart where uid=#{uid}")
    Integer EmptyCart(Integer uid);
    @Update("UPDATE shopcart SET number=number-1 WHERE sid=#{sid}")
    Integer updateCartNumber(Integer sid);

    @Select("select * from shopcart where uid=#{uid}")
    List<Shopcart> SelectByShopUid(Integer uid);

    @Update("UPDATE shopcart SET number=number-#{number} WHERE sid=#{sid}")
    void updateCartPrice(@Param("sid") Integer sid, @Param("number") Integer number);
}