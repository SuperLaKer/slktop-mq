package slktop.rabbit.tutorials.a_java;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 网络模型：
 * connect(ip:port) ---> channels.createQueue(name, 持久化等) = bufferChannel
 * bufferChannel.publish(message)
 *
 * workQueues: 消费者轮流消费消息
 */
public class AA_HelloWorld_Send {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionfactory = new ConnectionFactory();
        connectionfactory.setHost("localhost");
        connectionfactory.setVirtualHost("/");
        connectionfactory.setUsername("guest");
        connectionfactory.setPassword("guest");
        Connection connection = null;
        Channel channel = null;
        try {
            // 网络连接
            // connect可以建立多个channel
            connection = connectionfactory.newConnection();
            channel = connection.createChannel();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (null == channel) {
            System.out.println("建立连接失败...");
            return;
        }
        // queue：存储任务，消费者监听
        // durable: queue是否持久化，需要配个public
        channel.queueDeclare(Const.SIMPLE_QUEUE_NAME, false, false, false, null);

        // channel.basicQos(1);  这玩意配置到consumer端
        // 发布任务
        String message = "Hello World!";
        for (int i = 0; i < 20; i++) {
            // 持久化类型: 用于持久化
            channel.basicPublish("", Const.SIMPLE_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, (message + i).getBytes());
            System.out.println(" [x] Sent '" + message + "---" + i + "'");
        }
    }
}

/**
 * <p>
 * AA AB AC知识点：
 * 多个消费者：（默认）服务器以循环的方式投递任务（按任务数量平均分配，不考虑机器性能、不考虑任务重量）
 * consumer-ACK：防止任务丢失，如果某个消费者挂了，服务器会把该消费者剩下的任务分配给其他存活的消费者
 * 服务器挂了：queue和任务需要支持持久化。（queue是任务的缓冲区，发布任务式需要指定任务类型）
 * queue： 只要queue名称一样，其属性不能随便改，不允许会抛异常（所以：是否持久化需要 第一次创建queue的时候决定好）
 */