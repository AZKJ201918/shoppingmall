package com.shopping.shoppingmall.user.dao;

import com.shopping.shoppingmall.user.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    @Select("SELECT * FROM message WHERE phone=#{iphone} AND status=1")
    List<Message> SelectPhone(String iphone);
}