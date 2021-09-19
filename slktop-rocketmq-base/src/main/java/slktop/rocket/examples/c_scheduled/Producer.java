package slktop.rocket.examples.c_scheduled;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

public class Producer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        producer.start();
        int totalMessagesToSend = 10;
        try {
            for (int i = 0; i < totalMessagesToSend; i++) {
                byte[] msgBytes = ("Hello scheduled message " + i).getBytes();
                Message message = new Message("TestTopic2", msgBytes);
                // 设置延时等级3，18个等级。详见readme
                message.setDelayTimeLevel(3);
                producer.send(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }
}