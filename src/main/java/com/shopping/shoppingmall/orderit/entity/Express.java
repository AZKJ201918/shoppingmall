package com.shopping.shoppingmall.orderit.entity;

import lombok.Data;

@Data
public class Express {
    private Long id;

    private String orderId;

    private String expressNum;

    private String logisticMode;

    private String expressCompany;

    private String orderNote;

}