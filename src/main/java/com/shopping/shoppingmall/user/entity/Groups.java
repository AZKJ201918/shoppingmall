package com.shopping.shoppingmall.user.entity;


import com.shopping.shoppingmall.commodity.entity.Commodity;
import com.shopping.shoppingmall.commodity.entity.CommodityPeopleAttribute;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
public class Groups {
    private Integer gid;

    private Integer cid;

    private Date starttime;

    private Date endtime;

    private CommodityPeopleAttribute commodityPeopleAttribute;

    private List<People> peopleList;

    private  People people;

    private Integer count;

    private Integer gtype;

    private Integer maxcount;

    private Integer discount;

}