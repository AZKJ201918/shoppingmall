package com.shopping.shoppingmall.common.sms;



import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service("verCodeService")
@Slf4j
public class MiaoDiSmsSender implements SmsSender{


    @Override
    public void sendSms(String a, String b, String c, String d, String e) {
        System.out.println("---------------------------------------------");
        System.out.println(a+"---------"+b+"-----"+c+"-------"+d+"---------"+e+"--------");
    }
}

