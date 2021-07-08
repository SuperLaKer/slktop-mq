package slktop.rabbit.tutorials.a_java;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import slktop.rabbit.tutorials.RabbitTools;

import java.nio.charset.StandardCharsets;

/**
 * 直接拷贝Recv.java ：默认 轮询分发任务、挂了消息丢失
 */
public class AB_HelloWorld_Recv {
    public static void main(String[] argv) throws Exception {

        Channel channel = RabbitTools.getConnectionChannel();
        // producer和consumer都建立queue，因为不知道谁先启动
        // durable: queue是否持久化，应该开启防止服务器挂掉 -->
        assert channel != null;
        channel.queueDeclare(Const.SIMPLE_QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // 自定义消费者，消费者实现DeliverCallback 监听回调：用于处理消息
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };

        // 监听
        // autoAck=true，不启用消息确认
        System.out.println("执行监听...");
        channel.basicConsume(Const.SIMPLE_QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}
