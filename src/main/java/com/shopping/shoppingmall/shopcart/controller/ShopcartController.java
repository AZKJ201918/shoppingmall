package com.shopping.shoppingmall.shopcart.controller;



import com.shopping.shoppingmall.common.constants.Constants;
import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.common.resp.ApiResult;
import com.shopping.shoppingmall.shopcart.service.ShopcartService;
import com.shopping.shoppingmall.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(value = "购物车模块")
public class ShopcartController {

    @Autowired
    @Qualifier("shopcartServiceImpl")
    private ShopcartService shopcartService;


    //查看购物车
    @ApiOperation(value = "查看购物车",notes = "根据用户uid查看购物车",httpMethod = "GET")
    @ApiImplicitParam(name = "uid",required = true,dataType = "INT")
    @GetMapping("viewcart")
    public ApiResult Viewcart(Integer uid){
        ApiResult<User> result=new ApiResult<User>();
        try {
            User user=shopcartService.Selectviewcart(uid);
            result.setData(user);
            result.setMessage("查看成功");
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
    //添加购物车
    @ApiOperation(value = "添加购物车",notes = "根据用户uid，商品id添加购物车",httpMethod = "GET")
    @ApiImplicitParam(name = "uid,id",required = true,dataType = "int")
    @GetMapping("addcart")
    public ApiResult Addcart(Integer uid, Long id){
        ApiResult result= new ApiResult();
        try {
            shopcartService.AddShopCart(uid, id);
            result.setMessage("添加购物车成功");
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

    //减少数量
    @ApiOperation(value = "减少数量",notes = "根据用户uid，商品id减少数量",httpMethod = "GET")
    @ApiImplicitParam(name = "uid，id",required = true,dataType = "INT")
    @GetMapping("reducecart")
    public ApiResult Reducecart(Integer uid,Long id){
        ApiResult result=new ApiResult();
        try {
            shopcartService.ReduceCart(uid, id);
            result.setMessage("商品减少成功");
        }catch (SuperMarketException e){
            result.setMessage(e.getMessage());
            result.setCode(e.getStatusCode());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }

    //清空购物车
    @ApiOperation(value = "清空购物车",notes = "根据用户uid清空购物车",httpMethod = "GET")
    @ApiImplicitParam(name = "uid",required = true,dataType = "INT")
    @GetMapping("emptycart")
    public ApiResult Cmptycart(Integer uid){
        ApiResult result= new ApiResult();
        try {
            shopcartService.emptycart(uid);
            result.setMessage("清空成功");
        }catch (SuperMarketException e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return  result;
    }

    //清空某一个商品
    @ApiOperation(value = "清空某一个商品",notes = "根据用户uid，跟商品id清空某个商品",httpMethod = "GET")
    @ApiImplicitParam(name = "uid，id",required = true,dataType = "INT")
    @GetMapping("emptySomething")
    @RequestMapping("emptySomething")
    public  ApiResult EmptySomething(Integer uid,Long id){
        ApiResult result=new ApiResult();
        try {
            shopcartService.EmptySomething(uid,id);
            result.setMessage("删除成功");
        }catch (SuperMarketException e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return  result;
    }



}
