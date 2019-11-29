package com.shopping.shoppingmall.user.service.Impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.shopping.shoppingmall.commodity.dao.AttributeMapper;
import com.shopping.shoppingmall.commodity.dao.CommodityMapper;
import com.shopping.shoppingmall.commodity.dao.CommodityPeopleAttributeMapper;
import com.shopping.shoppingmall.commodity.dao.PropertyMapper;
import com.shopping.shoppingmall.commodity.entity.Commodity;
import com.shopping.shoppingmall.commodity.entity.CommodityPeopleAttribute;
import com.shopping.shoppingmall.common.constants.Constants;
import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.common.utils.DateUtil;
import com.shopping.shoppingmall.common.utils.UserUtil;
import com.shopping.shoppingmall.user.dao.*;
import com.shopping.shoppingmall.user.entity.*;
import com.shopping.shoppingmall.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service("userServiceImpl")
@Slf4j
@EnableCaching
public class UserServiceImpl implements UserService {


    private static final Integer ADRESS_DEFAULT_STATUS = 1;

    private static final Integer ADRESS_THEDEFAULT_STATUS = 0;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdressMapper adressMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private UcouponRoleMapper ucouponRoleMapper;

    @Autowired
    private GroupsMapper groupsMapper;

    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private  WxuserMapper wxuserMapper;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private CommodityPeopleAttributeMapper commodityPeopleAttributeMapper;

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private AttributeMapper attributeMapper;

    @Autowired
    private  AssembleMapper assembleMapper;


    @Autowired
    private RelevanceMapper relevanceMapper;

    @Autowired
    private MessageMapper messageMapper;

//    @Autowired
//    private ZkClient zkClient;
//
//    @Autowired
//    private JestHttpClient esClient;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Cacheable(cacheNames = Constants.CACHE_PRODUCT_DETAIL,key = "#id")
    public List<Adress> selectAdress(Integer uid) throws SuperMarketException {
        userUtil.UserElent(uid);
        return adressMapper.selectAdress(uid) ;
    }


    public void InsetrAddAdress(Adress adress) throws SuperMarketException {
        userUtil.UserElent(Integer.parseInt(adress.getUid()));
       List<Adress> adressList=adressMapper.selectAdress(Integer.parseInt(adress.getUid()));
       if(adressList.size()==0){
           adress.setStatus(ADRESS_DEFAULT_STATUS);
       }

     adressMapper.insertSelective(adress);

    }


    public void ChangeTheAddress(Integer uid, Integer aid) throws SuperMarketException {
        userUtil.UserElent(uid);
        Adress adress=adressMapper.SelectChangeTheAddress(uid,aid);
        if(adress==null){
            throw  new SuperMarketException("没有找到此地址");
        }

        adressMapper.UpdateChangeTheAddress(uid);
        adress.setStatus(ADRESS_DEFAULT_STATUS);
        adressMapper.updateByPrimaryKeySelective(adress);


    }


    public User selectCoupon(Integer uid) throws SuperMarketException {
        userUtil.UserElent(uid);
        User user=userMapper.selectCoupon(uid);
        if(user==null){
            throw  new SuperMarketException("没有任何优惠卷");
        }
        return user;
    }


    public void UpdateCouponsRegularly() {
        List <Integer> integerList=couponMapper.CouponsRegularly();
        if(integerList.size()>0){
        couponMapper.UpdateCouponsRegularly();
        for (Integer integer :integerList){
            userCouponMapper.UserCouponsRegularly(integer);
        }
    }
    }

     public void UpdateAddAdress(Adress adress) throws SuperMarketException {
        userUtil.UserElent(Integer.parseInt(adress.getUid()));
        int i=adressMapper.updateByPrimaryKeySelective(adress);
        if (i<0){
            throw  new SuperMarketException("修改商品失败");
        }
    }


    public void DeleteAddAdress(Adress adress) throws SuperMarketException {
        userUtil.UserElent(Integer.parseInt(adress.getUid()));
        int i= adressMapper.DeleteAddAdress(Integer.valueOf(adress.getUid()),adress.getAid());
        if(i<0){
            throw  new SuperMarketException("删除商品失败");
        }
    }

