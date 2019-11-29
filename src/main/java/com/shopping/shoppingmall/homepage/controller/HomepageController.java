package com.shopping.shoppingmall.homepage.controller;



import com.shopping.shoppingmall.commodity.entity.Commodity;
import com.shopping.shoppingmall.common.constants.Constants;
import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.common.resp.ApiResult;
import com.shopping.shoppingmall.homepage.entity.Slideshow;
import com.shopping.shoppingmall.homepage.service.HomepageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Api(value = "首页模块")
public class HomepageController {

    @Autowired
    @Qualifier("homepageServiceImpl")
    private HomepageService homepageService;


    @ApiOperation(value = "轮播图",notes = "轮播图",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("carousel")
    public ApiResult SelectCarousel(){
        ApiResult<List<Slideshow>> result=new ApiResult();
        try {
            List<Slideshow> slideshowList=homepageService.SelectCarousel();
            result.setData(slideshowList);
            result.setMessage("搜素轮播图成功");
        }catch (SuperMarketException e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }


    @ApiOperation(value = "店家推荐",notes = "店家推荐",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("recommended")
    public ApiResult SelectRecommended(){
        ApiResult<List<Commodity>> result=new ApiResult();
        try {
            List<Commodity> commodityList=homepageService.SelectRecommended();
            result.setData(commodityList);
            result.setMessage("搜索商品成功");
        }catch (SuperMarketException e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }

    @ApiOperation(value = "热门推荐",notes = "热门推荐",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("hotWaimaoquan")
    public ApiResult SelectHotWaimaoquan(){
        ApiResult<List<Commodity>> result=new ApiResult();
        try {
            List<Commodity> commodityList=homepageService.SelectHotWaimaoquan();
            result.setData(commodityList);
            result.setMessage("搜索商品成功");
        }catch (SuperMarketException e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }

    @ApiOperation(value = "新品推荐",notes = "新品推荐",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("newProducts")
    public ApiResult SelectHewProducts(){
        ApiResult<List<Commodity>> result=new ApiResult();
        try {
            List<Commodity> commodityList=homepageService.SelectHewProducts();
            result.setData(commodityList);
            result.setMessage("搜索商品成功");
        }catch (SuperMarketException e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }


    @ApiOperation(value = "限时抢购",notes = "限时抢购",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("shoppingSpree")
    public ApiResult SelectShoppingSpree(){
        ApiResult<List<Commodity>> result=new ApiResult();
        try {
            List<Commodity> commodityList=homepageService.SelectShoppingSpree();
            result.setData(commodityList);
            result.setMessage("搜索商品成功");
        }catch (SuperMarketException e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }
}
