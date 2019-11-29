package com.shopping.shoppingmall.homepage.service.Impl;


import com.shopping.shoppingmall.commodity.dao.CommodityMapper;
import com.shopping.shoppingmall.commodity.entity.Commodity;
import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.homepage.dao.IntroduceMapper;
import com.shopping.shoppingmall.homepage.dao.SlideshowMapper;
import com.shopping.shoppingmall.homepage.entity.Introduce;
import com.shopping.shoppingmall.homepage.entity.Slideshow;
import com.shopping.shoppingmall.homepage.service.HomepageService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("homepageServiceImpl")
public class HomepageServiceImpl implements HomepageService {

    @Autowired
    private IntroduceMapper introduceMapper;

    @Autowired
    private SlideshowMapper slideshowMapper;

    @Autowired
    private CommodityMapper commodityMapper;


    public List<Slideshow> SelectCarousel() throws SuperMarketException {
        List<Slideshow> slideshowList=slideshowMapper.selectCarousel();
        for (Slideshow slideshow:slideshowList){
            Introduce introduce=introduceMapper.selectByIntroduce(slideshow.getSlid());
            if(introduce!=null){
                slideshow.setIntroduce(introduce);
                slideshow.setIsEmpty(false);
            }
        }
        return slideshowList;
    }


    public List<Commodity> SelectRecommended() throws SuperMarketException {
        List<Commodity> commodityList=commodityMapper.SelectRecommended();
        if(CollectionUtils.isNotEmpty(commodityList)){
            return commodityList;
        }else{
            throw  new SuperMarketException("没有店家推荐的商品");
        }
    }


    public List<Commodity> SelectHotWaimaoquan() throws SuperMarketException {
        List<Commodity> commodityList = commodityMapper.SelectHotWaimaoquan();
        if (CollectionUtils.isNotEmpty(commodityList)) {
            return commodityList;
        } else {
            throw new SuperMarketException("没有热门的商品");
        }
    }

    public List<Commodity> SelectHewProducts() throws SuperMarketException {

        List<Commodity> commodityList=commodityMapper.SelectHewProducts();
        if(CollectionUtils.isNotEmpty(commodityList)){
            return commodityList;
        }else{
            throw  new SuperMarketException("没有新品的商品");
        }
    }

    public List<Commodity> SelectShoppingSpree() throws SuperMarketException {
        List<Commodity> commodityList=commodityMapper.SelectShoppingSpree();
        if(CollectionUtils.isNotEmpty(commodityList)){
            return commodityList;
        }else{
            throw  new SuperMarketException("没有抢购的商品");
        }
    }
}
