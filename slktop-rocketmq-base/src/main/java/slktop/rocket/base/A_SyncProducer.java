package slktop.rocket.base;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 可靠的同步传输
 * 应用于广泛的场景，如重要通知消息、短信通知、短信营销系统等。
 */
public class A_SyncProducer {
    public static void main(String[] args) throws Exception {
        // 生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("producer_group_1");
        // broker地址
        producer.setNamesrvAddr("localhost:9876");
        // producer.setSendMsgTimeout(10000);
        producer.start();

        for (int i = 0; i < 6; i++) {
            byte[] messageBody = ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET);
            // topic, tag, message
            Message msg = new Message("TopicTest","TagA", messageBody);
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        // producer不在使用就关闭
        producer.shutdown();
    }
}
