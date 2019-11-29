package com.shopping.shoppingmall.user.dao;


import com.shopping.shoppingmall.user.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @Insert("INSERT  INTO user (openid) VALUES (#{openid})")
    Integer insertUserOpenid(String openid);

    User Selectviewcart(Integer uid);

    User selectCoupon(Integer uid);

    User LookCollect(Integer uid);

    @Select("SELECT * FROM user where openid=#{openid}")
    User selectOpenid(String openid);

    @Select("select * from user")
    List<User> selectUser();

    @Select("SELECT * FROM user WHERE iphone=#{phone}")
    User selectByPhone(String phone);
}