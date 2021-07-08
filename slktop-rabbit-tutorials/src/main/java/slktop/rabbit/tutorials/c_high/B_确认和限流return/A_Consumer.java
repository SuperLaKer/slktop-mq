package slktop.rabbit.tutorials.c_high.B_确认和限流return;

import com.rabbitmq.client.Channel;
import slktop.rabbit.tutorials.RabbitConstant;
import slktop.rabbit.tutorials.RabbitTools;

import java.io.IOException;

public class A_Consumer {
    public static Channel channel = RabbitTools.getConnectionChannel();

    public static void main(String[] args) throws IOException {
        // 配置exchange
        channel.exchangeDeclare(RabbitConstant.CONFIRM_EX, RabbitConstant.CONFIRM_TYPE);
        channel.queueDeclare(RabbitConstant.CONFIRM_QUEUE, false, false, false, null);
        channel.queueBind(RabbitConstant.CONFIRM_QUEUE, RabbitConstant.CONFIRM_EX, RabbitConstant.CONFIRM_KEY);

        // 限流：需要手动ack
        // channel.basicQos(0, 3, false);
        channel.basicConsume(RabbitConstant.CONFIRM_QUEUE, false, new SelfConsumer(channel));
    }
}
