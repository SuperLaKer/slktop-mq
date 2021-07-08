package slktop.rabbit.tutorials.c_high.A_签收;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class SelfConsumer extends DefaultConsumer {

    public SelfConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        Channel channel = getChannel();
        try {
            // 根据header判断是否签收
            Integer mark = (Integer) properties.getHeaders().get("mark");
            if (mark != 0) {
                // basicAck签收了
                channel.basicAck(envelope.getDeliveryTag(), false);
                System.out.println("消息已经消费...");
            } else {
                throw new RuntimeException("异常：这个消息不会签收");
            }
        } catch (Exception e) {
            System.out.println("异常消息: " + new String(body));
            // 重回队列?  requeue需要设置为false否则一直死循环
            // 通过定时任务或手动干预
            // Nack不签收，multiple, requeue:是否重回队列
            channel.basicNack(envelope.getDeliveryTag(), false, false);
        }
    }
}
