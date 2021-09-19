package slktop.rocket.examples.b_order;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

/**
 * 顺序消息消费，带事务方式（应用可控制Offset什么时候提交）
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_3");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        /*
          设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
          如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TopicTest", "TagA || TagC || TagD");
        // 一组有序消息发送到了同一个queue
        // 消费者并发消费消息如何保证顺序？：一个线程绑定一个queue，这样就有序了
        consumer.registerMessageListener(new ConsumerMessageListener());
        consumer.start();
        System.out.println("Consumer Started.");
    }
}
