package com.shopping.shoppingmall.orderit.service.Impl;



import com.shopping.shoppingmall.commodity.dao.CommodityMapper;
import com.shopping.shoppingmall.commodity.dao.CommodityPeopleAttributeMapper;
import com.shopping.shoppingmall.commodity.dao.ShoppingspreeMapper;
import com.shopping.shoppingmall.commodity.entity.Commodity;
import com.shopping.shoppingmall.commodity.entity.CommodityPeopleAttribute;
import com.shopping.shoppingmall.commodity.entity.Shoppingspree;
import com.shopping.shoppingmall.common.exception.SuperMarketException;
import com.shopping.shoppingmall.common.utils.DateUtil;
import com.shopping.shoppingmall.common.utils.UserUtil;
import com.shopping.shoppingmall.orderit.dao.ExpressMapper;
import com.shopping.shoppingmall.orderit.dao.OrderitCommodityMapper;
import com.shopping.shoppingmall.orderit.dao.OrderitMapper;
import com.shopping.shoppingmall.orderit.entity.*;
import com.shopping.shoppingmall.orderit.service.OrderitService;
import com.shopping.shoppingmall.shopcart.dao.ShopcartMapper;
import com.shopping.shoppingmall.shopcart.entity.Shopcart;
import com.shopping.shoppingmall.user.dao.*;
import com.shopping.shoppingmall.user.entity.*;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("orderitServiceImpl")
@EnableTransactionManagement
public class OrderitServiceImpl implements OrderitService {

    //未付款
    private static final Integer NON_PAYMET_PRICE = 10;
    //已取消
    private static final Integer CANCEL_PAYMET_PRICE = 0;
    //成功
    private static final Integer ALEARDY_PAYMET_PRICE = 20;

    @Autowired
    private OrderitMapper orderitMapper;


    @Autowired
    private OrderitCommodityMapper orderitCommodityMapper;

    @Autowired
    private ShopcartMapper shopcartMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private WxuserMapper wxuserMapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private  GroupsMapper groupsMapper;

    @Autowired
    private CommodityPeopleAttributeMapper commodityPeopleAttributeMapper;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private ShoppingspreeMapper shoppingspreeMapper;

    @Autowired
    private RelevanceMapper relevanceMapper;

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private ExpressMapper expressMapper;


    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");




    public Orderit SelectOrderDetails(Integer uid, Integer id) throws SuperMarketException {
        userUtil.UserElent(uid);
        Orderit orderit=orderitMapper.SelectOrderDetails(uid,id);
        if(orderit==null){
            throw  new SuperMarketException("没有此订单");
        }
        List<OrderitCommodity> orderitCommodityList=orderitCommodityMapper.SelectCommodityList(orderit.getId());
        Express express=expressMapper.selectByExpress(orderit.getOrderid());
        if(express!=null){
            orderit.setExpress(express);
        }
        orderitCommodityList.stream().forEach(
                para->{
                    Shoppingspree shoppingspree=shoppingspreeMapper.selectByPrimaryKey(para.getSid());
                    if(shoppingspree!=null){
                        para.setShoppingspree(shoppingspree);
                    }
                }
        );
        if(orderitCommodityList.size()==0){
            throw new SuperMarketException("此订单没有商品");
        }
        orderit.setOrderitCommodityList(orderitCommodityList);
        return orderit;
    }


    public List<Orderit> SelectallOrderDetails(Integer uid) throws SuperMarketException {
        userUtil.UserElent(uid);
     List<Orderit> orderitList=orderitMapper.SelectallOrderDetails(uid);
        if (orderitList.size() == 0) {
          throw  new SuperMarketException("查找失败");
        }
        return  orderitList;
        }


