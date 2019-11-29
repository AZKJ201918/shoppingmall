package com.shopping.shoppingmall.user.entity;

import lombok.Data;

@Data
public class People {
    private Integer pid;

    private Integer gid;

    private Integer uid;

    private Integer status;

   private  Wxuser wxuser;

    private Integer ptype;
}