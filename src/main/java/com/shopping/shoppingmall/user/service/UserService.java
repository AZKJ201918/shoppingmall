package com.shopping.shoppingmall.user.service;



import com.shopping.shoppingmall.commodity.entity.Commodity;
import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.user.entity.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface UserService {
    List<Adress> selectAdress(Integer uid) throws SuperMarketException;

    void InsetrAddAdress(Adress adress) throws SuperMarketException;

    void ChangeTheAddress(Integer uid, Integer aid) throws  SuperMarketException;

    User selectCoupon(Integer uid) throws  SuperMarketException;

    void UpdateCouponsRegularly();

    void UpdateAddAdress(Adress adress) throws SuperMarketException;

    void DeleteAddAdress(Adress adress) throws  SuperMarketException;

    Adress SingleAdress(Adress adress) throws  SuperMarketException;

    List<Coupon> SelectPurchase() throws SuperMarketException;

    void DrawDown(UserCoupon userCoupon)throws SuperMarketException;

    void AddCollect(Collect collect)throws SuperMarketException;

    User LookCollect(Integer uid) throws SuperMarketException;

    void cancelCollect(Collect collect)throws SuperMarketException;

    void AddGroup(Integer uid, Integer cid) throws SuperMarketException, ParseException;


    List<Groups> SelectGroup(Integer uid) throws SuperMarketException;

    void JoinGroup(People people)throws  SuperMarketException;

    List<Groups> chenckGroup(Integer uid)throws SuperMarketException;

    void UpdateGroups();

//    UserElement Login( User user) throws SuperMarketException;
//
//    void res(User user) throws Exception;
//
//    List<Commodity> searchCommodity(Integer number, Integer sid, Integer uid) throws IOException;

     List<User> users();

    void insermessage(Integer uid, String name, String phone);

    void messages(String phone);
}
