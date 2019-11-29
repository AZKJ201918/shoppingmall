package com.shopping.shoppingmall.user.service;


import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.user.entity.User;
import com.shopping.shoppingmall.user.entity.Wxuser;

import java.util.Map;

public interface WxuserService {


    User wxLogin(String code, String name, String img)throws SuperMarketException;

    Wxuser LookIntegral(Integer uid)throws  SuperMarketException;

    Map<String,Integer> visitorVolume() throws SuperMarketException;

    void AlwaysVisitToday();
}
