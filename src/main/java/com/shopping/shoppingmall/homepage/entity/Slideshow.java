package com.shopping.shoppingmall.homepage.entity;

import lombok.Data;

@Data
public class Slideshow {
    private Integer slid;

    private String sname;

    private String simg;

    private Boolean isEmpty=true;

    private Introduce introduce;
}