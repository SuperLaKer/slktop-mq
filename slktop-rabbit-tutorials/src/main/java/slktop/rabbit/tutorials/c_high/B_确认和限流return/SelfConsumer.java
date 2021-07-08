package slktop.rabbit.tutorials.c_high.B_确认和限流return;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 *
 * 已经消费：basicAck
 * 未消费：basicNack
 * 直接删除消息（producer端有定时任务：扫描几分钟内消息状态非success的消息）
 * 重回队列再试一次
 */
@SuppressWarnings("all")
public class SelfConsumer extends DefaultConsumer {

    public SelfConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        Channel channel = getChannel();
        try {
            // 根据header判断是否签收()，修改库存、短信、邮件什么的任务执行成功后调用basicAck
            Integer mark = (Integer) properties.getHeaders().get("mark");
            if (mark != 0) {
                // basicAck签收了
                channel.basicAck(envelope.getDeliveryTag(), false);
                System.out.println("已经ACK");
            } else {
                throw new RuntimeException("异常：这个消息不会签收");
            }
        } catch (Exception e) {
            // ack异常，或业务执行失败。nack
            System.out.println("异常消息: " + new String(body));
            // Nack不签收，multiple, requeue:是否重回队列
            channel.basicNack(envelope.getDeliveryTag(), false, false);
        }
    }
}
