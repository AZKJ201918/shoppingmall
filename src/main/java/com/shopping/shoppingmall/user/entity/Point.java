package com.shopping.shoppingmall.user.entity;

import lombok.Data;


@Data
public class Point {

    public Point() {
    }
    public Point(Double[] loc){
        this.longitude = loc[0];
        this.latitude = loc[1];
    }
    public Point(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    private Integer hid;

    private double longitude;

    private double latitude;
}
