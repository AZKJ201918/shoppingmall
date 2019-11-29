package com.shopping.shoppingmall.common.exception;

public class SuperMarketException  extends Exception {



    public SuperMarketException(String message) {
        super(message);
    }


    public int getStatusCode() {
        return 500;
    }
}
