package slktop.rabbit.tutorials.c_high.A_ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.RandomUtils;
import slktop.rabbit.tutorials.c_high.RabbitTools;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 场景：两公司通过mq进行数据交换，consumer肯定需要进行限流处理
 * <p>
 * ack: 手动签收，自动签收
 */
@SuppressWarnings("all")
public class Producer {

    public static final String EXCHANGE_NAME = "confirm exchange";
    public static final String QUEUE_NAME = "queue_confirm";


    public static void main(String[] args) throws IOException, InterruptedException {
        Channel channel = RabbitTools.getConnectionChannel();
        // 发送数条消息
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");



        String str = "Hello World!";
        for (int i = 0; i < 20; i++) {
            String message = str + "---" + i;
            Thread.sleep(2000);
            channel.basicPublish(EXCHANGE_NAME, "", getBasicProperties(), message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }

    }

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
                .correlationId(UUID.randomUUID().toString())  // ack有关
                .headers(headsHashMap)  // 消息头
                .build();
        return props;
    }
}
