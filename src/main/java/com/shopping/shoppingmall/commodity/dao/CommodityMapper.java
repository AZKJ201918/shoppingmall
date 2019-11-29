package com.shopping.shoppingmall.commodity.dao;


import com.shopping.shoppingmall.commodity.entity.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CommodityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Commodity record);

    int insertSelective(Commodity record);

    Commodity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Commodity record);

    int updateByPrimaryKey(Commodity record);


    List<Commodity> selectByCommodtiy(Long id);

    @Select("select * FROM commodity where  cname LIKE  CONCAT('%',#{cname},'%')")
    List<Commodity> selectNavigation(String cname);

    @Update("UPDATE commodity  SET stock=stock-1  WHERE id=#{id}")
    void upShopCart(Long id);

    @Update("UPDATE commodity  SET stock=stock+1  WHERE id=#{id}")
    void upByStock(Long id);
    @Update("UPDATE commodity  SET stock=stock+#{number}  WHERE id=#{cid}")
    void upEmptyCart(@Param("cid") Long cid, @Param("number") Integer number);

    @Update("UPDATE commodity  SET stock=stock+#{number}  WHERE id=#{cid}")
    void updatQuartzTask(@Param("cid") Integer cid, @Param("number") Integer number);

    @Select("select price FROM commodity where id=#{key}")
    int selectBycommodtiyPrice(Integer key);

    @Select("SELECT* FROM commodity WHERE status=5")
    List<Commodity> SelectRecommended();

    @Select("SELECT* FROM commodity WHERE status=4")
    List<Commodity> SelectHotWaimaoquan();

    @Select("SELECT* FROM commodity WHERE status=6")
    List<Commodity> SelectHewProducts();

    List<Commodity> SelectShoppingSpree();
}