package com.shopping.shoppingmall.homepage.dao;


import com.shopping.shoppingmall.homepage.entity.Slideshow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SlideshowMapper {
    int deleteByPrimaryKey(Integer slid);

    int insert(Slideshow record);

    int insertSelective(Slideshow record);

    Slideshow selectByPrimaryKey(Integer slid);

    int updateByPrimaryKeySelective(Slideshow record);

    int updateByPrimaryKey(Slideshow record);

    @Select("select * from slideshow ")
    List<Slideshow> selectCarousel();
}