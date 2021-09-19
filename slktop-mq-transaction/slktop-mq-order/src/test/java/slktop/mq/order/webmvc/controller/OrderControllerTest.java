package slktop.mq.order.webmvc.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;
import slktop.mq.order.webmvc.service.LocalCache;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderControllerTest {

    @Autowired
    OrderController orderController;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    TestControllerTask testControllerTask;

    @Test
    public void orderServiceTest() {
        ArrayList<Map<String, Integer>> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            threadPoolTaskExecutor.submit(testControllerTask);
//            orderController.orderServiceTest();
        }
    }


    @Autowired
    LocalCache<Boolean> booleanLocalCache;

    @Test
    public void testLocal() {
        Object cache = booleanLocalCache.getCache("1");
        System.out.println(cache);

    }

    @Test
    public void testRandom() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(5));
        }
    }
}