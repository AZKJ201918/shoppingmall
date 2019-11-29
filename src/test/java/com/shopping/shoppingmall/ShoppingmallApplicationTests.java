package com.shopping.shoppingmall;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shopping.shoppingmall.commodity.controller.CommodtiyController;

import com.shopping.shoppingmall.commodity.service.CommodtiyService;
import com.shopping.shoppingmall.common.exception.SuperMarketException;

import com.shopping.shoppingmall.homepage.service.HomepageService;
import com.shopping.shoppingmall.orderit.entity.Coordinates;
import com.shopping.shoppingmall.orderit.entity.Discuss;
import com.shopping.shoppingmall.orderit.entity.Orderit;
import com.shopping.shoppingmall.orderit.service.OrderitService;
import com.shopping.shoppingmall.shopcart.service.ShopcartService;
import com.shopping.shoppingmall.user.controller.WxuserController;
import com.shopping.shoppingmall.user.dao.MessageMapper;
import com.shopping.shoppingmall.user.entity.*;
import com.shopping.shoppingmall.user.service.UserService;
import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import org.apache.solr.client.solrj.SolrServerException;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.Sphere;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingmallApplicationTests {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	private WxuserController wxuserController;

	@Autowired
	@Qualifier("commodtiyServiceImpl")
	private CommodtiyService commodtiyService;

	@Autowired
	@Qualifier("orderitServiceImpl")
	private OrderitService orderitService;

	@Autowired
	@Qualifier("shopcartServiceImpl")
	private ShopcartService shopcartService;

	@Autowired
	@Qualifier("homepageServiceImpl")
	private HomepageService homepageService;


	@Autowired
	private CommodtiyController commodtiyController;

	@Autowired
	private MessageMapper messageMapper;


	@Autowired
	private MongoTemplate mongoTemplate;


	@Test
	public void contextLoads() throws SuperMarketException, IOException, SolrServerException, ParseException {
//		ApiResult result=new ApiResult();
//		result=commodtiyController.selectByCommodity();
//		userService.insermessage(1307,"123123","21312321321312");
//		userService.messages("12431242141241");


//
//		homepageService.SelectHotWaimaoquan();

		//System.out.println(	commodtiyService.selectByCommoditytype().toString());
		//userService.selectAdress(1213);
		//wxuserController.Queen();
//		commodtiyService.selectCommdity(4l,1244);
//		orderitService.liushui();
//		List<String> list=new ArrayList<>();
//		list.add("1");
//		list.add("2");
//		Object[] aa= list.toArray();
//		System.out.println(aa[0]+"------------"+aa[1]);
//		List<Object> stringList= Arrays.asList(aa);
//		for(Object o:stringList){
//			System.out.println(o);
//		}
//
//
//		List<Object> lists=new ArrayList<>();
//		Collections.addAll(lists,aa);
//		for(Object o:lists){
//			System.out.println(o);
//		}
//
//     Commodity commodity= commodtiyService.selectCommdity(1l,0);
//		System.out.println();
//		List<User> users=userService.users();
//		for (User user:users){
//			System.out.println(user.toString());
//		}
//		List<Orderit> orderitList=orderitService.select();
//		System.out.println(1111);
//		CommodityPeopleAttribute commodityPeople=new CommodityPeopleAttribute();
//		commodityPeople.setAid(1);
//		commodityPeople.setPid(1);
//		commodityPeople.setCid(1l);
//		CommodityPeopleAttribute commodityPeopleAttribute=commodtiyService.SelectCommdityPeople(commodityPeople);
//		System.out.println(commodityPeopleAttribute.toString());

//		shopcartService.Selectviewcart(1288);
//		List<Commoditytype> commodities=commodtiyService.selectByCommoditytype();
//		commodities.stream().forEach(
//				param->{
//					System.out.println(param);
//				}
//		);
//		People people = new People();
//		people.setGid(85);
//		userService.JoinGroup(people);

//		userService.users();
//		commodtiyService.selectByCommoditytype();

//		System.out.println(userService.selectAdress(1288).toString());
//		System.out.println(DateUtil.plusDay2(1));
//		List<Commodity> commodityList=commodtiyService.select(10001l);
//		System.out.println(commodityList);
//		shopcartService.Selectviewcart(1290);


//		List<Commodity> commodities=commodtiyService.select(10001l);
//		commodities.stream().forEach(
//				para->{
//					System.out.println(para.toString());
//				}
//		);

//		List<Coupon> couponList=userService.SelectPurchase();
//		couponList.stream().forEach(
//				pras->{
//					System.out.println(pras.toString());
//				}
//		);
//
//		for(Coupon coupon:couponList){
//			System.out.println(coupon.toString());
//		}
//
//		System.out.println(1);
//		System.out.println(1);
//		System.out.println(1);
//		System.out.println(1);
//		System.out.println(1);
//		System.out.println(1);
//UserCoupon userCoupon=new UserCoupon();
//userCoupon.setUid(1314);
//userCoupon.setCid(1000009);
//userService.DrawDown(userCoupon);
//		Query query=new Query();

		double x = 106.063339, y = 30.547347;
		Point point=new Point(x,y);
		Sphere sphere = new Sphere(point, 6.2137119 / 3963.2);
		List<Coordinates> positions = mongoTemplate.find(new Query(Criteria.where("location.coordinates").within(sphere)), Coordinates.class);
        for (Coordinates coordinates:positions){
			JSONObject jsonObject= (JSONObject) JSON.toJSON(coordinates);
			System.out.println(jsonObject.getString("hid"));
			JSONObject points= jsonObject.getJSONObject("location");
			JSONArray jsonArray=points.getJSONArray("coordinates");
			System.out.println(jsonArray.getDouble(0));
			System.out.println(jsonArray.getDouble(1));
		}


		//		List<Coordinates> list=mongoTemplate.find(query1,Coordinates.class,"coordinates");

//		query.addCriteria(Criteria.where("hid").is("1"));
//		List<Coordinates> coordinates=mongoTemplate.find(query, Coordinates.class);
//		query.addCriteria(Criteria.where("_id").is("5dbfc4284e89983444793ff3"));
//		query.addCriteria(Criteria.where("_id").is("5dbfc4284e89983444793ff3"));
		Update update = new Update();
//        Discuss discuss=new Discuss();
//        discuss.setWxname("5201314520");
//        update.setOnInsert("discuss",discuss);
//		update.set("", "213213215201314520");
//		UpdateResult discusss=mongoTemplate.updateMulti(query,update,Discuss.class);

//		List<Discuss> discussList= mongoTemplate.find(query,Discuss.class);
//		for (Discuss discuss:discussList){
//			System.out.println(discuss.toString());
//		}
//		System.out.println(1);
//
//		Discuss discuss=new Discuss();
//		discuss.setCid(1);
//		discuss.setDetails("12312");
//		discuss.setDetailsImg("131231");
//		discuss.setEvaluate(2);
//		discuss.setUid("12312312");
//		mongoTemplate.save(discuss);







	}





}
