package slktop.rabbit.tutorials.c_high.A_ack;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import slktop.rabbit.tutorials.c_high.RabbitTools;

/**
 * 场景：两公司通过mq进行数据交换，consumer肯定需要进行限流处理
 * <p>
 * ack: 手动签收，自动签收
 */
public class Consumer {
    public static final String EXCHANGE_NAME = "confirm exchange";
    public static final String QUEUE_NAME = "queue_confirm";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitTools.getConnectionChannel();
        assert channel != null;

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        // 关闭自动签收，自定义消费者，把channel传递过去
        channel.basicConsume("",false, new SelfConsumer(channel));
    }
}