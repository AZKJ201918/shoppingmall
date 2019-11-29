package com.shopping.shoppingmall.common.quartz;


import com.shopping.shoppingmall.orderit.service.OrderitService;
import com.shopping.shoppingmall.user.service.UserService;
import com.shopping.shoppingmall.user.service.WxuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class QuartzTask {

        @Autowired
        @Qualifier("userServiceImpl")
        private UserService userService;

        @Autowired
        @Qualifier("wxuserServiceImpl")
        private WxuserService wxuserService;

        @Autowired
        @Qualifier("orderitServiceImpl")
        private OrderitService orderitService;


        @Scheduled(cron = "0 0 0 * * ?")
        //定时任务删除优惠卷的
        public void play() throws Exception {

            userService.UpdateCouponsRegularly();
            orderitService.SelectOrderitTask();
            wxuserService.AlwaysVisitToday();
            System.out.println("执行完成1");

       }

    @Scheduled(cron = "0 0 * * * ?")
    //每一个小时刷一次
    public  void QuartzTask(){
        userService.UpdateGroups();
        System.out.println("执行完成2");
}

}


