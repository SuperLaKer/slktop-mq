package slktop.rabbit.tutorials.a_java;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import slktop.rabbit.tutorials.RabbitTools;

import java.nio.charset.StandardCharsets;

/**
 * message+routing_key ---> exchange 1--binding_Key-->* queue
 * <p>
 * routing_key和binding_key对应
 * 所有的routing和bindingKey都一样 == fanout
 */
public class DB_Direct_Subscribing_warning {
    // exchange
    private static final String exchange_name = "logs_system_direct_exchange";
    // queue and exchange binding
    private static final String binding_key = "warning";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitTools.getConnectionChannel();
        assert channel != null;

        // 交换机名字一样
        channel.exchangeDeclare(exchange_name, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();
        // 绑定交换机和queue
        channel.queueBind(queueName, exchange_name, binding_key);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}