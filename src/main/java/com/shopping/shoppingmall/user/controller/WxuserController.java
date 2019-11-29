package com.shopping.shoppingmall.user.controller;


import com.shopping.shoppingmall.common.constants.Constants;
import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.common.jms.SmaProcessor;
import com.shopping.shoppingmall.common.resp.ApiResult;
import com.shopping.shoppingmall.user.entity.User;
import com.shopping.shoppingmall.user.entity.Wxuser;
import com.shopping.shoppingmall.user.service.WxuserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@Slf4j
public class WxuserController {


    private static final String SMS_QUEUE ="sms_queue" ;

    @Autowired
    @Qualifier("wxuserServiceImpl")
    private WxuserService wxuserService;

    @Autowired
    private SmaProcessor processor;

    //登陆
    @ApiOperation(value = "登陆",notes = "根据微信前端传openid进行登陆",httpMethod = "GET")
    @ApiImplicitParam(name = "openid",required = true,dataType = "STRING")
    @GetMapping("/login")
    public ApiResult Wxlogin(String code, String name, String  img){
        ApiResult<User> result=new ApiResult<User>();
        try {
            User user = wxuserService.wxLogin(code,name,img);
            result.setData(user);
            result.setMessage("登录成功");
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
    @ApiOperation(value = "查看积分",notes ="查看积分",httpMethod = "GET")
    @ApiImplicitParam(name = "uid",required = true,dataType = "INT")
    @GetMapping("LookIntegral")
    public  ApiResult LookIntegral(Integer uid){
        ApiResult<Wxuser> result=new ApiResult<Wxuser>();
        try {
            Wxuser wxuser = wxuserService.LookIntegral(uid);
            result.setData(wxuser);
            result.setMessage("登录成功");
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



//    @ApiOperation(value = "异步发送请求",notes = "异步发送请求",httpMethod = "GET")
//    @ApiImplicitParam(name = "null",required = false,dataType = "null")
//    @GetMapping("Queen")
//    public void Queen(){
//        Destination destination=new ActiveMQQueue(SMS_QUEUE);
//        Map<String,String> map= new HashMap();
//        map.put("chengxiang","陈翔");
//        map.put("yeshun","叶顺");
//        map.put("shenqingyuan","沈清源");
//        map.put("yanxuejun","严学军");
//        map.put("jinqifan","金其帆");
//        String message= JSON.toJSONString(map);
//        processor.sendSmsToQueue(destination,message);
//    }

    @ApiOperation(value = "访问量",notes = "访问量",httpMethod = "GET")
    @ApiImplicitParam(name = "null",required = false,dataType = "null")
    @GetMapping("visitorVolume")
    public ApiResult VisitorVolume(){
        ApiResult<Map<String,Integer>> result=new ApiResult<Map<String, Integer>>();
        try {
            Map<String,Integer> map=wxuserService.visitorVolume();
            result.setData(map);
            result.setMessage("登录成功");
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
}
