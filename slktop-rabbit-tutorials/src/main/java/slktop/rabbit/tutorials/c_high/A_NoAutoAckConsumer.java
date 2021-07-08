package slktop.rabbit.tutorials.c_high;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;

import java.io.IOException;

public class A_NoAutoAckConsumer extends DefaultConsumer {

    public A_NoAutoAckConsumer(Channel channel) {
        super(channel);
    }

    /**
     * 参数看看a_base里面的
     */
    @Override
    public void handle(String consumerTag, Delivery message) throws IOException {

        // 不ACK(放弃的任务|tag错误)， multile批量处理，requeue重回队列(设置为false,否则一直死循环)
        channel.basicNack(message.getEnvelope().getDeliveryTag(), false, false);
    }
}
