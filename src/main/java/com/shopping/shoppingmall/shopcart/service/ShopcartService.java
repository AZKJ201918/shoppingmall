package com.shopping.shoppingmall.shopcart.service;


import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.user.entity.User;

public interface ShopcartService {
    User Selectviewcart(Integer uid)throws SuperMarketException;

    void AddShopCart(Integer uid, Long id) throws SuperMarketException;

    void emptycart(Integer uid) throws SuperMarketException;

    void ReduceCart(Integer uid, Long id)throws SuperMarketException;

    void EmptySomething(Integer uid, Long id) throws SuperMarketException;
}
