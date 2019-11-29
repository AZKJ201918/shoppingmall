package com.shopping.shoppingmall.user.dao;


import com.shopping.shoppingmall.user.entity.Adress;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdressMapper {
    int deleteByPrimaryKey(Integer aid);

    int insert(Adress record);

    int insertSelective(Adress record);

    Adress selectByPrimaryKey(Integer aid);

    int updateByPrimaryKeySelective(Adress record);

    int updateByPrimaryKey(Adress record);

    @Select("select * from adress where uid=#{uid}")
    List<Adress> selectAdress(Integer uid);

    @Select("select * from adress where uid=#{uid} and aid=#{aid}  ")
    Adress SelectChangeTheAddress(@Param("uid") Integer uid, @Param("aid") Integer aid);

    @Update("UPDATE adress SET status=0 where uid=#{uid}")
    void UpdateChangeTheAddress(Integer uid);

    @Delete("DELETE FROM adress WHERE uid=#{uid} AND aid=#{aid}")
    int DeleteAddAdress(@Param("uid") Integer uid, @Param("aid") Integer aid);

    @Select("select * from adress where uid=#{uid} and aid=#{aid}")
    Adress SingleAdress(@Param("uid") Integer uid, @Param("aid") Integer aid);
}