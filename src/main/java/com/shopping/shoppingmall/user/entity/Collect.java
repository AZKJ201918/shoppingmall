package com.shopping.shoppingmall.user.entity;


import com.shopping.shoppingmall.commodity.entity.Commodity;
import lombok.Data;

@Data
public class Collect {
    private Integer coid;

    private Integer cid;

    private Integer uid;


    private Commodity commodity;


}