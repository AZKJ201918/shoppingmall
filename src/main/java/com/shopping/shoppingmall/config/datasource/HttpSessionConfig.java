package com.shopping.shoppingmall.config.datasource;

import com.shopping.shoppingmall.common.constants.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



public class HttpSessionConfig {

//    @Autowired
//    private Parameters parameters;
//    @Bean
//    public HttpSessionStrategy httpSessionStrategy(){
//        return  new HeaderHttpSessionStrategy();
//    }
//
//    @Bean
//    public JedisConnectionFactory connectionFactory(){
//        JedisConnectionFactory connectionFactory=new JedisConnectionFactory();
//        String redisHost= parameters.getRedisHost();
//        Integer redisPort=parameters.getRedisPort();
//        connectionFactory.setTimeout(2000);
//        connectionFactory.setHostName(redisHost);
//        connectionFactory.setPort(redisPort);
//
//        return  connectionFactory;
//
//    }
}
