package com.shopping.shoppingmall.user.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shopping.shoppingmall.common.cache.CommonCacheUtil;
import com.shopping.shoppingmall.common.constants.Constants;
import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.common.utils.UserUtil;
import com.shopping.shoppingmall.common.utils.getOpenIdutil;
import com.shopping.shoppingmall.user.dao.MessageMapper;
import com.shopping.shoppingmall.user.dao.UserCouponMapper;
import com.shopping.shoppingmall.user.dao.UserMapper;
import com.shopping.shoppingmall.user.dao.WxuserMapper;
import com.shopping.shoppingmall.user.entity.*;
import com.shopping.shoppingmall.user.service.WxuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service("wxuserServiceImpl")
public class WxuserServiceImpl implements WxuserService {

    @Autowired
    private WxuserMapper wxuserMapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private CommonCacheUtil cacheUtil;

    private static AtomicInteger counter = new AtomicInteger(0);

    private static AtomicInteger count = new AtomicInteger(0);


    public User wxLogin(String code, String name, String img) throws SuperMarketException {

        if(code!=null){
            getOpenIdutil getopenid = new getOpenIdutil();
            String jsonId = getopenid.getopenid(Constants.WEXING_STATUS_APPID,code,Constants.WEIXING_STATUS_APPSECRET);

            JSONObject jsonObject= JSON.parseObject(jsonId);
            Wxuser wxuser= wxuserMapper.wxLogin(jsonObject.getString("openid"));
            if (wxuser==null){
                Wxuser wxuser1=new Wxuser();
                wxuser1.setOpenid(jsonObject.getString("openid"));
                wxuser1.setNiname(name);
                wxuserMapper.insertSelective(wxuser1);
                User user=new User();
                user.setOpenid(jsonObject.getString("openid"));
                user.setName(img);
                user.setCreateTime(new Date());
                userMapper.insertSelective(user);
//                return  userMapper.selectOpenid(jsonObject.getString("openid"));

            }
            User user=userMapper.selectOpenid(jsonObject.getString("openid"));
            UserElement userElement=new UserElement();
            userElement.setUserId(String.valueOf(user.getUid()));
            userElement.setToken(UserUtil.generateToken(user.getUid()));
            cacheUtil.putTokenWhenLogin(userElement);
            counter.incrementAndGet();
            count.incrementAndGet();
            if(user.getIphone()!=null){
                List<Message> messageList=messageMapper.SelectPhone(user.getIphone());
                messageList.stream().forEach(
                        pram->{
                            UserCoupon userCoupon=new UserCoupon();
                            userCoupon.setUid(user.getUid());
                            userCoupon.setCid(1000004);
                            userCouponMapper.insertSelective(userCoupon);
                            pram.setStatus(2l);
                            messageMapper.updateByPrimaryKeySelective(pram);

                        }
                );
            }
            return user;

        }else{
            throw new SuperMarketException("没有coder");
        }
    }

    public Wxuser LookIntegral(Integer uid) {
        return wxuserMapper.SelectGroup(uid);
    }

    @Override
    public Map<String, Integer> visitorVolume() throws SuperMarketException {
        Map<String,Integer> map=new HashMap<String, Integer>();
        map.put("TotalVisits",counter.get());
        map.put("AlwaysVisits",count.get());
        return map;
    }

    @Override
    public void AlwaysVisitToday() {
        count.set(0);
    }


}
