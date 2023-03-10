package com.hmdp;

import com.hmdp.service.impl.ShopServiceImpl;
import com.hmdp.utils.RedisIdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class HmDianPingApplicationTests {
    @Resource
    private ShopServiceImpl shopService;

    @Resource
    private RedisIdWorker redisIdWorker;

    //    @Test
//    void test() throws InterruptedException {
//        shopService.saveShop2Redis(1L,10L);
//        shopService.saveShop2Redis(2L,10L);
//        shopService.saveShop2Redis(3L,10L);
//        shopService.saveShop2Redis(4L,10L);
//        shopService.saveShop2Redis(5L,10L);
//    }
    private ExecutorService es = Executors.newFixedThreadPool(500);

    @Test
    void testWorker() {
        CountDownLatch countDownLatch = new CountDownLatch(300);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    long id = redisIdWorker.nextId("order");
                    System.out.println("id = " + id);
                }
                countDownLatch.countDown();
            }
        };
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 300; i++) {
            es.submit(task);
        }
        long end = System.currentTimeMillis();
        System.out.println("time = " + (end - begin));

    }

    @Test
    void test2() {
        System.out.println(1 & 8);
    }

}