    public Adress SingleAdress(Adress adress) throws SuperMarketException {
        userUtil.UserElent(Integer.parseInt(adress.getUid()));
        Adress adress1=adressMapper.SingleAdress(Integer.valueOf(adress.getUid()),adress.getAid());
        if (adress1==null){
            throw  new SuperMarketException("查询失败");
        }
        return adress1;
    }


    public List<Coupon> SelectPurchase() throws SuperMarketException {
        List<Coupon> couponList=couponMapper.SelectPurchase();
        if(couponList.size()==0){
            throw new SuperMarketException("没有抢购的优惠券");
        }
        return couponList;
    }

    public void DrawDown(UserCoupon userCoupon) throws SuperMarketException {
        userUtil.UserElent(userCoupon.getUid());
        Coupon coupon=couponMapper.selectByPrimaryKey(userCoupon.getCid());
        if(coupon.getNumber()-coupon.getDraw()>0){
            UserCoupon ucoupon=userCouponMapper.SelectByDrawDown(userCoupon.getCid(),userCoupon.getUid());
            if(ucoupon==null){
                UcouponRole ucouponRole=ucouponRoleMapper.selectByCoponRole(userCoupon.getUid(),userCoupon.getCid());
                if (ucouponRole==null) {
                    couponMapper.updateByCoupon(userCoupon.getCid());
                    userCouponMapper.insertSelective(userCoupon);
                    UcouponRole ucouponRole1=new UcouponRole();
                    ucouponRole1.setUid(userCoupon.getUid());
                    ucouponRole1.setCid(userCoupon.getCid());
                    ucouponRoleMapper.insertSelective(ucouponRole1);
                }else{
                    throw  new SuperMarketException("你领取过这个优惠卷了");
                }
            }else{
                throw new  SuperMarketException("你已经有这个这个优惠卷了");
            }
        }else{
            throw  new SuperMarketException("优惠卷以抢完");
        }
    }

    public void AddCollect(Collect collect) throws SuperMarketException {
        userUtil.UserElent(collect.getUid());
        Collect collects=collectMapper.selectByCollect(collect.getUid(),collect.getCid());
        if(collects==null){
            collectMapper.insertSelective(collect);
        }else{
            collectMapper.deleteByPrimaryKey(collects.getCoid());
        }
    }

    public User LookCollect(Integer uid) throws SuperMarketException {
        userUtil.UserElent(uid);
        User user=userMapper.LookCollect(uid);
        if(user==null){
            throw  new SuperMarketException("没有收藏");
        }

        return user;
    }


    public void cancelCollect(Collect collect) throws SuperMarketException {
        userUtil.UserElent(collect.getUid());
        if(collect.getUid()!=null){
            if(collect.getCid()!=null) {
                Collect collect1 = collectMapper.selectByCollect(collect.getUid(), collect.getCid());
                if (collect1 != null) {
                    int i = collectMapper.deleteByPrimaryKey(collect1.getCoid());
                    if (i > 0) {

                    } else {
                        throw new SuperMarketException("删除物品失败");
                    }
                } else {
                    throw new SuperMarketException("没有这个物品的收藏");
                }
            }else{
                throw  new SuperMarketException("没有参数");
            }
        }else{
            throw new SuperMarketException("没有参数");
        }
    }

    public void AddGroup(Integer uid, Integer cid) throws SuperMarketException, ParseException {
        userUtil.UserElent(uid);
        CommodityPeopleAttribute commodityPeopleAttribute=commodityPeopleAttributeMapper.selectByPrimaryKey(cid);
        Assemble res=assembleMapper.selectBoolen(commodityPeopleAttribute.getCid());
        if(res==null){
            throw new SuperMarketException("次商品不能拼团");
        }

        Commodity commodity=commodityMapper.selectByPrimaryKey(commodityPeopleAttribute.getCid());
        if(commodity.getStock()==0){
            throw  new SuperMarketException("库存为0无法拼团");
        }
        Groups groups=new Groups();
        groups.setCid(cid);
        groups.setStarttime(new Date());
        groups.setEndtime(DateUtil.plusDay2(2));
        groupsMapper.insertSelective(groups);
        People people=new People();
        people.setGid(groups.getGid());
        people.setUid(uid);
        people.setStatus(ADRESS_DEFAULT_STATUS);
        peopleMapper.insertSelective(people);

        Relevance relevance=new Relevance();
        relevance.setGid(groups.getGid());
        relevance.setAid(res.getId());
        relevanceMapper.insertSelective(relevance);


    }


