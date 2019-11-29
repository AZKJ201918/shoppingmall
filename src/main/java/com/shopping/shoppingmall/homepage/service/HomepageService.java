package com.shopping.shoppingmall.homepage.service;



import com.shopping.shoppingmall.commodity.entity.Commodity;
import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.homepage.entity.Slideshow;

import java.util.List;

public interface HomepageService {
    List<Slideshow> SelectCarousel()throws SuperMarketException;

    List<Commodity> SelectRecommended() throws SuperMarketException;

    List<Commodity> SelectHotWaimaoquan() throws SuperMarketException;

    List<Commodity> SelectHewProducts() throws SuperMarketException;

    List<Commodity> SelectShoppingSpree() throws SuperMarketException;
}
