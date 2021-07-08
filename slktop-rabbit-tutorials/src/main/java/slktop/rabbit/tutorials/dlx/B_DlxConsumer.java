package slktop.rabbit.tutorials.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * 死信队列: 专门处理未被消费或过期的消息的exchange
 * 涉及到两个交换机
 * 交换机A: 消息没有被消费，消息变为 “ 死信 ”
 * 交换机A: 把死信发给交换机 B
 * 交换机B: 处理 “死信”, 交换机B就是死信交换机
 */
public class B_DlxConsumer {

    public static final String EXCHANGE_NAME = "";

    public static void main(String[] args) throws Exception {
        Channel channel = getChannel();

        // normal exchange
        channel.exchangeDeclare(DlxConstant.DLX_NORMAL_EX, DlxConstant.EX_TYPE, true, false, null);
        // normal queue
        HashMap<String, Object> queueArgs = new HashMap<>();
        queueArgs.put("x-dead-letter-exchange", DlxConstant.DLX_DELAY_EX);
        queueArgs.put("x-max-length", 4);
        channel.queueDeclare(DlxConstant.DLX_NORMAL_QUEUE, true, false, false, queueArgs);
        channel.queueBind(DlxConstant.DLX_NORMAL_QUEUE, DlxConstant.DLX_NORMAL_EX, DlxConstant.DLX_NORMAL_KEY);


        // 死信 delay
        channel.exchangeDeclare(DlxConstant.DLX_DELAY_EX, DlxConstant.EX_TYPE, true, false, null);
        channel.queueDeclare(DlxConstant.DLX_DELAY_QUEUE, true, false, false, null);
        channel.queueBind(DlxConstant.DLX_DELAY_QUEUE, DlxConstant.DLX_DELAY_EX, "#");


        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        channel.basicConsume(DlxConstant.DLX_NORMAL_QUEUE, false, deliverCallback, consumerTag -> {
        });


    }



    private static Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }

}