    @Transactional(rollbackFor=SuperMarketException.class )
    public Orderit AggregatePrice(OrderitCreate orderitCreate) throws SuperMarketException, ParseException {
        userUtil.UserElent(orderitCreate.getUid());
        Orderit orderit = new Orderit();
        Coupon coupon = null;
        orderit.setOrderid(DateUtil.getOrderIdByTime());
        orderit.setUid(orderitCreate.getUid());
        orderit.setAdressid(orderitCreate.getAdressid());
        orderit.setRemark(orderitCreate.getRemark());
        orderit.setCreatetime(new Date());
        orderit.setClosetime(DateUtil.plusDay2(7));
        orderit.setStatus(NON_PAYMET_PRICE);
        if (orderitCreate.getCouponid() != null && orderitCreate.getCouponid() != 0) {
            orderit.setCouponid(orderitCreate.getCouponid());
            coupon = couponMapper.selectByPrimaryKey(orderitCreate.getCouponid());
            userCouponMapper.updateByAggregatePrice(orderitCreate.getUid(), coupon.getCid());
        }
        orderitMapper.insertSelective(orderit);

        List<Shopcart> shopcartList = shopcartMapper.SelectByShopUid(orderit.getUid());
        double price = 0;
        double allprice = 0;
        Map<Integer, Integer> map = orderitCreate.getMapOrderid();

//        map.keySet().stream().forEach(
//                param->{
//
//                    OrderitCommodity orderitCommodity = new OrderitCommodity();
//                    orderitCommodity.setOid(orderit.getId());
//                    orderitCommodity.setCid(key);
//                    orderitCommodity.setNumber(map.get(key));
//                    int i = commodityMapper.selectBycommodtiyPrice(key);
//                    price = price + (i * map.get(key));
//                    allprice = allprice + (i * map.get(key));
//
//                    for (Shopcart shopcart : shopcartList) {
//
//                        if (shopcart.getCid().hashCode() == key) {
//                            if (shopcart.getNumber() == map.get(key)) {
//                                shopcartMapper.deleteByPrimaryKey(shopcart.getSid());
//                            } else if (shopcart.getNumber() < map.get(key)) {
//                                throw new SuperMarketException("错误");
//                            } else {
//                                shopcartMapper.updateCartPrice(shopcart.getSid(), map.get(key));
//                            }
//                        }
//                    }
//                    orderitCommodityMapper.insertSelective(orderitCommodity);
//                }
//        );


        for (Integer key : map.keySet()) {
            OrderitCommodity orderitCommodity = new OrderitCommodity();
            orderitCommodity.setOid(orderit.getId());
            orderitCommodity.setCid(key);
            orderitCommodity.setNumber(map.get(key));
            CommodityPeopleAttribute commodityPeopleAttribute=commodityPeopleAttributeMapper.selectByPrimaryKey(key);
            Shoppingspree shoppingspree=shoppingspreeMapper.SelectShoppingspree(commodityPeopleAttribute.getCid());
            if(shoppingspree!=null){
                orderitCommodity.setSid(shoppingspree.getSid());
                price = price + ((commodityPeopleAttribute.getPrice()-shoppingspree.getSubtract()) * map.get(key));
            }else {
                price = price + (commodityPeopleAttribute.getPrice() * map.get(key));
            }
            allprice = allprice + (commodityPeopleAttribute.getPrice() * map.get(key));

            for (Shopcart shopcart : shopcartList) {

                if (shopcart.getCid().hashCode() == key) {
                    if (shopcart.getNumber() == map.get(key)) {
                        shopcartMapper.deleteByPrimaryKey(shopcart.getSid());
                    } else if (shopcart.getNumber() < map.get(key)) {
                        throw new SuperMarketException("错误");
                    } else {
                        shopcartMapper.updateCartPrice(shopcart.getSid(), map.get(key));
                    }
                }
            }
            orderitCommodityMapper.insertSelective(orderitCommodity);
        }
        if (orderitCreate.getPostage() != null) {
            orderit.setPostage(orderitCreate.getPostage());
            price = price + orderitCreate.getPostage();
            allprice = allprice + orderitCreate.getPostage();
        }
        orderit.setAllprice((int) allprice);
        if(coupon!=null){
        if (coupon.getCtype() == 1) {
            if (price >= coupon.getHowmany()) {
                price = price - coupon.getFullMoney();
            } else {
                throw new SuperMarketException("优惠卷额度过高无法使用优惠卷");
            }
        } else if (coupon.getCtype() == 2) {
            if (price >= coupon.getThresholdMoney()) {
                price = price - coupon.getThresholdMoney();
            } else {
                throw new SuperMarketException("无法使用");
            }
        } else if (coupon.getCtype() == 3) {
            Float sale = Float.valueOf(coupon.getSale()) / 10;
            price = price * sale;
        }
    }
        orderit.setZzprice((float) price);
        orderitMapper.updateByPrimaryKeySelective(orderit);
        return orderitMapper.selectByPrimaryKey(orderit.getId());
    }


