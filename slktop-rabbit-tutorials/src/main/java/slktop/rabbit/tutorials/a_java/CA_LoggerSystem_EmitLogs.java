package slktop.rabbit.tutorials.a_java;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import slktop.rabbit.tutorials.RabbitTools;

import java.nio.charset.StandardCharsets;

/**
 * fanout: 广播
 * workQueues模式：
 * 1、每个worker只能接收到部分message
 * 2、消息可以缓存，可以读取到旧的消息
 * 日志系统：
 * 1、每个worker都能就收到同样的消息，无序过滤
 * 2、关心实时日志
 * 实现：
 * 相同的消息：广播
 * 实时性：consumer连接，创建新的queue，consumer离开删除queue
 * 创建新的queue：queue名字随机生成
 */
public class CA_LoggerSystem_EmitLogs {

    private static final String EXCHANGE_NAME = "logs_exchange";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitTools.getConnectionChannel();
        assert channel != null;

        // 交换机类型：fanout广播，exchange会把消息缓存到每个与其绑定的queue
        // 与queue关联的consumer就会收到消息
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        // 生产者端没有声明queue
        // 如果consumer没有queue，message会被丢弃（先启动emit 再启动receive，之前的消息会被丢弃）


        String str = "Hello World!";
        for (int i = 0; i < 20; i++) {
            String message = str + "---" + i;
            Thread.sleep(2000);
            // 发送消息给交换机，之前是queueName
            // fanout广播模式：routingKey没用
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
