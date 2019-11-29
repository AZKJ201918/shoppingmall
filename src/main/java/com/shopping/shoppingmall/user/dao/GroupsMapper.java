package com.shopping.shoppingmall.user.dao;


import com.shopping.shoppingmall.user.entity.Groups;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GroupsMapper {
    int deleteByPrimaryKey(Integer gid);

    int insert(Groups record);

    int insertSelective(Groups record);

    Groups selectByPrimaryKey(Integer gid);

    int updateByPrimaryKeySelective(Groups record);

    int updateByPrimaryKey(Groups record);

    @Select("select * from groups WHERE NOW() BETWEEN startTime AND  endTime and gtype=0 GROUP BY startTime DESC")
    List<Groups> selectByGroup();
    @Update("UPDATE groups SET gtype=2 WHERE NOW()>endTime AND gtype=0")
    void UpdateGroups();

}