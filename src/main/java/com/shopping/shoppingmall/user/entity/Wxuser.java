package com.shopping.shoppingmall.user.entity;

public class Wxuser {
    private Long id;
    //微信号
    private String openid;
    //微信名称
    private String niname;
    //线上积分
    private Integer onlineGrade;
    //线下积分
    private Integer offlineGrade;


    private  User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getNiname() {
        return niname;
    }

    public void setNiname(String niname) {
        this.niname = niname == null ? null : niname.trim();
    }

    public Integer getOnlineGrade() {
        return onlineGrade;
    }

    public void setOnlineGrade(Integer onlineGrade) {
        this.onlineGrade = onlineGrade;
    }

    public Integer getOfflineGrade() {
        return offlineGrade;
    }

    public void setOfflineGrade(Integer offlineGrade) {
        this.offlineGrade = offlineGrade;
    }
}