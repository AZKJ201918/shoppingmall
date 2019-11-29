package com.shopping.shoppingmall.user.dao;

import com.shopping.shoppingmall.user.entity.Assemble;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AssembleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Assemble record);

    int insertSelective(Assemble record);

    Assemble selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Assemble record);

    int updateByPrimaryKey(Assemble record);

    @Select("SELECT * FROM assemble WHERE status=1 AND cid=#{id}")
    Assemble selectBoolen(Long id);


}