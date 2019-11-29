package com.shopping.shoppingmall.user.dao;


import com.shopping.shoppingmall.user.entity.Collect;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CollectMapper {
    int deleteByPrimaryKey(Integer coid);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Integer coid);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    @Select("select * from collect where uid=#{uid} and cid=#{cid}")
    Collect selectByCollect(@Param("uid") Integer uid, @Param("cid") Integer cid);


    @Delete("DELETE FROM collect where uid=#{uid} and cid=#{cid")
    int deletecancelCollect(@Param("uid") Integer uid, @Param("cid") Integer cid);
}