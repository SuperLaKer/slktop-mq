package slktop.rabbit.tutorials.a_java;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import slktop.rabbit.tutorials.c_high.RabbitTools;

import java.nio.charset.StandardCharsets;

/**
 * 之前的日志系统：广播
 * 现在：根据日志等级区分日志，error日志通过一个consumer持久化，其他日志通过另一个consumer输出到屏幕上
 * 简单的说：根据日志等级过滤日志
 * <p>
 * ## routingKey(bindKey)
 * <p>
 * 绑定：queue对exchange中的message感兴趣
 * 区分 routingKey
 * channel.queueBind(routingKey);  // bind key
 * channel.basicPublish(routingKey)  // ···
 * <p>
 * bind Key 的含义取决于交换类型:
 * - Fanout exchange: 忽略
 * - Direct exchange:
 * <p>
 * message可以设置routingKey
 * exchange和queue之间也可以绑定routingKey.(routingKey相同 == fanout, rabbit支持这样做)
 * <p>
 * 如何根据日志(message)等级过滤日志？
 * log的等级作为 routingKey
 */
public class DC_Direct_Subscribing_error {

    private static final String EXCHANGE_NAME = "logs_system_direct_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitTools.getConnectionChannel();
        assert channel != null;

        // 交换机名字一样
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "error");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        // 自定义消费者 new CallbackConsumer(channel) implement DeliverCallback
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}