package slktop.mq.order.webmvc.service.impl;

import aa.slkenv.utils.redis.RedisUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slktop.constants.product.ConstSecKillRedisKey;
import slktop.mq.order.SnowFlakeIDGenerator;
import slktop.mq.order.globalexception.OrderServiceException;
import slktop.mq.order.mq.OrderMessageSender;
import slktop.mq.order.redistools.OrderServiceRedisLua;
import slktop.mq.order.webmvc.controller.OrderResponseResult;
import slktop.mq.order.webmvc.entity.Order;
import slktop.mq.order.webmvc.entity.vo.FlashOrderVo;
import slktop.mq.order.webmvc.entity.vo.OrderProduct;
import slktop.mq.order.webmvc.mapper.OrderMapper;
import slktop.mq.order.webmvc.service.IOrderService;
import slktop.mq.order.webmvc.service.LocalCache;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author slk
 * @since 2021-08-02
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    @SuppressWarnings("all")
    OrderMapper orderMapper;

    @Autowired
    LocalCache<Boolean> localCache;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    OrderServiceRedisLua orderServiceRedisLua;

    @Autowired
    OrderMessageSender messageSender;

    @Override
    public OrderResponseResult createFlashOrder(FlashOrderVo flashOrderVo) {

        // 判断商品是否卖完了
        OrderProduct orderProduct = flashOrderVo.getProductIdNum().get(0);
        Integer product_id = orderProduct.getProduct_id();
        Integer product_num = orderProduct.getProduct_num();

        // 本地标志
        Object cache = localCache.getCache(ConstSecKillRedisKey.STORE_IS_NULL + product_id);
        if (cache != null) {
            throw new OrderServiceException(product_id + ": 商品售罄1...");
        }

        // 访问redis
        Long aLong = orderServiceRedisLua.useLua(ConstSecKillRedisKey.FLASH_KEY + product_id, product_num);
        if (aLong == null) {
            throw new OrderServiceException(product_id + ": 网络拥挤请稍后重试...");
        }
        // 刚好卖完
        if (aLong == 0) {
            setLocalProductId(product_id);
        }
        // 库存不足
        if (aLong < 0) {
            setLocalProductId(product_id);
            throw new OrderServiceException(product_id + ": 库存不足...");
        }
        // 生成订单
        Order order = getOrder(flashOrderVo);
        try {
            Random random = new Random();
            int i = random.nextInt(6);
            if (i >= 5) {
                throw new OrderServiceException(i + "订单入库失败haha，下单失败！");
            }
            int insert = orderMapper.insert(order);
        } catch (Exception e) {
            // 库存补偿
            redisUtils.incr(ConstSecKillRedisKey.FLASH_KEY + product_id, product_num);
            removeLocalProductId(product_id);
            throw new OrderServiceException("订单入库失败，下单失败！");
        }
        // 发送消息
        try {
            messageSender.send(order.getOrderMessage());
        } catch (Exception e) {
            new RuntimeException("订单和消息持久化成功，发送消息失败！" +
                    "不影响，定时任务会定时扫描").printStackTrace();
        }
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order);
        return OrderResponseResult.success(orders);
    }


    // 售罄
    private void setLocalProductId(Integer product_id) {
        try {
            localCache.setLocalCache(ConstSecKillRedisKey.STORE_IS_NULL + product_id, true);
        } catch (Exception ignored){

        }
    }

    // 没有售罄
    private void removeLocalProductId(Integer product_id) {
        try {
            localCache.remove(ConstSecKillRedisKey.STORE_IS_NULL + product_id);
        } catch (Exception ignored){

        }
    }

    // 判断是否卖完了
    private void isProductNoLeft(String product_id) {
        Object cache = localCache.getCache(ConstSecKillRedisKey.STORE_IS_NULL + product_id);
        if (cache != null) {
            throw new OrderServiceException(product_id + ": 商品已经售罄...");
        }

        Integer left = redisUtils.get(ConstSecKillRedisKey.FLASH_KEY + product_id, Integer.class);
        if (left <= 0) {
            localCache.setLocalCache(ConstSecKillRedisKey.STORE_IS_NULL + product_id, true);
        }
    }


    // 获取orderMessage by order
    private Order getOrder(FlashOrderVo flashOrderVo) {
        Order order = new Order();

        // 时间信息
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        // 用户信息和商品信息
        order.setUserId(flashOrderVo.getUser_id());
        order.setProductIdNum(flashOrderVo.getProductIdNum());

        order.setProductId(flashOrderVo.getProductIdNum().get(0).getProduct_id());
        order.setProductNum(flashOrderVo.getProductIdNum().get(0).getProduct_num());

        // 订单入库 == 订单消息入口
        order.setOrderStatus(1);
        order.setOrderMessageStatus(1);

        order.setOnlyId(String.valueOf(SnowFlakeIDGenerator.generateSnowFlakeId()));
        return order;
    }


    /**
     * message和product都需要入库，这里使用一个order就行
     */
    @Transactional
    public void saveOrderAndMessage(Order order) {
        try {
            orderMapper.insert(order);
        } catch (Exception e) {
            throw new OrderServiceException("保存订单信息失败，无法继续下单！");
        }
    }
}
