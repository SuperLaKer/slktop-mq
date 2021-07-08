package slktop.rabbit.tutorials.dlx;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 死信队列: 专门处理未被消费或过期的消息的exchange
 * 涉及到两个交换机
 * 交换机A: 消息没有被消费，消息变为 “ 死信 ”
 * 交换机A: 把死信发给交换机 B
 * 交换机B: 处理 “死信”, 交换机B就是死信交换机
 */
public class A_DlxMain {

    public static final String EXCHANGE_NAME = "";

    public static void main(String[] args) throws Exception {

        Channel channel = getChannel();
        AMQP.BasicProperties props = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .expiration("10000")
                .build();

        // 消息发送给正常的交换机
        channel.exchangeDeclare(DlxConstant.DLX_NORMAL_EX, DlxConstant.EX_TYPE, true, false, null);
        for (int i = 0; i < 10; i++) {
            channel.basicPublish(DlxConstant.DLX_NORMAL_EX, DlxConstant.DLX_NORMAL_KEY,
                    props, ("消息"+i).getBytes());
        }
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
