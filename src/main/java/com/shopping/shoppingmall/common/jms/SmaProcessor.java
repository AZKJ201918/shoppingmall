package com.shopping.shoppingmall.common.jms;


import org.springframework.stereotype.Component;




@Component
public class SmaProcessor {


//    @Autowired
//    private JmsMessagingTemplate jmsMessagingTemplate;
//
//
//    @Autowired
//    @Qualifier("verCodeService")
//    private SmsSender smsSender;
//
//    public  void sendSmsToQueue(Destination destination, final String message){
//        jmsMessagingTemplate.convertAndSend(destination,message);
//
//    }
//
//
//    @JmsListener(destination = "sms_queue")
//    public void doSendSmsMessage(String text){
//        JSONObject jsonObject = JSON.parseObject(text);
//        smsSender.sendSms(jsonObject.getString("chengxiang"),jsonObject.getString("yeshun"),jsonObject.getString("shenqingyuan"),jsonObject.getString("yanxuejun"),jsonObject.getString("jinqifan"));
//    }
}
