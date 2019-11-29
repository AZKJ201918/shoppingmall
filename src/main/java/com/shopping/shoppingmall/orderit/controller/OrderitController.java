package com.shopping.shoppingmall.orderit.controller;


import com.shopping.shoppingmall.common.constants.Constants;
import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.common.resp.ApiResult;
import com.shopping.shoppingmall.orderit.entity.Group;
import com.shopping.shoppingmall.orderit.entity.Orderit;
import com.shopping.shoppingmall.orderit.entity.OrderitCreate;
import com.shopping.shoppingmall.orderit.entity.Single;
import com.shopping.shoppingmall.orderit.service.OrderitService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@Slf4j

public class OrderitController {

    @Autowired
    @Qualifier("orderitServiceImpl")
    private OrderitService orderitService;


    //查看单个信息
    @ApiOperation(value = "查看订单详情",notes = "根据用户uid跟订单id查看订单详情",httpMethod = "POST")
    @ApiImplicitParam(name = "orderit",required = true,dataType = "Orderit")
    @PostMapping("individualorder")
    public ApiResult Individualorder(@RequestBody Orderit orderit){
        ApiResult<Orderit> result=new ApiResult<Orderit>();
        try {
            Orderit orderits=orderitService.SelectOrderDetails(orderit.getUid(),orderit.getId());
            result.setData(orderits);
            result.setMessage("搜素订单详情成功");
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

    //订单详情
    @ApiOperation(value = "查看所有订单",notes = "根据用户uid查看所有订单",httpMethod = "GET")
    @ApiImplicitParam(name = "uid",required = true,dataType = "Integer")
    @GetMapping("allOrderDetails")
    public ApiResult allOrderDetails(Integer uid){
        ApiResult<List<Orderit>> result=new ApiResult();
        try {
            List<Orderit> orderitLits=orderitService.SelectallOrderDetails(uid);
            result.setData(orderitLits);
            result.setMessage("搜素订单成功");
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

//    //生成订单计算价格
    @ApiOperation(value = "生成订单" ,notes = "根据微信小程序传值生成订单",httpMethod = "POST")
    @ApiImplicitParam(name = "orderitCreate",required = true,dataType ="OrderitCreate")
    @PostMapping("imputedPrice")
    public  ApiResult ImputedPrice(@RequestBody OrderitCreate orderitCreate){
    ApiResult<Orderit> result=new ApiResult();
        try {
            Orderit orderit=orderitService.AggregatePrice(orderitCreate);
            result.setData(orderit);
            result.setMessage("订单生成成功");
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


    @ApiOperation(value = "单个商品购买",notes = "传入uid跟商品id",httpMethod = "POST")
    @ApiImplicitParam(name = "Single",required = true,dataType ="Single" )
    @PostMapping("SinglePurchase")
    public  ApiResult SinglePurchase(@RequestBody Single single){
        ApiResult<Map<String,Object>> result =new ApiResult();
        try {

            Map<String,Object> map=orderitService.SinglePurchase(single);
            result.setData(map);
            result.setMessage("订单生成成功");
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



    @ApiOperation(value = "异步通知",notes ="传入订单号与用户id",httpMethod = "POST")
    @ApiImplicitParam(name = "orderit",required = true,dataType = "orderit")
    @PostMapping("Notification")
    public  ApiResult Notification(@RequestBody Orderit orderit){
        ApiResult result=new ApiResult();
        try {

            orderitService.Notification(orderit);
            result.setMessage("支付成功");
        }catch (SuperMarketException e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty"+e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }

    @ApiOperation(value = "线下异步通知",notes = "传入uid号跟钱数",httpMethod = "GET")
    @ApiImplicitParam(name = "uid,price",required = true,dataType = "INT")
    @GetMapping("asynchronous")
    public  ApiResult Asynchronous(String openid,Float price){
        ApiResult result=new ApiResult();
        try {

            orderitService.Asynchronous(openid,price);
            result.setMessage("支付成功");
        }catch (SuperMarketException e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty"+e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }

    @ApiOperation(value = "取消订单",notes = "传入uid跟orderid进行取消订单",httpMethod = "POST")
    @ApiImplicitParam(name = "orderit",required = true,dataType = "Orderit")
    @PostMapping("cancelOrder")
    public  ApiResult cancelOrder(@RequestBody Orderit orderit){
        ApiResult result=new ApiResult();
        try {

            orderitService.cancelOrder(orderit);
            result.setMessage("取消订单成功");
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

    @ApiOperation(value = "拼团付款",notes = "拼图付款",httpMethod = "POST")
    @ApiImplicitParam(value = "group",required = true,dataType ="Group" )
    @PostMapping("paymentGroup")
    public ApiResult paymentGroup(@RequestBody Group group){
        ApiResult result=new ApiResult();
        try {
            orderitService.paymentGroup(group);
            result.setMessage("支付成功");
        }catch (SuperMarketException e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("出现异常请联系客服");
        }
        return result;
    }

    @ApiOperation(value = "确认收货",notes = "确认时间",httpMethod = "GET")
    @ApiImplicitParam(value = "orderid",required = true,dataType ="STRING" )
    @GetMapping("confirmReceipt")
    public ApiResult confirmReceipt(String orderid) throws SuperMarketException {
        ApiResult result=new ApiResult();
        try {
        orderitService.SelectconfirmReceipt(orderid);
        result.setMessage("收货成功");
        }catch (SuperMarketException e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("出现异常请联系客服");
        }
        return result;
    }

    @ApiOperation(value = "删除订单",notes = "删除订单",httpMethod = "GET")
    @ApiImplicitParam(value = "orderid",required = true,dataType ="STRING" )
    @GetMapping("deleteOrderit")
    public ApiResult Deleteorderit(String orderid,Integer uid) {
        ApiResult result=new ApiResult();
        try {
            orderitService.Deleteorderit(orderid,uid);
            result.setMessage("删除成功");
        }catch (SuperMarketException e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("出现异常请联系客服");
        }
        return result;
    }

}