    public void SelectOrderitTask() {
        List<Orderit> orderitList=orderitMapper.selectQuartzTask();
        if(orderitList.size()>0){
            for (Orderit orderit:orderitList ) {
                orderit.setStatus(CANCEL_PAYMET_PRICE);
                List<OrderitCommodity> orderitCommodityList = orderitCommodityMapper.selectQuartzTask(orderit.getId());
                if (orderitCommodityList.size() > 0) {
                    for (OrderitCommodity orderitCommodity : orderitCommodityList) {
                        if(orderit.getCoupon()!=null){
                            UserCoupon userCoupon=new UserCoupon();
                            userCoupon.setUid(orderit.getUid());
                            userCoupon.setCid(orderit.getCouponid());
                            userCouponMapper.insertSelective(userCoupon);
                        }
                        CommodityPeopleAttribute commodityPeopleAttribute=commodityPeopleAttributeMapper.selectBycommodtiys(orderitCommodity.getCid());
                        commodityMapper.updatQuartzTask(Math.toIntExact(commodityPeopleAttribute.getCid()), orderitCommodity.getNumber());
                    }
                    orderitMapper.updateByPrimaryKeySelective(orderit);
                }
            }
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> SinglePurchase(Single single) throws SuperMarketException, ParseException {
        userUtil.UserElent(single.getUid());
        Orderit orderit=new Orderit();
        Coupon coupon=new Coupon();
        Map<String,Object> map=new HashMap<String,Object>();
        orderit.setOrderid(DateUtil.getOrderIdByTime());
        orderit.setUid(single.getUid());
        orderit.setAdressid(single.getAdressid());
        orderit.setRemark(single.getRemark());
        orderit.setCreatetime(new Date());
        orderit.setClosetime(DateUtil.plusDay2(7));
        orderit.setStatus(NON_PAYMET_PRICE);
        CommodityPeopleAttribute commodityPeopleAttribute=commodityPeopleAttributeMapper.selectByPrimaryKey(single.getCid());
        Commodity commodity=commodityMapper.selectByPrimaryKey(Long.valueOf(commodityPeopleAttribute.getCid()));
        if(commodity!=null){
            if (commodity.getStock()>=1) {
                if (single.getCouponid() != 0) {
                    if (single.getCouponid() != null) {
                        orderit.setCouponid(single.getCouponid());
                        coupon = couponMapper.selectByPrimaryKey(single.getCouponid());
                        userCouponMapper.updateByAggregatePrice(single.getUid(), coupon.getCid());
                    }
                }
                commodityMapper.upShopCart(Long.valueOf(single.getCid()));
                orderitMapper.insertSelective(orderit);
//                Integer i = commodityMapper.selectBycommodtiyPrice(single.getCid());
                Integer i = commodityPeopleAttributeMapper.selectBycommodtiyPrice(single.getCid());
                OrderitCommodity orderitCommodity = new OrderitCommodity();
                orderitCommodity.setOid(orderit.getId());
                orderitCommodity.setCid(single.getCid());
                orderitCommodity.setNumber(1);
                float price = i;
                Integer allprice = i;
                Shoppingspree shoppingspree=shoppingspreeMapper.SelectShoppingspree(commodity.getId());
                if(shoppingspree!=null){
                    price=price-shoppingspree.getSubtract();
                    orderitCommodity.setSid(shoppingspree.getSid());
                }
                orderitCommodityMapper.insertSelective(orderitCommodity);
                if (single.getPostage() != null) {
                    orderit.setPostage(single.getPostage());
                    price = price + single.getPostage();
                    allprice = allprice + single.getPostage();
                }
                orderit.setAllprice(allprice);

                if(single.getCouponid()!=0){
                if (single.getCouponid() != null) {
                    if (coupon.getCtype() == 1) {
                        if (price >= coupon.getHowmany()) {
                            price = price - coupon.getFullMoney();
                        } else {
                            throw new SuperMarketException("优惠卷额度过高无法使用优惠卷");
                        }
                    } else if (coupon.getCtype() == 2) {
                        if (price >= coupon.getThresholdMoney()) {
                            price = price - coupon.getThresholdMoney();
                        } else {
                            throw new SuperMarketException("无法使用");
                        }
                    } else if (coupon.getCtype() == 3) {
                        Float sale = Float.valueOf(coupon.getSale()) / 10;
                        price = price * sale;
                    }
                }
            }
                orderit.setZzprice(price);

                map.put("total_fee",orderit.getZzprice());
                map.put("orderid",orderit.getOrderid());
                orderitMapper.updateByPrimaryKeySelective(orderit);
                return  map;

            }else{
                throw  new SuperMarketException("没有库存");
            }
        }else{
            throw  new SuperMarketException("没有次商品");
        }
    }

    public void Notification(Orderit orderit) throws SuperMarketException {
        Orderit orderit1=orderitMapper.SelectNotification(orderit.getOrderid());
        if (orderit1==null){
            throw  new SuperMarketException("订单不存在");
        }
        orderit1.setStatus(ALEARDY_PAYMET_PRICE);
        orderit1.setEndTime(new Date());
        orderitMapper.updateByPrimaryKeySelective(orderit1);
        Wxuser wxuser= wxuserMapper.SelectGroup(orderit1.getUid());
        wxuserMapper.instNotification(wxuser.getId(),orderit1.getZzprice());

    }

    public void Asynchronous(String openid, Float price) throws SuperMarketException {
        User user=userMapper.selectOpenid(openid);
        Wxuser wxuser= wxuserMapper.SelectGroup(user.getUid());
        Orderit orderit=new Orderit();
        orderit.setStatus(30);
        orderit.setOrderid(DateUtil.getOrderIdByTime());
        orderit.setUid(user.getUid());
        orderit.setZzprice(price);
        orderit.setCreatetime(new Date());
        orderit.setEndTime(new Date());
        if(price<1){
            price=1f;
        }
        orderitMapper.insertSelective(orderit);
        wxuserMapper.Asynchronous(wxuser.getId(),price);
    }

    public void cancelOrder(Orderit orderit) throws SuperMarketException {
        userUtil.UserElent(orderit.getUid());
        Orderit orderit1=orderitMapper.selectOrderid(orderit.getOrderid(),orderit.getUid());
        orderit1.setStatus(CANCEL_PAYMET_PRICE);
        if(orderit1!=null){
            List<OrderitCommodity> orderitCommodityList=orderitCommodityMapper.selectQuartzTask(orderit1.getId());
            for(OrderitCommodity orderitCommodity:orderitCommodityList){
                commodityMapper.updatQuartzTask(orderitCommodity.getCid(),orderitCommodity.getNumber());
            }
            orderitMapper.updateByPrimaryKeySelective(orderit1);
        }else{
            throw  new SuperMarketException("没有此订单");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void paymentGroup(Group group) throws SuperMarketException, ParseException {
        Orderit orderit=new Orderit();
        orderit.setOrderid(DateUtil.getOrderIdByTime());
        orderit.setUid(group.getUid());
        orderit.setAdressid(group.getAdressid());
        orderit.setRemark(group.getRemark());
        orderit.setCreatetime(new Date());
        orderit.setClosetime(DateUtil.plusDay2(7));
        orderit.setStatus(ALEARDY_PAYMET_PRICE);
        Groups groups= groupsMapper.selectByPrimaryKey(group.getGid());
        Integer i=commodityPeopleAttributeMapper.selectBycommodtiyPrice(groups.getCid());
//        Integer i=commodityMapper.selectBycommodtiyPrice(groups.getCid());
        Integer k=relevanceMapper.SelectBydisCount(group.getGid());
        Float sale = Float.valueOf(k) / 10;
        float price=i*sale;
        orderit.setZzprice(price);
        orderit.setAllprice(i);
        orderitMapper.insertSelective(orderit);
        OrderitCommodity orderitCommodity=new OrderitCommodity();
        orderitCommodity.setNumber(1);
        orderitCommodity.setCid(groups.getCid());
        orderitCommodity.setOid(orderit.getId());
        orderitCommodityMapper.insertSelective(orderitCommodity);
        People people=peopleMapper.selectByBollen(group.getUid(),group.getGid());
        people.setPtype(1);
        peopleMapper.updateByPrimaryKeySelective(people);
        Integer j=peopleMapper.selectCountPtype(group.getGid());
        if(j<k){

        }else if (j==k){
            groups.setGtype(1);
            groupsMapper.updateByPrimaryKeySelective(groups);
        }else{
            throw  new SuperMarketException("出现异常请联系客服");
        }
        Wxuser wxuser= wxuserMapper.SelectGroup(group.getUid());
        wxuserMapper.instNotification(wxuser.getId(), Float.valueOf(i));

    }

    @Override
    public  List<Map<Date,Integer>> liushui() {
        List<Map<Date,Integer>> mapList=orderitMapper.liushui();
        return  mapList;
    }


//    public  List<Orderit> select() throws IOException, SolrServerException {
//
//        SolrQuery query = new SolrQuery();
//
//        query.setQuery("*:*");
//        QueryResponse request=solrClient.query(query);
//        SolrDocumentList results = request.getResults();
//        List<Orderit> orderits=new ArrayList<>();
//
//        if(!results.isEmpty()){
//            Gson gson = new Gson();
//            String listString = gson.toJson(results);
//            orderits=gson.fromJson(listString, new TypeToken<List<Orderit>>() {}.getType());
//        }
//        return orderits;
//    }

    @Override
    public void SelectconfirmReceipt(String orderid) throws SuperMarketException {
        Orderit orderit=orderitMapper.SelectconfirmReceipt(orderid);
        if(orderit==null){
            throw new SuperMarketException("次商品还没有发货或没有次订单");
        }
        orderit.setEndTime(new Date());
        orderit.setStatus(50);
        orderitMapper.updateByPrimaryKeySelective(orderit);
    }

    @Override
    public void Deleteorderit(String orderid, Integer uid) throws SuperMarketException {
        Orderit orderit=orderitMapper.selectByDeleteorderit(orderid,uid);
        if(orderid==null){
            throw new SuperMarketException("没有次订单");
        }
        if(orderit.getStatus()==50){
            orderit.setOtype(2);
            orderitMapper.updateByPrimaryKeySelective(orderit);
        }
        if(orderit.getStatus()==0||orderid==null){
            orderitMapper.deleteByPrimaryKey(orderit.getId());
        }
        if(orderit.getStatus()==10){
            List<OrderitCommodity> commodity=orderitCommodityMapper.selectQuartzTask(orderit.getId());
            if (commodity.size() > 0) {
                for (OrderitCommodity orderitCommodity : commodity) {
                    if(orderit.getCoupon()!=null){
                        UserCoupon userCoupon=new UserCoupon();
                        userCoupon.setUid(orderit.getUid());
                        userCoupon.setCid(orderit.getCouponid());
                        userCouponMapper.insertSelective(userCoupon);
                    }
                    CommodityPeopleAttribute commodityPeopleAttribute=commodityPeopleAttributeMapper.selectBycommodtiys(orderitCommodity.getCid());
                    commodityMapper.updatQuartzTask(Math.toIntExact(commodityPeopleAttribute.getCid()), orderitCommodity.getNumber());
                }
                orderitMapper.deleteByPrimaryKey(orderit.getId());
            }
        }
    }
}


