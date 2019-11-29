package com.shopping.shoppingmall.user.controller;



import com.shopping.shoppingmall.common.constants.Constants;
import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.common.resp.ApiResult;
import com.shopping.shoppingmall.common.utils.DateUtil;
import com.shopping.shoppingmall.user.entity.*;
import com.shopping.shoppingmall.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Api( value = "用户模块")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    //收货地址
    @ApiOperation(value = "查看自己的收货地址", notes = "根据用户uid自己的收货地址", httpMethod = "GET")
    @ApiImplicitParam(name = "uid", required = true, dataType = "Integer")
    @GetMapping("/adress")
    public ApiResult selectAdress(Integer uid) {
        ApiResult<List<Adress>> result = new ApiResult<List<Adress>>();
        try {
            List<Adress> adressList = userService.selectAdress(uid);
            result.setData(adressList);
            result.setMessage("收货查询成功");
        } catch (SuperMarketException e) {
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }

    //添加收货地址
    @ApiOperation(value = "添加收货地址", notes = "添加收货地址", httpMethod = "POST")
    @ApiImplicitParam(name = "adress", required = true, dataType = "Adress")
    @PostMapping("addAdress")
    public ApiResult InsetrAddAdress(@RequestBody Adress adress) {
        ApiResult result = new ApiResult();
        try {
            userService.InsetrAddAdress(adress);
            result.setMessage("添加成功");
        } catch (SuperMarketException e) {
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }

    //更改默认地址
    @ApiOperation(value = "更改默认地址", notes = "根据用户uid跟地址id更爱默认地址", httpMethod = "POST")
    @ApiImplicitParam(name = "uid", required = true, dataType = "Integer")
    @PostMapping("changeAddress")
    public ApiResult ChangeTheAddress(@RequestBody Adress adress) {
        ApiResult result = new ApiResult();
        try {
            userService.ChangeTheAddress(Integer.valueOf(adress.getUid()), adress.getAid());
        } catch (SuperMarketException e) {
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }


    //修改收货地址
    @ApiOperation(value = "修改收货地址", notes = "修改收货地址不能少地址uid", httpMethod = "POST")
    @ApiImplicitParam(name = "adress", required = true, dataType = "Adress")
    @PostMapping("updateAdress")
    public ApiResult updateAddAdress(@RequestBody Adress adress) {
        ApiResult result = new ApiResult();
        try {
            userService.UpdateAddAdress(adress);
            result.setMessage("修改成功");
        } catch (SuperMarketException e) {
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }


    //删除
    @ApiOperation(value = "删除地址", notes = "根据用户uid跟地址id进行删除地址", httpMethod = "POST")
    @ApiImplicitParam(name = "adress", required = true, dataType = "Adress")
    @PostMapping("deleteAdress")
    public ApiResult deleteteAddAdress(@RequestBody Adress adress) {
        ApiResult result = new ApiResult();
        try {
            userService.DeleteAddAdress(adress);
            result.setMessage("修改成功");
        } catch (SuperMarketException e) {
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }


    //查询单个地址
    @ApiOperation(value = "查看单个地址", notes = "根据用户uid跟地址id 查看地址", httpMethod = "POST")
    @ApiImplicitParam(name = "adress", required = true, dataType = "Adress")
    @PostMapping("singleAdress")
    public ApiResult singleAdress(@RequestBody Adress adress) {
        ApiResult<Adress> result = new ApiResult<Adress>();
        try {
            Adress adressList = userService.SingleAdress(adress);
            result.setData(adressList);
            result.setMessage("查询成功");
        } catch (SuperMarketException e) {
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }


    //查看自己的优惠卷
    @ApiOperation(value = "查看自己的优惠卷", notes = "根据用户uid查看自己的优惠卷", httpMethod = "GET")
    @ApiImplicitParam(name = "uid", required = true, dataType = "Integer")
    @GetMapping("/coupon")
    public ApiResult selectCoupon(Integer uid) {
        ApiResult<User> result = new ApiResult<User>();
        try {

            User user = userService.selectCoupon(uid);
            result.setData(user);
            result.setMessage("优惠卷查询成功");
        } catch (SuperMarketException e) {
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }


    //查看正在抢购中的优惠卷
    @ApiOperation(value = "查看抢购中的优惠卷", notes = "查看抢购中的优惠卷", httpMethod = "GET")
    @ApiImplicitParam(required = false)
    @GetMapping("purchase")
    public ApiResult Purchase() {
        ApiResult<List<Coupon>> result = new ApiResult<List<Coupon>>();
        try {
            List<Coupon> couponList = userService.SelectPurchase();
            result.setData(couponList);
            result.setMessage("查询成功");
        } catch (SuperMarketException e) {
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("SQL statement error or that place is empty" + e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }

    @ApiOperation(value = "领取优惠卷",notes = "领取优惠卷",httpMethod = "POST")
    @ApiImplicitParam(name = "userCoupon",required = true,dataType = "UserCoupon")
    @PostMapping ("drawdown")
    public ApiResult DrawDown(@RequestBody UserCoupon userCoupon){
        ApiResult result=new ApiResult();
        try {
            userService.DrawDown(userCoupon);
            result.setMessage("领取成功");
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

    @ApiOperation(value = "添加收藏",notes = "添加收藏",httpMethod = "POST")
    @ApiImplicitParam(name = "collect",required = true,dataType = "Collect")
    @PostMapping("addcollect")
    public ApiResult AddCollect(@RequestBody Collect collect){
        ApiResult result=new ApiResult();
        try {
            userService.AddCollect(collect);
            result.setMessage("添加收藏成功");
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

    @ApiOperation(value = "查看收藏",notes = "查看收藏",httpMethod = "GET")
    @ApiImplicitParam(name = "uid",required =true,dataType = "INT")
    @GetMapping("lookCollect")
    public ApiResult LookCollect(Integer uid){
        ApiResult<User> result=new ApiResult();
        try {
            User user=userService.LookCollect(uid);
            result.setData(user);
            result.setMessage("查看收藏成功");
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

    @ApiOperation(value = "取消收藏",notes ="查看收藏",httpMethod = "POST")
    @ApiImplicitParam(name = "uid cid ",required = true,dataType ="INT")
    @PostMapping("cancelCollect")
    public ApiResult cancelCollect(@RequestBody Collect collect){
        ApiResult result=new ApiResult();
        try {
            userService.cancelCollect(collect);
            result.setMessage("取消收藏成功");
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


    @GetMapping("/md5")
    public ApiResult MD5(double price) {

        ApiResult<Map<String,Object>> result=new ApiResult<Map<String, Object>>();
        //生成订单

        Date date = new Date();
        String terminal_trace = DateUtil.getOrderIdByTime();


        //处理时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String sdfdate = simpleDateFormat.format(date);
        int ff;
        ff= (int) (price*100);
        //md5处理的
        String string = "pay_ver=100&pay_type=010&service_id=015&merchant_no=852100266000038&terminal_id=11185908&terminal_trace=" + terminal_trace + "&terminal_time=" + sdfdate + "&total_fee=" + ff +"&access_token="+Constants.WEIXING_ACCESS_TOKEN;

        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }


        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        Map<String,Object>mdMap=new HashMap<String,Object>();
        mdMap.put("key_sign", hex.toString());
        mdMap.put("terminal_trace", terminal_trace);
        mdMap.put("terminal_time", sdfdate);
        mdMap.put("total_fee",ff);
//        mdMap.put("openid",openid);
        result.setData(mdMap);
        return result;

    }


    @ApiOperation(value = "创建拼团",notes = "创建拼图",httpMethod = "GET")
    @ApiImplicitParam (name = "uid,cid",required = true,dataType = "int")
    @GetMapping("addGroup")
    public ApiResult AddGroup(Integer uid,Integer cid){
        ApiResult result =new ApiResult();
        try {
            userService.AddGroup(uid,cid);
            result.setMessage("创建成功");
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

    @ApiOperation(value ="查看所有拼团",notes = "查看所有拼团",httpMethod = "GET")
    @ApiImplicitParam(name = "uid",required = true,dataType = "int")
    @GetMapping("selectGroup")
    public ApiResult SelectGroup(Integer uid){
        ApiResult<List<Groups>> result=new ApiResult();
        try {
            List<Groups> groupsList=userService.SelectGroup(uid);
            result.setData(groupsList);
            result.setMessage("查看成功");
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

    @ApiOperation(value = "加入拼团",notes = "加入拼团",httpMethod = "POST")
    @ApiImplicitParam(name = "gid，uid",required = true,dataType = "INT")
    @PostMapping("joinGroup")
    public  ApiResult joinGroup(@RequestBody People people){
        ApiResult result=new ApiResult();
        try {
            userService.JoinGroup(people);
            result.setMessage("加入成功");
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

    @ApiOperation(value = "查看自己的拼团",notes ="查看自己的拼图",httpMethod = "GET")
    @ApiImplicitParam (name = "uid",required = true,dataType = "INT")
    @GetMapping("checkGroup")
    public ApiResult checkGroup (Integer uid){
        ApiResult<List<Groups>> result=new ApiResult();
        try {
            List<Groups> groupsList=userService.chenckGroup(uid);
            result.setData(groupsList);
            result.setMessage("查看成功");
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


//
//    @PostMapping("uploadHeadImg")
//    public String uploadHeadImg(HttpServletRequest req, @RequestParam(required=false )MultipartFile file) throws Exception {
//        String imgUrlName = QiniuFileUploadUtil.uploadHeadImg(file);
//        return Constants.QINIU_HEAD_IMG_BUCKET_URL+"/"+imgUrlName;
//    }

    @GetMapping("insermessage")
    public ApiResult insermessage(Integer uid,String name,String phone){
        ApiResult result=new ApiResult();
        userService.insermessage(uid,name,phone);
        return result;

    }


    @GetMapping("messages")
    public String messages(String phone){
        System.out.println(phone);
        userService.messages(phone);
        return "ok";
    }
//    @RequestMapping("login")
//    public  ApiResult login(@RequestBody @Valid User user, HttpSession session) throws SuperMarketException {
//    ApiResult result=new ApiResult();
//    UserElement userElement=userService.Login(user);
//    if(userElement!=null){
//     if(session.getAttribute(Constants.REQUEST_USER_SESSION)==null){
//         session.setAttribute(Constants.REQUEST_USER_SESSION,userElement);
//
//     }
//     result.setData(userElement);
//
//
//    }
//    return result;
//    }
//    @RequestMapping("/res")
//    public ApiResult res(@RequestBody @Valid User user) throws Exception {
//        ApiResult result=new ApiResult();
//
//
//        userService.res(user);
//        result.setMessage("注册成功");
//        return result;
//    }
//
//    @RequestMapping("/search")
//    public ApiResult<List<Commodity>> searchCommodity(@RequestBody Shopcart shopcart) throws IOException {
//        ApiResult<List<Commodity>>  result=new ApiResult<>();
//
//        List<Commodity> commodityList=userService.searchCommodity(shopcart.getNumber(), shopcart.getSid(),shopcart.getUid());
//        result.setData(commodityList);
//        return result;
//
//    }
}


