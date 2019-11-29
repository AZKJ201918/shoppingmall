package com.shopping.shoppingmall.commodity.controller;

import com.shopping.shoppingmall.commodity.entity.Commodity;
import com.shopping.shoppingmall.commodity.entity.CommodityPeopleAttribute;
import com.shopping.shoppingmall.commodity.entity.Commoditytype;
import com.shopping.shoppingmall.commodity.service.CommodtiyService;
import com.shopping.shoppingmall.common.constants.Constants;
import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.common.resp.ApiResult;
import com.shopping.shoppingmall.homepage.service.HomepageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@Api(value = "商品模块")
public class CommodtiyController {


    @Autowired
    @Qualifier("commodtiyServiceImpl")
    private CommodtiyService commodtiyService;

    @Autowired
    @Qualifier("homepageServiceImpl")
    private HomepageService homepageService;

    //分类查找
    @ApiOperation(value = "分类查找",notes = "根据商品id进行查找",httpMethod ="GET" )
    @ApiImplicitParam(name = "id",required = true,dataType = "LONG")
    @GetMapping("/commodity")
    public ApiResult select(Long  id){

        ApiResult<List<Commodity>> result=new ApiResult<List<Commodity>>();
        try {
            List<Commodity> commodity = commodtiyService.select(id);
            result.setData(commodity);
            result.setMessage("查询成功");
        } catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;

    }

    //所有的商品类型
    @ApiOperation(value = "查看所有的商品类型",notes ="查看所有的商品",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("/commoditytype")
    public ApiResult selectByCommodity(){
        ApiResult<List<Commoditytype>> result=new ApiResult<List<Commoditytype>>();
        try {
        List<Commoditytype> commoditytypeList=commodtiyService.selectByCommoditytype();
            commoditytypeList.stream().forEach(
                    param->{
                        List<Commodity> commodityList =commodtiyService.select(param.getId());
//                        commodityList.stream().forEach(
//                                pas->{
//                                    commodities.stream().forEach(
//                                            pts->{
//                                                if(pas.getId()== pts.getId()){
//                                                    commodityList.remove(pas);
//                                                }
//                                            }
//                                    );
//                                }
//                        );
                        if (commodityList.size()!=0){
                            param.setCommodityList(commodityList);
                            param.setResult(true);
                        }
                    }
            );

//        for(Commoditytype commoditytype : commoditytypeList){
//            List<Commodity> commodityList =commodtiyService.select(commoditytype.getId());
//            for(Commodity commodity:commodityList){
//                for(Commodity commodity1:commodities){
//                    if(commodity1.getId()==commodity.getId()){
//                        commodityList.remove(commodity);
//                    }
//                }
//            }
//            if (commodityList.size()!=0){
//                commoditytype.setCommodityList(commodityList);
//                commoditytype.setResult(true);
//            }
//        }
        result.setData(commoditytypeList);
            result.setMessage("查询分类成功");
        } catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;

    }

    //导航搜索商品
    @ApiOperation(value = "导航搜索",notes = "根据名字进行匹配搜索",httpMethod = "GET")
    @ApiImplicitParam(name = "cname",required = true,dataType = "STRING")
    @GetMapping("navigation")
    public ApiResult<List<Commodity>> selectNavigation(String cname){

        ApiResult<List<Commodity>> result=new ApiResult<List<Commodity>>();
        try {
           List<Commodity> commodityList=commodtiyService.selectNavigation(cname);
            result.setData(commodityList);
            result.setMessage("搜素商品成功");
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


    //查询商品
    @ApiOperation(value = "查询某一个商品",notes = "根据商品id号来查",httpMethod = "GET")
    @ApiImplicitParam(name="id,uid",required = true,dataType = "INT")
    @GetMapping("selectCommdity")
    public   ApiResult selectCommdity(Long id,Integer uid){
        ApiResult<Commodity> result=new ApiResult<Commodity>();
        try {
            Commodity commodity=commodtiyService.selectCommdity(id,uid);
            result.setData(commodity);
            result.setMessage("搜素商品成功");
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


    @ApiOperation(value = "商品联动",notes = "根据pid,aid,cid 进行商品联动",httpMethod = "POST")
    @ApiImplicitParam(name="commodityPeopleAttribute",required = true,dataType = "CommodityPeopleAttribute")
    @PostMapping("/CommdityPeople")
    public ApiResult CommdityPeople(@RequestBody CommodityPeopleAttribute commodityPeopleAttribute){
        ApiResult<CommodityPeopleAttribute> result=new ApiResult<>();
        CommodityPeopleAttribute commodityPeopleAttribute1=commodtiyService.SelectCommdityPeople(commodityPeopleAttribute);
        result.setData(commodityPeopleAttribute1);
        result.setMessage("查询成功");
        return result;
    }







}
