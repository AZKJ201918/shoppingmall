package com.shopping.shoppingmall.user.dao;


import com.shopping.shoppingmall.user.entity.People;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PeopleMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(People record);

    int insertSelective(People record);

    People selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(People record);

    int updateByPrimaryKey(People record);

    @Select("select * from people where gid=#{gid}")
    List<People> selectByGroup(Integer gid);

    @Select("select COUNT(*)　 FROM people WHERE gid=#{gid} GROUP BY gid")
    int selectCount(Integer gid);

    @Select("select * from people where uid=#{uid}")
    List<People> selectByPeople(Integer uid);

    @Select("select * from people where uid=#{uid} and gid=#{gid}")
    People selectByBollen(@Param("uid") Integer uid, @Param("gid") Integer gid);


    @Select("SELECT COUNT(*) FROM people  WHERE ptype=1 AND gid=#{gid} GROUP BY ptype")
    Integer selectCountPtype(Integer gid);
}