package slktop.rocket.examples.e_tags;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;

/**
 * 消费者将接收包含TAGA或TAGB或TAGC的消息。
 * 但是限制是一个消息只能有一个标签，这对于复杂的场景可能不起作用。
 * 在这种情况下，可以使用SQL表达式筛选消息。
 * SQL特性可以通过发送消息时的属性来进行计算。
 * 在RocketMQ定义的语法下，可以实现一些简单的逻辑。下面是一个例子：
 */
public class Producer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("CID_EXAMPLE");
        consumer.subscribe("TOPIC", "TAGA || TAGB || TAGC");
    }
}
