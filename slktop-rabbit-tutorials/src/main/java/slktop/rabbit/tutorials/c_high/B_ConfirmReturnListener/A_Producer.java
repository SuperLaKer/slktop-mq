package slktop.rabbit.tutorials.c_high.B_ConfirmReturnListener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.RandomUtils;
import slktop.rabbit.tutorials.c_high.B_ConfirmReturnListener.listeners.AppConfirmListener;
import slktop.rabbit.tutorials.c_high.B_ConfirmReturnListener.listeners.AppReturnListener;
import slktop.rabbit.tutorials.c_high.RabbitConstant;
import slktop.rabbit.tutorials.c_high.RabbitTools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("all")
public class A_Producer {

    public static Channel channel = RabbitTools.getConnectionChannel();

    public static void main(String[] args) throws IOException, InterruptedException {
        // 消息确认
        channel.confirmSelect();

        // 配置exchange
        channel.exchangeDeclare(RabbitConstant.CONFIRM_EX, RabbitConstant.CONFIRM_TYPE);
        channel.queueDeclare(RabbitConstant.CONFIRM_QUEUE, false, false, false, null);
        channel.queueBind(RabbitConstant.CONFIRM_QUEUE, RabbitConstant.CONFIRM_EX, RabbitConstant.CONFIRM_KEY);

        channel.addConfirmListener(new AppConfirmListener());
        channel.addReturnListener(new AppReturnListener());
        // 发送消息
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            channel.basicPublish(RabbitConstant.CONFIRM_EX,
                    RabbitConstant.CONFIRM_KEY, getBasicProperties(), ("消息" + i).getBytes());
        }
    }

    // 每次调用：每条消息的properties都不一样
    private static AMQP.BasicProperties getBasicProperties() {
        Map<String, Object> headsHashMap = new HashMap<>();
        headsHashMap.put("name", "jon");
        int i1 = RandomUtils.nextInt() % 2;
        System.out.println("mark: " + i1);
        headsHashMap.put("mark", i1);
        AMQP.BasicProperties props = new AMQP.BasicProperties().builder()
                .deliveryMode(2)  // 持久化
                .expiration("1000000")  // 消息过期
                .contentEncoding("UTF-8")  // 编码
                .correlationId(UUID.randomUUID().toString())  // ack有关, 返回CorrelationId和ACK
                .headers(headsHashMap)  // 消息头
                .build();
        return props;
    }
}
