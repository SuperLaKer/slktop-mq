package slktop.mq.order.webmvc.controller;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import slktop.constants.product.ConstSecKillRedisKey;
import slktop.mq.order.globalexception.OrderServiceException;
import slktop.mq.order.webmvc.entity.vo.FlashOrderVo;
import slktop.mq.order.webmvc.entity.vo.OrderProduct;
import slktop.mq.order.webmvc.service.IOrderService;
import slktop.mq.order.webmvc.service.LocalCache;

import java.util.ArrayList;
import java.util.Random;

@RestController
@RequestMapping("/web/order")
public class OrderController {

    @Autowired
    LocalCache<Boolean> localCache;

    @Autowired
    IOrderService orderService;

    @PutMapping("/")
    public OrderResponseResult genFlashOrder(@RequestBody @Validated FlashOrderVo flashOrderVo) {
        return orderService.createFlashOrder(flashOrderVo);
    }

    @GetMapping("/clear")
    public String clearLocalCache() {
        localCache.remove(ConstSecKillRedisKey.STORE_IS_NULL + 1);
        return "清除成功";
    }

    @GetMapping("/local/cache")
    public String getLocalCache() {
        Object cache = localCache.getCache(ConstSecKillRedisKey.STORE_IS_NULL + 1);
        return cache != null ? "存在锁" : "不存在锁";
    }

    @GetMapping("/test/{num}")
    public OrderResponseResult orderServiceTest(@PathVariable("num") Integer num) {
        // 本地标志
        Object cache = localCache.getCache(ConstSecKillRedisKey.STORE_IS_NULL + 1);
        if (cache != null) {
            throw new OrderServiceException(1 + ": 商品售罄ccc1...");
        }

        Random random = new Random();
        FlashOrderVo flashOrderVo = new FlashOrderVo();
        flashOrderVo.setUser_id(1);

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct_id(1);
        int i = random.nextInt(5) + 1;
        System.out.println("需求：" + i);
        orderProduct.setProduct_num(num != 1 ? num : i);
        ArrayList<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(orderProduct);
        flashOrderVo.setProductIdNum(orderProducts);
        OrderResponseResult flashOrder = orderService.createFlashOrder(flashOrderVo);
        System.out.println(flashOrder.getCode());
        return flashOrder;
    }
}

@Configuration
class TestControllerTask implements Runnable, ApplicationContextAware {

    ApplicationContext context;

    @Override
    public void run() {
        OrderController orderController = context.getBean(OrderController.class);
        //orderController.orderServiceTest();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}