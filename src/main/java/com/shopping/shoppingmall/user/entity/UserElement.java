package com.shopping.shoppingmall.user.entity;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserElement implements Serializable {

    private String userId;

    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 转 map
     * @return
     */
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", this.userId);
        map.put("token", token);
        return map;
    }

    /**
     * map转对象
     * @param map
     * @return
     */
    public static UserElement fromMap(Map<String, String> map) {
        UserElement ue = new UserElement();
        ue.setToken(map.get("token"));
        ue.setUserId(map.get("userId"));
        return ue;
    }
}
