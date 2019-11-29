package com.shopping.shoppingmall.common.utils;



import com.shopping.shoppingmall.common.cache.CommonCacheUtil;
import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.user.dao.UserMapper;
import com.shopping.shoppingmall.user.entity.User;
import com.shopping.shoppingmall.user.entity.UserElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;


@Component
public  class  UserUtil {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommonCacheUtil cacheUtil;


   public  void UserElent(Integer uid) throws SuperMarketException {
       if(uid==0){
           throw  new SuperMarketException("请登录");
       }
       if(uid==null){
           throw  new SuperMarketException("请登录");
       }
       User user=userMapper.selectByPrimaryKey(uid);
       if(user==null) {
           throw new SuperMarketException("请登录");
       }
       UserElement userElement=cacheUtil.getUserByToken(generateToken(uid));
       if(userElement==null){
           throw  new SuperMarketException("请登录");
       }

   }



    public static String generateToken(Integer uid) {
        String source= String.valueOf(uid);
        return MD5Util.getMD5(source);
    }
}
