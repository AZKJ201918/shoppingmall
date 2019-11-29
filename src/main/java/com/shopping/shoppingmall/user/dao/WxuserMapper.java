package com.shopping.shoppingmall.user.dao;


import com.shopping.shoppingmall.user.entity.Wxuser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface WxuserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Wxuser record);

    int insertSelective(Wxuser record);

    Wxuser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Wxuser record);

    int updateByPrimaryKey(Wxuser record);


    @Select("select * from wxuser where openid=#{openid}")
    Wxuser wxLogin(String openid);

    @Insert("INSERT  INTO wxuser (openid) VALUES (#{openid})")
    Integer insertWxOpenid(String openid);

    Wxuser SelectGroup(Integer uid);


    @Update("UPDATE wxuser SET online_grade=online_grade+#{zzprice} WHERE id=#{id}")
    void instNotification(@Param("id") Long id, @Param("zzprice") Float zzprice);

    @Update("UPDATE wxuser SET offline_grade=offline_grade+#{price} WHERE id=#{id}")
    void Asynchronous(@Param("id") Long id, @Param("price") Float price);
}