    public List<Groups> SelectGroup(Integer uid) throws SuperMarketException {
        List<Groups> groupsList=groupsMapper.selectByGroup();
        groupsList.stream().forEach(
                para->{
                    CommodityPeopleAttribute commodityPeopleAttribute=commodityPeopleAttributeMapper.selectByPrimaryKey(para.getCid());
                    commodityPeopleAttribute.setProperty(propertyMapper.selectByPrimaryKey(commodityPeopleAttribute.getPid()));
                    commodityPeopleAttribute.setAttribute(attributeMapper.selectByPrimaryKey(commodityPeopleAttribute.getAid()));
                    commodityPeopleAttribute.setCommodity(commodityMapper.selectByPrimaryKey(commodityPeopleAttribute.getCid()));
                    para.setCommodityPeopleAttribute(commodityPeopleAttribute);
                    List<People> peopleList=peopleMapper.selectByGroup(para.getGid());
                    para.setCount(peopleList.size());
                    para.setMaxcount(relevanceMapper.SelectBygid(para.getGid()));
                    para.setDiscount(relevanceMapper.SelectBydisCount(para.getGid()));
                    peopleList.stream().forEach(
                           people -> {
                               Wxuser wxuser=wxuserMapper.SelectGroup(people.getUid());
                               people.setWxuser(wxuser);
                           }
                    );
                    para.setPeopleList(peopleList);
                }
        );

//        for (Groups groups:groupsList){
//            Commodity commodity=commodityMapper.selectByPrimaryKey(Long.valueOf(groups.getCid()));
//            groups.setCommodity(commodity);
//            List<People> peopleList=peopleMapper.selectByGroup(groups.getGid());
//            groups.setCount(peopleList.size());
//            for (People people:peopleList){
//                Wxuser wxuser=wxuserMapper.SelectGroup(people.getUid());
//                people.setWxuser(wxuser);
//            }
//            groups.setPeopleList(peopleList);
//        }
        return groupsList;
    }


    @Transactional(rollbackFor = Exception.class)
    public void JoinGroup(People people) throws SuperMarketException {
        userUtil.UserElent(people.getUid());
        int i=peopleMapper.selectCount(people.getGid());
        int j=relevanceMapper.SelectBygid(people.getGid());

        if(i==j){
            Groups groups=groupsMapper.selectByPrimaryKey(people.getGid());
            groups.setGtype(ADRESS_DEFAULT_STATUS);
            groupsMapper.updateByPrimaryKeySelective(groups);
        }
        if(i>j){
            throw  new SuperMarketException("拼团人数已经满");
        }
        People people1=peopleMapper.selectByBollen(people.getUid(),people.getGid());
        if(people1==null) {
            peopleMapper.insertSelective(people);
        }else{
            throw  new SuperMarketException("你已经在这个拼团里面了");
        }
    }


    public List<Groups> chenckGroup(Integer uid) throws SuperMarketException {
        userUtil.UserElent(uid);
        List<People> peopleList=peopleMapper.selectByPeople(uid);
        List<Groups> groupsList=new ArrayList<Groups>();
        if (CollectionUtils.isNotEmpty(peopleList)){
            for (People people:peopleList){
                Groups groups=groupsMapper.selectByPrimaryKey(people.getGid());
                CommodityPeopleAttribute commodityPeopleAttribute=commodityPeopleAttributeMapper.selectByPrimaryKey(groups.getCid());
                commodityPeopleAttribute.setProperty(propertyMapper.selectByPrimaryKey(commodityPeopleAttribute.getPid()));
                commodityPeopleAttribute.setAttribute(attributeMapper.selectByPrimaryKey(commodityPeopleAttribute.getAid()));
                commodityPeopleAttribute.setCommodity(commodityMapper.selectByPrimaryKey(commodityPeopleAttribute.getCid()));
                groups.setCommodityPeopleAttribute(commodityPeopleAttribute);
                groups.setPeople(peopleMapper.selectByBollen(uid,people.getGid()));
                List<People> peopleListt=peopleMapper.selectByGroup(groups.getGid());
                groups.setCount(peopleListt.size());
                groups.setMaxcount(relevanceMapper.SelectBygid(groups.getGid()));
                groups.setDiscount(relevanceMapper.SelectBydisCount(groups.getGid()));
                for (People people1:peopleListt){
                    Wxuser wxuser=wxuserMapper.SelectGroup(people1.getUid());
                    people1.setWxuser(wxuser);
                }
                groups.setPeopleList(peopleListt);
                groupsList.add(groups);
            }
            return  groupsList;
        }else{
            throw  new SuperMarketException("你没有任何拼图记录");
        }
    }

