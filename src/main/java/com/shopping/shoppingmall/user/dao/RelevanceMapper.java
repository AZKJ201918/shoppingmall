package com.shopping.shoppingmall.user.dao;

import com.shopping.shoppingmall.user.entity.Relevance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RelevanceMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(Relevance record);

    int insertSelective(Relevance record);

    Relevance selectByPrimaryKey(Integer rid);

    int updateByPrimaryKeySelective(Relevance record);

    int updateByPrimaryKey(Relevance record);

    @Select("SELECT a.size FROM relevance r INNER JOIN assemble a ON a.id=r.aid WHERE gid=#{gid}")
    int SelectBygid(Integer gid);

    @Select("SELECT a.discount FROM relevance r INNER JOIN assemble a ON a.id=r.aid WHERE gid=#{gid}")
    int SelectBydisCount(Integer gid);
}