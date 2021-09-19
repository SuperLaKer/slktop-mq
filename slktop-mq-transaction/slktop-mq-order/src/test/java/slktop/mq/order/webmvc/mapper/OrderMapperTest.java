package slktop.mq.order.webmvc.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import slktop.mq.order.webmvc.entity.Order;

import java.sql.Timestamp;


@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMapperTest {
    @Autowired
    @SuppressWarnings("all")
    OrderMapper orderMapper;


    @Test
    public void insertTet() {
        Order order = new Order();
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//        order.setOrderDescribe("测试mapper");
        order.setUserId(111);
        int insert = orderMapper.insert(order);
        System.out.println(insert);
        System.out.println(order);
    }

    @Test
    public void messageInsertTet() {
//        Message message = new Message();
//        message.setTag("22");
//        int insert = messageMapper.insert(message);
//        System.out.println(message);
//        System.out.println(insert);
    }
}