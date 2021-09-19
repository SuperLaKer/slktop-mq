package slktop.rabbit.tutorials.a_java;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import slktop.rabbit.tutorials.c_high.RabbitTools;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 */
public class 消息属性 {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitTools.getConnectionChannel();
        assert channel != null;
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        Map<String, Object> headsHashMap = new HashMap<>();
        headsHashMap.put("name", "jon");
        AMQP.BasicProperties props = new AMQP.BasicProperties().builder()
                .deliveryMode(2)  // 持久化
                .expiration("10000")  // 消息过期
                .contentEncoding("UTF-8")  // 编码
                .correlationId(UUID.randomUUID().toString())  // ack有关
                .headers(headsHashMap)  // 消息头
                .build();


        String str = "Hello World!";
        for (int i = 0; i < 20; i++) {
            String message = str + "---" + i;
            Thread.sleep(2000);
            channel.basicPublish(EXCHANGE_NAME, "", props, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