    public void UpdateGroups() {
        groupsMapper.UpdateGroups();
    }


    @Cacheable(cacheNames = Constants.CACHE_PRODUCT_DETAIL)
    public List<User> users(){
        return userMapper.selectUser();
    }

    @Override
    public void insermessage(Integer uid, String name, String phone) {
        User user=userMapper.selectByPrimaryKey(uid);
        user.setIphone(phone);
        userMapper.updateByPrimaryKeySelective(user);

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

    @Override
    public void messages(String phone) {
        User user=userMapper.selectByPhone(phone);
        Message message=new Message();
        message.setPhone(phone);
        if(user!=null){
         UserCoupon userCoupon=new UserCoupon();
         userCoupon.setCid(1000004);
         userCoupon.setUid(user.getUid());
         userCouponMapper.insertSelective(userCoupon);
         message.setStatus(2l);
        }
        messageMapper.insertSelective(message);

    }
//
//    @Override
//    public UserElement Login(User user) throws SuperMarketException {
//        UserElement ue=null;
//        User existUser=userMapper.selectByPrimaryKey(user.getUid());
//        if(existUser==null){
//            throw  new SuperMarketException("用户不存在");
//        }else{
//
//            Boolean result=bCryptPasswordEncoder.matches(user.getIphone(),existUser.getIphone());
//            if(!result){
//                throw  new SuperMarketException("密码错误");
//            }else{
//                ue=new UserElement();
//                ue.setUserId(String.valueOf(user.getUid()));
//                ue.setToken(UserUtil.generateToken(user.getUid()));
//
//            }
//        }
//        return ue ;
//    }

//    @Override
//    public void res( User user) throws Exception {
//        InterProcessMutex lock=null;
//        try {
//            lock = new InterProcessMutex(zkClient.getZkClient(), Constants.USER_REGISTER_DISTRIBUTE_LOCK_PATH);
//            boolean retry = true;
//            do{
//                if (lock.acquire(3000, TimeUnit.MILLISECONDS)){
//                    //查询重复用户
//                    User repeatedUser = userMapper.selectByPrimaryKey(user.getUid());
//                    if(repeatedUser!=null){
//                        throw  new SuperMarketException("用户邮箱重复");
//                    }
//                    //检查两次密码是否一致
//                    user.setIphone(bCryptPasswordEncoder.encode(user.getIphone()));
//                    user.setName("码码购用户"+user.getName());
//                    userMapper.insertSelective(user);
//                }
//                retry = false;
//            }while (retry);
//
//        } catch (Exception e) {
//            log.error("用户注册异常",e);
//            throw  e;
//        } finally {
//            if(null != lock){
//                try {
//                    lock.release();
//                    log.info(user.getIphone()+Thread.currentThread().getName()+"释放锁");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }

//    @Override
//    public List<Commodity> searchCommodity(Integer number, Integer sid, Integer uid) throws IOException {
//
//        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.boolQuery()
//                .must(QueryBuilders.matchQuery("spu_name",uid))
//                .must(QueryBuilders.matchQuery("status",1)))
//                .from(15)
//                .size(3);
//        Search search=new Search.Builder(searchSourceBuilder.toString()).build();
//        SearchResult result=esClient.execute(search);
//        String json=result.getJsonString();
//        JSONArray jsonArray= JSON.parseObject(json).getJSONObject("hits").getJSONArray("hist");
//        List<Commodity> commodities=new ArrayList<>();
//        for(int i=0;i<jsonArray.size();i++){
//
//        }
//
//        return commodities;
//    }
}
