package slktop.rabbit.tutorials.a_java;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * https://www.rabbitmq.com/tutorials/tutorial-two-java.html
 */
public class AC_HelloWorld_Recv {
    public static void main(String[] argv) throws Exception {

        // 作为client，建立连接是必须的
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // producer和consumer都建立queue，因为不知道谁先启动
        // durable: 是否持久化，应该开启防止服务器挂掉
        channel.queueDeclare(Const.SIMPLE_QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(1);  // 一次就收一个任务，等我做完在发送新的任务

        // 监听回调：用于处理消息
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            try {
                // 模拟重任务，可以证实rabbit一些特性：
                // 1、任务按数量平均分配,而不是重量
                // 2、结束前杀掉服务，消息丢失
                // 3、验证basicQos可用性
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" [x] Received '" + message + "'");
        };

        // 监听
        // autoAck=false启用消息确认, 该服务挂了 rabbitServer会发剩下的消息重新投递给其他消费者
        System.out.println("执行监听...");
        channel.basicConsume(Const.SIMPLE_QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });
    }
}
